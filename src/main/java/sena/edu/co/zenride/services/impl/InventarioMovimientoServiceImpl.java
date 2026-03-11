package sena.edu.co.zenride.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sena.edu.co.zenride.model.InventarioMovimiento;
import sena.edu.co.zenride.repository.InventarioMovimientoRepository;
import sena.edu.co.zenride.services.InventarioMovimientoService;
import java.util.List;


@Service
@RequiredArgsConstructor
public class InventarioMovimientoServiceImpl implements InventarioMovimientoService {

    private final InventarioMovimientoRepository inventarioRepository;

    @Override
    public InventarioMovimiento registrarMovimiento(InventarioMovimiento movimiento) {
        return inventarioRepository.save(movimiento);
    }

    @Override
    public List<InventarioMovimiento> listarMovimientos() {
        return inventarioRepository.findAll();
    }

    @Override
    public List<InventarioMovimiento> buscarPorBicicleta(Long idBicicleta) {
        return inventarioRepository.findByBicicleta_IdBicicleta(idBicicleta);
    }

}