package sena.edu.co.zenride.services;

import sena.edu.co.zenride.model.Venta;
import java.util.List;

public interface VentaService {
    Venta crearVenta(Venta venta);
    List<Venta> listarVentas();
}