package sena.edu.co.zenride.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sena.edu.co.zenride.dto.request.InventarioDetalleRequestDTO;
import sena.edu.co.zenride.dto.request.InventarioMovimientoRequestDTO;
import sena.edu.co.zenride.dto.response.InventarioMovimientoResponseDTO;
import sena.edu.co.zenride.entities.Bicicleta;
import sena.edu.co.zenride.entities.InventarioMovimiento;
import sena.edu.co.zenride.mapper.InventarioMovimientoMapper;
import sena.edu.co.zenride.repository.BicicletaRepository;
import sena.edu.co.zenride.repository.InventarioMovimientoRepository;
import sena.edu.co.zenride.services.IInventarioMovimientoService;

import java.util.List;

@Service
public class InventarioMovimientoServiceImpl implements IInventarioMovimientoService {

    @Autowired
    private InventarioMovimientoRepository repository;

    @Autowired
    private InventarioMovimientoMapper mapper;

    @Autowired
    private BicicletaRepository bicicletaRepository;

    @Override
    public List<InventarioMovimientoResponseDTO> listarTodo() {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public List<InventarioMovimientoResponseDTO> listarPorBicicleta(Long id) {
        return mapper.toResponseList(repository.findByBicicletaId(id));
    }

    @Override
    @Transactional
    public List<InventarioMovimientoResponseDTO> registrarMovimiento(InventarioMovimientoRequestDTO request) {
        List<InventarioMovimiento> movimientosGuardados = new java.util.ArrayList<>();

        // 1. Recorremos cada bicicleta del lote que viene de Angular
        for (InventarioDetalleRequestDTO detalle : request.getDetalles()) {

            // 2. Buscamos la bici
            Bicicleta bici = bicicletaRepository.findById(detalle.getBicicletaId())
                    .orElseThrow(() -> new RuntimeException("Bicicleta ID " + detalle.getBicicletaId() + " no encontrada"));

            // 3. Calculamos stock
            int actual = (bici.getStockActual() != null) ? bici.getStockActual() : 0;
            int cantidad = detalle.getCantidad();

            if ("ENTRADA".equalsIgnoreCase(request.getTipoMovimiento())) {
                bici.setStockActual(actual + cantidad);
            } else {
                if (actual < cantidad) throw new RuntimeException("Stock insuficiente para: " + bici.getModelo());
                bici.setStockActual(actual - cantidad);
            }
            bicicletaRepository.save(bici);

            // 4. Creamos la entidad de movimiento (tu entidad de siempre)
            InventarioMovimiento mov = new InventarioMovimiento();
            mov.setBicicleta(bici);
            mov.setCantidad(cantidad);
            mov.setTipoMovimiento(InventarioMovimiento.TipoMovimiento.valueOf(request.getTipoMovimiento().toUpperCase()));
            mov.setResponsableOperacion(request.getResponsableOperacion());
            mov.setDescripcion(request.getDescripcion());

            movimientosGuardados.add(repository.save(mov));
        }

        // 5. Convertimos toda la lista a DTO para responder al Frontend
        return mapper.toResponseList(movimientosGuardados);
    }

}