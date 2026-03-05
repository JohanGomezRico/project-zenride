package sena.edu.co.zenride.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bicicletas")
public class Bicicletas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long idBicicleta;

    @Column(name= "codigo")
    private String codigoBicicleta;

    @Column(name= "marca")
    private String marcaBicicleta;

    @Column(name= "modelo")
    private String modeloBicicleta;

    // 1. Mapeo de ENUM
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoBicicleta tipoBicicleta;

    // 2. Mapeo de DECIMAL a BigDecimal
    @Column(name = "precio_venta", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioVenta;

    @Column(name= "stock_actual")
    private Integer stockBicicleta;

    // Define el Enum dentro o fuera de la clase
    public enum TipoBicicleta {
        Montaña, Ruta, Urbana
    }

}
