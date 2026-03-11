package sena.edu.co.zenride.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sena.edu.co.zenride.model.*;
import sena.edu.co.zenride.repository.*;
import sena.edu.co.zenride.services.VentaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final BicicletaRepository bicicletaRepository; // Inyectado para stock
    private final InventarioMovimientoRepository inventarioRepository; // Inyectado para auditoría

    @Override
    @Transactional
    public Venta crearVenta(Venta venta) {
        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVenta detalle : venta.getDetalles()) {
            // 1. Buscar la bicicleta en DB para validar existencia y stock
            Bicicletas bicicleta = bicicletaRepository.findById(detalle.getBicicleta().getIdBicicleta())
                    .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada ID: " + detalle.getBicicleta().getIdBicicleta()));

            // 2. Regla Obligatoria: No vender más de lo disponible
            if (bicicleta.getStockBicicleta() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + bicicleta.getMarcaBicicleta() +
                        ". Disponibles: " + bicicleta.getStockBicicleta());
            }

            // 3. Regla Obligatoria: Descontar del inventario
            bicicleta.setStockBicicleta(bicicleta.getStockBicicleta() - detalle.getCantidad());
            bicicletaRepository.save(bicicleta);

            // 4. Auditoría: Registrar movimiento de salida
            InventarioMovimiento movimiento = InventarioMovimiento.builder()
                    .bicicleta(bicicleta)
                    .cantidad(detalle.getCantidad())
                    .tipoMovimiento(InventarioMovimiento.TipoMovimiento.SALIDA)
                    .descripcion("Venta ID: Automática")
                    .build();
            inventarioRepository.save(movimiento);

            // 5. Cálculos de precio y vinculación
            detalle.setPrecioUnitarioVenta(bicicleta.getPrecioVenta());
            detalle.setVenta(venta); // Vinculación manual necesaria para la FK
            detalle.calcularSubtotal();

            total = total.add(detalle.getSubtotal());
        }

        venta.setTotalVenta(total);
        return ventaRepository.save(venta);
    }

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Optional<Venta> buscarPorId(Long id) {
        // El repositorio ya trae este método por defecto gracias a JpaRepository
        return ventaRepository.findById(id);
    }
}