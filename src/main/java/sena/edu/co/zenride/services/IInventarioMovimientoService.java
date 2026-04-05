package sena.edu.co.zenride.services;

import sena.edu.co.zenride.dto.request.InventarioMovimientoRequestDTO;
import sena.edu.co.zenride.dto.response.InventarioMovimientoResponseDTO;
import java.util.List;

public interface IInventarioMovimientoService {
    List<InventarioMovimientoResponseDTO> listarTodo();
    List<InventarioMovimientoResponseDTO> listarPorBicicleta(Long bicicletaId);
    List<InventarioMovimientoResponseDTO> registrarMovimiento(InventarioMovimientoRequestDTO request);}
