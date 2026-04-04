package sena.edu.co.zenride.dto.response;

import lombok.Data;

@Data
public class ReporteResumenResponseDTO {
	private String periodo;
    private Long bicicletasEnStock;
    private Integer totalEntradas;
    private Integer totalSalidas;
    private Integer totalBicicletasVendidas;
    private Double ingresosBrutosTotales;
}
