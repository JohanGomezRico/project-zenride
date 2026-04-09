package sena.edu.co.zenride.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class ReporteResumenResponseDTO {
    private String periodo;
    private Long bicicletasEnStock;
    private Integer totalEntradas;
    private Integer totalSalidas;
    private Integer totalBicicletasVendidas;
    private Double ingresosBrutosTotales;

    // 👇 NUEVOS CAMPOS PARA LAS GRÁFICAS
    private List<String> labelsFechas;       // ['Lun', 'Mar', ...]
    private List<Double> datosIngresos;      // [120000, 250000, ...]
    
    private List<String> labelsTopBicis;     // ['GW Nantes', 'Trek...']
    private List<Integer> datosTopBicis;     // [12, 9, ...]

    // 👇 NUEVOS CAMPOS PARA LAS TABLAS
    private List<HistorialVentaDTO> historialVentas;
    private List<StockBajoDTO> stockBajo;

    // --- Clases Internas de Apoyo ---
    @Data
    public static class HistorialVentaDTO {
        private String fecha;
        private String producto;
        private String cliente;
        private Double total;
    }

    @Data
    public static class StockBajoDTO {
        private String producto;
        private String categoria;
        private Integer stock;
    }
}