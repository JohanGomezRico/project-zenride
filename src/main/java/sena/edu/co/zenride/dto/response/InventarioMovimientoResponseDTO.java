package sena.edu.co.zenride.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventarioMovimientoResponseDTO {
    private Long id;
    private String nombreBicicleta; // Para mostrar en la tabla de Angular
    private String codigoBicicleta;
    private Integer cantidad;
    private String tipoMovimiento;
    private String responsableOperacion;
    private LocalDateTime fechaMovimiento;
}
