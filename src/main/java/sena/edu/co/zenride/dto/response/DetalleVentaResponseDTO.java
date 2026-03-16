package sena.edu.co.zenride.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleVentaResponseDTO {
    // A Angular no le enviamos el objeto Bicicleta completo, solo lo que le importa al usuario
    private String marcaBicicleta;
    private String modeloBicicleta;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
