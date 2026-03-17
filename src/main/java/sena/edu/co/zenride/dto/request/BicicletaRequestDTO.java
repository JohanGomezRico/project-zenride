package sena.edu.co.zenride.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import sena.edu.co.zenride.model.Bicicletas.TipoBicicleta;

@Data
public class BicicletaRequestDTO {
    private String codigoBicicleta;
    private String marcaBicicleta;
    private String modeloBicicleta;
    private TipoBicicleta tipoBicicleta;
    private BigDecimal precioVenta;
    private Integer stockBicicleta;
}
