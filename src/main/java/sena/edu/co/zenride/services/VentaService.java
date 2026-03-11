package sena.edu.co.zenride.services;

import sena.edu.co.zenride.model.Venta;
import java.util.List;
import java.util.Optional;

public interface VentaService {
    Venta crearVenta(Venta venta);
    List<Venta> listarVentas();
    Optional<Venta> buscarPorId(Long id);
}