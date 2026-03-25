package sena.edu.co.zenride.services;

import sena.edu.co.zenride.dto.response.InventarioMovimientoResponseDTO;
import java.util.List;

public interface IInventarioMovimientoService {
    List<InventarioMovimientoResponseDTO> listarTodo();
    List<InventarioMovimientoResponseDTO> listarPorBicicleta(Long bicicletaId);
    InventarioMovimientoResponseDTO registrarMovimiento(sena.edu.co.zenride.dto.request.InventarioMovimientoRequestDTO request);
}