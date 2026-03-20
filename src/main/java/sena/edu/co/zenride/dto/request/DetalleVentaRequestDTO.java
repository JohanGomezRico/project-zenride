package sena.edu.co.zenride.dto.request;

import lombok.Data;

@Data
public class DetalleVentaRequestDTO {
    private Long bicicletaId; // ID de la bici que se lleva
    private Integer cantidad;
}