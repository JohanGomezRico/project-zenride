package sena.edu.co.zenride.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sena.edu.co.zenride.dto.response.ReporteResumenResponseDTO;
import sena.edu.co.zenride.entities.DetalleVenta;
import sena.edu.co.zenride.entities.InventarioMovimiento;
import sena.edu.co.zenride.entities.Venta;
import sena.edu.co.zenride.repository.BicicletaRepository;
import sena.edu.co.zenride.repository.InventarioMovimientoRepository;
import sena.edu.co.zenride.repository.VentaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class IReporteService {

	@Autowired
	private VentaRepository ventaRepository;
	@Autowired
	private InventarioMovimientoRepository movimientoRepository;
	@Autowired
	private BicicletaRepository bicicletaRepository;

	@Transactional
	public ReporteResumenResponseDTO generarResumen(LocalDateTime inicio, LocalDateTime fin, String tipo, String marca,
			String vendedor) {

		// 1. OBTENER Y FILTRAR VENTAS
		List<Venta> ventasBase = ventaRepository.findByFechaVentaBetween(inicio, fin);

		List<Venta> ventasFiltradas = ventasBase.stream().filter(v -> {
			boolean coincideMarcaOTipo = v.getDetalles().stream().anyMatch(d -> {
				boolean cMarca = marca.equals("Todas") || d.getBicicleta().getMarca().equalsIgnoreCase(marca);
				// Como tipo es un Enum, extraemos su nombre (.name()) para compararlo
				boolean cTipo = tipo.equals("Todos") || d.getBicicleta().getTipo().name().equalsIgnoreCase(tipo);
				return cMarca && cTipo;
			});
			return coincideMarcaOTipo;
		}).toList();

		List<InventarioMovimiento> movimientos = movimientoRepository.findByFechaMovimientoBetween(inicio, fin);
		ReporteResumenResponseDTO resumen = new ReporteResumenResponseDTO();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		resumen.setPeriodo("Del " + inicio.format(formatter) + " al " + fin.format(formatter));

		// 2. MÉTRICAS PRINCIPALES
		resumen.setIngresosBrutosTotales(ventasFiltradas.stream().mapToDouble(Venta::getTotalVenta).sum());
		resumen.setTotalBicicletasVendidas(ventasFiltradas.stream().flatMap(v -> v.getDetalles().stream())
				.mapToInt(DetalleVenta::getCantidad).sum());

		resumen.setTotalEntradas(
				movimientos.stream().filter(m -> m.getTipoMovimiento() == InventarioMovimiento.TipoMovimiento.ENTRADA)
						.mapToInt(InventarioMovimiento::getCantidad).sum());
		resumen.setTotalSalidas(
				movimientos.stream().filter(m -> m.getTipoMovimiento() == InventarioMovimiento.TipoMovimiento.SALIDA)
						.mapToInt(InventarioMovimiento::getCantidad).sum());
		resumen.setBicicletasEnStock(bicicletaRepository.count());

		// 3. DATOS PARA LA GRÁFICA DE LÍNEAS (Ingresos por Día)
		Map<LocalDate, Double> ingresosPorDia = ventasFiltradas.stream()
				.collect(Collectors.groupingBy(v -> v.getFechaVenta().toLocalDate(), TreeMap::new, // TreeMap ordena las
																									// fechas
																									// cronológicamente
																									// de forma
																									// automática
						Collectors.summingDouble(Venta::getTotalVenta)));

		// Formateamos las fechas (ej: "08/04") y guardamos
		resumen.setLabelsFechas(
				ingresosPorDia.keySet().stream().map(d -> d.format(DateTimeFormatter.ofPattern("dd/MM"))).toList());
		resumen.setDatosIngresos(new ArrayList<>(ingresosPorDia.values()));

		// 4. DATOS PARA LA GRÁFICA DE BARRAS (Top 5 Bicicletas)
		Map<String, Integer> bicisVendidasMap = ventasFiltradas.stream().flatMap(v -> v.getDetalles().stream())
				.collect(Collectors.groupingBy(d -> d.getBicicleta().getMarca() + " " + d.getBicicleta().getModelo(),
						Collectors.summingInt(DetalleVenta::getCantidad)));

		List<Map.Entry<String, Integer>> topBicis = bicisVendidasMap.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // Ordenar de mayor a menor
				.limit(5) // Tomar solo el Top 5
				.toList();

		resumen.setLabelsTopBicis(topBicis.stream().map(Map.Entry::getKey).toList());
		resumen.setDatosTopBicis(topBicis.stream().map(Map.Entry::getValue).toList());

		// 5. DATOS PARA LA TABLA: Historial Reciente (Últimas 10 ventas)
		List<ReporteResumenResponseDTO.HistorialVentaDTO> historial = ventasFiltradas.stream()
				.sorted(Comparator.comparing(Venta::getFechaVenta).reversed()).limit(10).map(v -> {
					ReporteResumenResponseDTO.HistorialVentaDTO dto = new ReporteResumenResponseDTO.HistorialVentaDTO();
					dto.setFecha(v.getFechaVenta().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
					dto.setCliente(v.getCliente() != null ? v.getCliente().getNombre() : "Mostrador");
					dto.setTotal(v.getTotalVenta());

					// Si compró varias cosas, mostramos la principal y un "+X"
					String prodPrincipal = v.getDetalles().isEmpty() ? "Venta sin detalle"
							: v.getDetalles().get(0).getBicicleta().getModelo();
					if (v.getDetalles().size() > 1)
						prodPrincipal += " (+" + (v.getDetalles().size() - 1) + " ítems)";
					dto.setProducto(prodPrincipal);
					return dto;
				}).toList();
		resumen.setHistorialVentas(historial);

		// 6. DATOS PARA LA TABLA: Alerta de Stock Crítico (Menos de 3 unidades)
		List<ReporteResumenResponseDTO.StockBajoDTO> stockBajoList = bicicletaRepository.findAll().stream()
				.filter(b -> b.getStockActual() <= 3).map(b -> {
					ReporteResumenResponseDTO.StockBajoDTO dto = new ReporteResumenResponseDTO.StockBajoDTO();
					dto.setProducto(b.getMarca() + " " + b.getModelo());
					dto.setCategoria(b.getTipo().name());
					dto.setStock(b.getStockActual());
					return dto;
				}).toList();
		resumen.setStockBajo(stockBajoList);

		return resumen;
	}

	// 2. Método para dibujar el PDF
	public byte[] generarPdfResumen(LocalDateTime inicio, LocalDateTime fin, String tipo, String marca,
			String vendedor) {
		ReporteResumenResponseDTO datos = generarResumen(inicio, fin, tipo, marca, vendedor);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = new PdfWriter(baos);
		PdfDocument pdfDocument = new PdfDocument(writer);
		Document document = new Document(pdfDocument);

		// Título
		Paragraph titulo = new Paragraph("REPORTE GENERAL - ZEN RIDE").setBold().setFontSize(18)
				.setTextAlignment(TextAlignment.CENTER);
		document.add(titulo);

		// Subtítulo
		Paragraph periodo = new Paragraph("Periodo: " + datos.getPeriodo()).setFontSize(12)
				.setTextAlignment(TextAlignment.CENTER).setMarginBottom(20);
		document.add(periodo);

		// Crear una tabla de 2 columnas para mostrar las métricas
		Table tabla = new Table(2);
		tabla.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));

		// Agregar celdas a la tabla
		tabla.addCell(new Cell().add(new Paragraph("Bicicletas Activas en Catálogo:").setBold()));
		tabla.addCell(new Cell().add(new Paragraph(String.valueOf(datos.getBicicletasEnStock()))));

		tabla.addCell(new Cell().add(new Paragraph("Bicicletas Entrantes (Stock):").setBold()));
		tabla.addCell(new Cell().add(new Paragraph(String.valueOf(datos.getTotalEntradas()))));

		tabla.addCell(new Cell().add(new Paragraph("Bicicletas Salientes (Ajustes):").setBold()));
		tabla.addCell(new Cell().add(new Paragraph(String.valueOf(datos.getTotalSalidas()))));

		tabla.addCell(new Cell().add(new Paragraph("Bicicletas Vendidas (Ventas):").setBold()));
		tabla.addCell(new Cell().add(new Paragraph(String.valueOf(datos.getTotalBicicletasVendidas()))));

		tabla.addCell(new Cell().add(new Paragraph("Ingresos Brutos Totales:").setBold()));
		// Formateamos el dinero de forma sencilla
		tabla.addCell(new Cell().add(new Paragraph(String.format("$%,.2f", datos.getIngresosBrutosTotales()))));

		document.add(tabla);
		document.close();

		return baos.toByteArray();
	}
}
