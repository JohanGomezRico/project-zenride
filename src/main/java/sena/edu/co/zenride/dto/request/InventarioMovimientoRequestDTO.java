package sena.edu.co.zenride.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class InventarioMovimientoRequestDTO {
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String tipoMovimiento;

    @NotBlank(message = "El responsable es obligatorio")
    private String responsableOperacion;

    private String descripcion;

    // Esta lista debe llamarse 'detalles' para coincidir con tu Angular
    @NotNull(message = "El lote no puede estar vacío")
    private List<InventarioDetalleRequestDTO> detalles;
}