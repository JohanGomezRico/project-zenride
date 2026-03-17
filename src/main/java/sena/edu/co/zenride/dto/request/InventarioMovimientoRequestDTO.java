package sena.edu.co.zenride.dto.request;

import lombok.Data;
import sena.edu.co.zenride.model.InventarioMovimiento.TipoMovimiento;

@Data
public class InventarioMovimientoRequestDTO {
    private Long idBicicleta; // Angular nos dice a qué bici le entra stock
    private TipoMovimiento tipoMovimiento; // ENTRADA o SALIDA
    private Integer cantidad;
    private String descripcion;
}
