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
@Table(name = "detalle_ventas")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la Venta Maestra
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    // Relación con el Producto (Bicicleta)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bicicleta_id", nullable = false)
    private Bicicletas bicicleta;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario_venta", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioUnitarioVenta;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal subtotal;

    public void calcularSubtotal() {
        if (this.precioUnitarioVenta != null && this.cantidad != null) {
            this.subtotal = this.precioUnitarioVenta.multiply(new BigDecimal(this.cantidad));
        }
    }
}
