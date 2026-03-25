package sena.edu.co.zenride.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public InventarioMovimientoResponseDTO registrarMovimiento(InventarioMovimientoRequestDTO request) {
        // 1. Buscas la bici
        Bicicleta bici = bicicletaRepository.findById(request.getBicicletaId())
                .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada"));

        // 2. Declaras las variables PRIMERO (Importante el orden)
        int actual = (bici.getStockActual() != null) ? bici.getStockActual() : 0;
        int cantidad = request.getCantidad(); // 👈 Esta línea debe ir ARRIBA de los IF

        // 3. Lógica de Suma o Resta
        if ("ENTRADA".equalsIgnoreCase(request.getTipoMovimiento())) {
            bici.setStockActual(actual + cantidad);
        } else if ("SALIDA".equalsIgnoreCase(request.getTipoMovimiento())) {
            if (actual < cantidad) throw new RuntimeException("Stock insuficiente");
            bici.setStockActual(actual - cantidad);
        }

        // 4. Guardar cambios en la bicicleta
        bicicletaRepository.save(bici);

        // 5. Crear el registro del movimiento para el historial
        InventarioMovimiento mov = new InventarioMovimiento();
        mov.setBicicleta(bici);
        mov.setCantidad(cantidad);
        mov.setTipoMovimiento(InventarioMovimiento.TipoMovimiento.valueOf(request.getTipoMovimiento().toUpperCase()));
        mov.setResponsableOperacion(request.getResponsableOperacion());
        mov.setDescripcion(request.getDescripcion());

        return mapper.toResponseDTO(repository.save(mov));
    }
}