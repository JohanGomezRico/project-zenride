
package sena.edu.co.zenride.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventarioMovimientoResponseDTO {
    private Long id;
    private String bicicletaCodigo;
    private String nombreBicicleta;
    private String tipoMovimiento;
    private Integer cantidad;
    private String responsableOperacion;
    private LocalDateTime fechaMovimiento;
    private String descripcion;
}