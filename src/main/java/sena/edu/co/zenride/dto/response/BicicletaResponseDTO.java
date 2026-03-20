package sena.edu.co.zenride.dtos.response;

import lombok.Data;

@Data
public class BicicletaResponseDTO {
    private Long id;
    private String codigo;
    private String marca;
    private String modelo;
    private String tipo;
    private Double precioVenta;
    private Integer stockActual;
}