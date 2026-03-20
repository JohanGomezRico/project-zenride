package sena.edu.co.zenride.dto.response;

import lombok.Data;

@Data
public class DetalleVentaResponseDTO {
    private Long id;
    private String codigoBicicleta; // "BICI-001"
    private String modeloBicicleta; // "Mountain Pro 2026"
    private Integer cantidad;
    private Double precioUnitarioVenta;
    private Double subtotal;
}