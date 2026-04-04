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
import sena.edu.co.zenride.entities.InventarioMovimiento;
import sena.edu.co.zenride.entities.Venta;
import sena.edu.co.zenride.repository.BicicletaRepository;
import sena.edu.co.zenride.repository.InventarioMovimientoRepository;
import sena.edu.co.zenride.repository.VentaRepository;

import java.io.ByteArrayOutputStream;
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
    
    
 // 1. Método para obtener los datos numéricos
    public ReporteResumenResponseDTO generarResumen(LocalDateTime inicio, LocalDateTime fin) {
        List<Venta> ventas = ventaRepository.findByFechaVentaBetween(inicio, fin);
        List<InventarioMovimiento> movimientos = movimientoRepository.findByFechaMovimientoBetween(inicio, fin);

        ReporteResumenResponseDTO resumen = new ReporteResumenResponseDTO();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        resumen.setPeriodo("Del " + inicio.format(formatter) + " al " + fin.format(formatter));
        
        // Sumar ingresos brutos
        double ingresos = ventas.stream().mapToDouble(Venta::getTotalVenta).sum();
        resumen.setIngresosBrutosTotales(ingresos);
        
     // Contar bicicletas vendidas (sumando las cantidades de los detalles de cada venta)
        int bicisVendidas = ventas.stream()
                .flatMap(v -> v.getDetalles().stream())
                .mapToInt(d -> d.getCantidad())
                .sum();
        resumen.setTotalBicicletasVendidas(bicisVendidas);

        // Separar entradas y salidas de inventario
        int entradas = movimientos.stream()
                .filter(m -> m.getTipoMovimiento() == InventarioMovimiento.TipoMovimiento.ENTRADA)
                .mapToInt(InventarioMovimiento::getCantidad).sum();
        
        int salidas = movimientos.stream()
                .filter(m -> m.getTipoMovimiento() == InventarioMovimiento.TipoMovimiento.SALIDA)
                .mapToInt(InventarioMovimiento::getCantidad).sum();
                
        resumen.setTotalEntradas(entradas);
        resumen.setTotalSalidas(salidas);
        resumen.setBicicletasEnStock(bicicletaRepository.count());

        return resumen;
}
    
 // 2. Método para dibujar el PDF
    public byte[] generarPdfResumen(LocalDateTime inicio, LocalDateTime fin) {
        ReporteResumenResponseDTO datos = generarResumen(inicio, fin);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        
     // Título
        Paragraph titulo = new Paragraph("REPORTE GENERAL - ZEN RIDE")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(titulo);

        // Subtítulo
        Paragraph periodo = new Paragraph("Periodo: " + datos.getPeriodo())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
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
