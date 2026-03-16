package sena.edu.co.zenride.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import sena.edu.co.zenride.model.InventarioMovimiento.TipoMovimiento;

@Data
public class InventarioMovimientoResponseDTO {
    private Long id;
    private String modeloBicicleta; // Solo enviamos el nombre para la tabla
    private TipoMovimiento tipoMovimiento;
    private Integer cantidad;
    private LocalDateTime fechaMovimiento;
    private String descripcion;
}