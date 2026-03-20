package sena.edu.co.zenride.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.edu.co.zenride.dto.request.ClienteRequestDTO;
import sena.edu.co.zenride.dto.response.ClienteResponseDTO;
import sena.edu.co.zenride.services.IClienteService;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/documento/{doc}")
    public ResponseEntity<ClienteResponseDTO> buscar(@PathVariable String doc) {
        return ResponseEntity.ok(clienteService.buscarPorDocumento(doc));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrar(@RequestBody ClienteRequestDTO request) {
        return ResponseEntity.ok(clienteService.guardar(request));
    }
}