package sena.edu.co.zenride.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_ventas")
@Data
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bicicleta_id", nullable = false)
    private Bicicleta bicicleta;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario_venta", nullable = false)
    private Double precioUnitarioVenta;

    @Column(nullable = false)
    private Double subtotal;
}
