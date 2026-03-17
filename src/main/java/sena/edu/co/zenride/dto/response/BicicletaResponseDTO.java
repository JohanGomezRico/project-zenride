package sena.edu.co.zenride.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import sena.edu.co.zenride.model.Bicicletas.TipoBicicleta;

@Data
public class BicicletaResponseDTO {
    private Long idBicicleta; // Aquí SÍ va el ID
    private String codigoBicicleta;
    private String marcaBicicleta;
    private String modeloBicicleta;
    private TipoBicicleta tipoBicicleta;
    private BigDecimal precioVenta;
    private Integer stockBicicleta;
}
