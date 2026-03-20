package sena.edu.co.zenride.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class VentaRequestDTO {
    private Long clienteId;
    // Quitamos el prefijo largo sena.edu.co... para evitar errores de package
    private List<sena.edu.co.zenride.dto.request.DetalleVentaRequestDTO> detalles;
}