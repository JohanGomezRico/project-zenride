package sena.edu.co.zenride.services;

import sena.edu.co.zenride.entities.Bicicleta;
import java.util.List;

public interface IBicicletaService {
    List<sena.edu.co.zenride.dtos.response.BicicletaResponseDTO> listarTodas();
    sena.edu.co.zenride.dtos.response.BicicletaResponseDTO buscarPorId(Long id);
    sena.edu.co.zenride.dtos.response.BicicletaResponseDTO guardar(sena.edu.co.zenride.dtos.request.BicicletaRequestDTO request); // Ahora recibe RequestDTO
    void registrarEntrada(Long idBicicleta, Integer cantidad, String responsable);
    void eliminar(Long id);
}