package sena.edu.co.zenride.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sena.edu.co.zenride.model.Cliente;
import sena.edu.co.zenride.services.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Listar clientes registrados")
    @GetMapping("/obtenerClientes")
    public List<Cliente> getClientes() {
        return clienteService.listarTodos();
    }

    @Operation(summary = "Registrar cliente")
    @PostMapping("/guardar")
    public Cliente guardar(@RequestBody Cliente cliente) {
        return clienteService.guardar(cliente);
    }
}
