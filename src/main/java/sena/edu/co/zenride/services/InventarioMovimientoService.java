package sena.edu.co.zenride.services;

import sena.edu.co.zenride.model.InventarioMovimiento;
import java.util.List;

public interface InventarioMovimientoService {
    InventarioMovimiento registrarMovimiento(InventarioMovimiento movimiento);
    List<InventarioMovimiento> listarMovimientos();
}
