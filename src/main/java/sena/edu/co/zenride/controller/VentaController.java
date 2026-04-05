package sena.edu.co.zenride.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sena.edu.co.zenride.dto.request.VentaRequestDTO;
import sena.edu.co.zenride.dto.response.VentaResponseDTO;
import sena.edu.co.zenride.services.IVentaService;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(originPatterns = "*")
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> vender(@RequestBody VentaRequestDTO request) {
        // CORREGIDO: Llamamos al service pasándole el DTO
        return ResponseEntity.ok(ventaService.realizarVenta(request));
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> historial() {
        return ResponseEntity.ok(ventaService.listarVentas());
    }
}
