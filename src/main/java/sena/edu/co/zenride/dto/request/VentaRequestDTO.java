package sena.edu.co.zenride.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class VentaRequestDTO {
    private Long idCliente; // Angular solo nos manda el ID del cliente
    private List<DetalleVentaRequestDTO> detalles; // Lista de lo que compró
}
