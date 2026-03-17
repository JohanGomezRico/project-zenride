package sena.edu.co.zenride.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VentaResponseDTO {
    private Long idVenta;
    private String nombreCliente; // En vez de todo el cliente, solo le pasamos el nombre
    private LocalDateTime fechaVenta;
    private BigDecimal totalVenta;
    // Si quieres, puedes devolver la lista de detalles aquí también,
    // pero a veces con el total y la fecha es suficiente para una tabla.
}
