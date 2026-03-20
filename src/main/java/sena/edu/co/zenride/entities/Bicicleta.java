package sena.edu.co.zenride.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bicicletas")
@Data // Genera Getters, Setters, toString, etc.
public class Bicicleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String codigo;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Enumerated(EnumType.STRING) // Importante para que coincida con tu ENUM de SQL
    @Column(nullable = false)
    private TipoBicicleta tipo;

    @Column(name = "precio_venta", nullable = false)
    private Double precioVenta;

    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual = 0;

    public enum TipoBicicleta {
        Montaña, Ruta, Urbana, Electrica
    }
}
