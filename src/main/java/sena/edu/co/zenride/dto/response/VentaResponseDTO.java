package sena.edu.co.zenride.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fechaVenta;
    private Double totalVenta;
    private String nombreCliente; // Para mostrar en Angular directamente
    private List<DetalleVentaResponseDTO> detalles;
}