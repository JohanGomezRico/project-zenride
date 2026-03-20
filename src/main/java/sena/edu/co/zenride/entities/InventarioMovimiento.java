package sena.edu.co.zenride.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario_movimientos")
@Data
public class InventarioMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bicicleta_id", nullable = false)
    private Bicicleta bicicleta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "responsable_operacion", nullable = false, length = 100)
    private String responsableOperacion;

    @Column(name = "fecha_movimiento", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaMovimiento = LocalDateTime.now();

    @Column(length = 255)
    private String descripcion;

    public enum TipoMovimiento {
        ENTRADA, SALIDA
    }
}
