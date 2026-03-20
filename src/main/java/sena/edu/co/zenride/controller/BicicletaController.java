package sena.edu.co.zenride.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.edu.co.zenride.dtos.request.BicicletaRequestDTO;
import sena.edu.co.zenride.dtos.response.BicicletaResponseDTO;
import sena.edu.co.zenride.services.IBicicletaService;
import java.util.List;

@RestController
@RequestMapping("/api/bicicletas")
@CrossOrigin(origins = "*") // Permite que Angular se conecte
public class BicicletaController {

    @Autowired
    private IBicicletaService bicicletaService;

    @GetMapping
    public ResponseEntity<List<BicicletaResponseDTO>> listar() {
        return ResponseEntity.ok(bicicletaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BicicletaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bicicletaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<BicicletaResponseDTO> crear(@RequestBody BicicletaRequestDTO request) {
        return ResponseEntity.ok(bicicletaService.guardar(request));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<String> cargarStock(@PathVariable Long id, @RequestParam Integer cantidad, @RequestParam String responsable) {
        bicicletaService.registrarEntrada(id, cantidad, responsable);
        return ResponseEntity.ok("Stock actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        bicicletaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
