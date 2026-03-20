package sena.edu.co.zenride.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioMovimientoRequestDTO {

    @NotNull(message = "El ID de la bicicleta es obligatorio")
    private Long bicicletaId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotBlank(message = "El tipo de movimiento es obligatorio (ENTRADA/SALIDA)")
    private String tipoMovimiento;

    @NotBlank(message = "El responsable es obligatorio")
    private String responsableOperacion;

    private String descripcion;
}