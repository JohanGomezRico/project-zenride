package sena.edu.co.zenride.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sena.edu.co.zenride.model.Cliente;
import sena.edu.co.zenride.repository.ClienteRepository;
import sena.edu.co.zenride.services.ClienteService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Optional<Cliente> buscarPorDocumento(String documento) {

        return clienteRepository.findByDocumento(documento);
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
