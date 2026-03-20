package sena.edu.co.zenride.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sena.edu.co.zenride.dto.request.ClienteRequestDTO;
import sena.edu.co.zenride.dto.response.ClienteResponseDTO;
import sena.edu.co.zenride.entities.Cliente;
import sena.edu.co.zenride.mapper.ClienteMapper;
import sena.edu.co.zenride.repository.ClienteRepository;
import sena.edu.co.zenride.services.IClienteService;
import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ClienteMapper clienteMapper;

    @Override
    public List<ClienteResponseDTO> listarTodos() {
        // Obtenemos entidades de la BD
        List<Cliente> listaEntidades = clienteRepository.findAll();
        // Convertimos a DTOs para que el Controller no marque error
        return clienteMapper.toResponseList(listaEntidades);
    }

    @Override
    public ClienteResponseDTO buscarPorDocumento(String documento) {
        Cliente cliente = clienteRepository.findByDocumento(documento)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return clienteMapper.toResponseDTO(cliente);
    }

    @Override
    public ClienteResponseDTO guardar(ClienteRequestDTO request) {
        Cliente cliente = clienteMapper.toEntity(request);
        return clienteMapper.toResponseDTO(clienteRepository.save(cliente));
    }
}