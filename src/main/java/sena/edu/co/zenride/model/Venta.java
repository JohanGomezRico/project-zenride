package sena.edu.co.zenride.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // --- NUEVA MEJORA: RELACIÓN CON DETALLES ---
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Necesario para que Lombok no deje la lista nula al usar el Builder
    private List<DetalleVenta> detalles = new ArrayList<>();

    @Column(name = "fecha_venta", updatable = false)
    private LocalDateTime fechaVenta;

    @Column(name = "total_venta", precision = 12, scale = 2, nullable = false)
    private BigDecimal totalVenta;

    @PrePersist
    protected void onCreate() {
        this.fechaVenta = LocalDateTime.now();
    }

    // --- MÉTODO RECOMENDADO PARA EVITAR ERRORES ---
    public void agregarDetalle(DetalleVenta detalle) {
        if (this.detalles == null) {
            this.detalles = new ArrayList<>();
        }
        this.detalles.add(detalle);
        detalle.setVenta(this); // <--- ESTO vincula el venta_id que tu SQL pide como NOT NULL
    }
}
