package sena.edu.co.zenride.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sena.edu.co.zenride.model.InventarioMovimiento;
import sena.edu.co.zenride.services.InventarioMovimientoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InventarioController {

    private final InventarioMovimientoService inventarioService;

    @Operation(summary = "Ver auditoría de stock")
    @GetMapping("/movimientos")
    public List<InventarioMovimiento> listar() {
        return inventarioService.listarMovimientos();
    }

    @Operation(summary = "Historial por bicicleta")
    @GetMapping("/bicicleta/{idBicicleta}")
    public List<InventarioMovimiento> porBicicleta(@PathVariable Long idBicicleta) {
        return inventarioService.buscarPorBicicleta(idBicicleta);
    }
}
