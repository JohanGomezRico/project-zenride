package sena.edu.co.zenride.services;

import java.util.List;

public interface IBicicletaService {
    List<sena.edu.co.zenride.dto.response.BicicletaResponseDTO> listarTodas();
    sena.edu.co.zenride.dto.response.BicicletaResponseDTO buscarPorId(Long id);
    sena.edu.co.zenride.dto.response.BicicletaResponseDTO guardar(sena.edu.co.zenride.dto.request.BicicletaRequestDTO request); // Ahora recibe RequestDTO
    void registrarEntrada(String codigoBicicleta, Integer cantidad, String responsable, String tipoMovimiento);
    void eliminar(Long id);
}