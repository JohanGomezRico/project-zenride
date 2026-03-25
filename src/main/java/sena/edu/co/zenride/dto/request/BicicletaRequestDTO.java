package sena.edu.co.zenride.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BicicletaRequestDTO {
    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;

    private String modelo;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precioVenta;

    private String tipo; // Se recibe como String y el Mapper lo pasa a ENUM
}