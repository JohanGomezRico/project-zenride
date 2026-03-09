package sena.edu.co.zenride.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sena.edu.co.zenride.model.Venta;
import sena.edu.co.zenride.repository.VentaRepository;
import sena.edu.co.zenride.services.VentaService;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;

    @Override
    @Transactional
    public Venta crearVenta(Venta venta) {
        BigDecimal total = BigDecimal.ZERO;

        for (var detalle : venta.getDetalles()) {
            // Calculamos subtotal de cada línea
            detalle.calcularSubtotal();
            total = total.add(detalle.getSubtotal());

            // Vinculamos el detalle con la venta maestra
            venta.agregarDetalle(detalle);
        }

        venta.setTotalVenta(total);
        return ventaRepository.save(venta);
    }

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }
}
