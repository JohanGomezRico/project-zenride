package sena.edu.co.zenride.services;

import sena.edu.co.zenride.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listarTodos();
    Optional<Cliente> buscarPorId(Long id);
    Optional<Cliente> buscarPorDocumento(String documento); // Muy útil para ventas
    Cliente guardar(Cliente cliente);
    void eliminar(Long id);
}
