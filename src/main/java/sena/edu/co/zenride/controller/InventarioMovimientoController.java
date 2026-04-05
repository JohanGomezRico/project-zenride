package sena.edu.co.zenride.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.edu.co.zenride.dto.request.InventarioMovimientoRequestDTO;
import sena.edu.co.zenride.dto.response.InventarioMovimientoResponseDTO;
import sena.edu.co.zenride.services.IInventarioMovimientoService;
import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(originPatterns = "*")
public class InventarioMovimientoController {
    @Autowired
    private IInventarioMovimientoService inventarioService;

    @GetMapping("/movimientos")
    public ResponseEntity<List<InventarioMovimientoResponseDTO>> verTodo() {
        return ResponseEntity.ok(inventarioService.listarTodo());
    }

    @GetMapping("/bicicleta/{id}")
    public ResponseEntity<List<InventarioMovimientoResponseDTO>> porBicicleta(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.listarPorBicicleta(id));
    }

    @PostMapping
    public ResponseEntity<List<InventarioMovimientoResponseDTO>> registrar(@RequestBody InventarioMovimientoRequestDTO request) {
        // Ahora devuelve List porque el servicio devuelve List
        return ResponseEntity.ok(inventarioService.registrarMovimiento(request));
    }
}
