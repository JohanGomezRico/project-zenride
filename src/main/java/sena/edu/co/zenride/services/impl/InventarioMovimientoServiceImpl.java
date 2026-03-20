package sena.edu.co.zenride.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sena.edu.co.zenride.dto.response.InventarioMovimientoResponseDTO;
import sena.edu.co.zenride.entities.InventarioMovimiento;
import sena.edu.co.zenride.mapper.InventarioMovimientoMapper;
import sena.edu.co.zenride.repository.InventarioMovimientoRepository;
import sena.edu.co.zenride.services.IInventarioMovimientoService;
import java.util.List;

@Service
public class InventarioMovimientoServiceImpl implements IInventarioMovimientoService {

    @Autowired private InventarioMovimientoRepository repository;
    @Autowired private InventarioMovimientoMapper mapper;

    @Override
    public List<InventarioMovimientoResponseDTO> listarTodo() {
        // Buscamos entidades en DB
        List<InventarioMovimiento> entidades = repository.findAll();
        // Convertimos a DTOs y retornamos
        return mapper.toResponseList(entidades);
    }

    @Override
    public List<InventarioMovimientoResponseDTO> listarPorBicicleta(Long id) {
        List<InventarioMovimiento> entidades = repository.findByBicicletaId(id);
        return mapper.toResponseList(entidades);
    }
}