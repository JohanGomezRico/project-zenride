package sena.edu.co.zenride.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.edu.co.zenride.dto.response.ReporteResumenResponseDTO;
import sena.edu.co.zenride.services.IReporteService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(originPatterns = "*")
public class ReporteController {
	
	@Autowired
    private IReporteService reporteService;

	// Obtener los datos JSON para mostrar en pantalla
    @GetMapping("/resumen")
    public ResponseEntity<ReporteResumenResponseDTO> obtenerResumen(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            // 👇 NUEVOS FILTROS (Son requeridos, pero en Angular enviaremos "Todos")
            @RequestParam(value = "tipo", defaultValue = "Todos") String tipo,
            @RequestParam(value = "marca", defaultValue = "Todas") String marca,
            @RequestParam(value = "vendedor", defaultValue = "Todos") String vendedor) {
        
        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);
        
        // Le pasamos los nuevos filtros al servicio
        return ResponseEntity.ok(reporteService.generarResumen(inicio, fin, tipo, marca, vendedor));
    }
    
 // Descargar el archivo PDF
    @GetMapping("/descargar-pdf")
    public ResponseEntity<byte[]> descargarPdf(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            // 👇 Agregamos los filtros opcionales aquí también
            @RequestParam(value = "tipo", defaultValue = "Todos") String tipo,
            @RequestParam(value = "marca", defaultValue = "Todas") String marca,
            @RequestParam(value = "vendedor", defaultValue = "Todos") String vendedor) {

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        // 👇 Pasamos todos los parámetros al servicio
        byte[] pdfBytes = reporteService.generarPdfResumen(inicio, fin, tipo, marca, vendedor);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Reporte_ZenRide.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
