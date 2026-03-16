package sena.edu.co.zenride.dto.request;

import lombok.Data;

@Data
public class DetalleVentaRequestDTO {
    private Long idBicicleta; // Solo necesitamos saber qué bici es
    private Integer cantidad; // Y cuántas va a llevar
}