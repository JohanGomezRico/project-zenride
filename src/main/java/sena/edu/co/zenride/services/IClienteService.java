package sena.edu.co.zenride.services;

import sena.edu.co.zenride.dto.request.ClienteRequestDTO;
import sena.edu.co.zenride.dto.response.ClienteResponseDTO;
import java.util.List;

public interface IClienteService {
    List<ClienteResponseDTO> listarTodos();
    ClienteResponseDTO buscarPorDocumento(String documento);
    ClienteResponseDTO guardar(ClienteRequestDTO request);
}