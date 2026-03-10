package sena.edu.co.zenride.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sena.edu.co.zenride.model.Bicicletas;
import sena.edu.co.zenride.services.BicicletaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BicicletaController {


    @Autowired
    private BicicletaService bicicletaService;

    @Operation(summary = "Obtener bicicletas", description = "Servicio para obtener todos los bicicletas")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Exitoso"),
            @ApiResponse(responseCode = "204", description = "No hay información"),
            @ApiResponse(responseCode = "500", description = "Error interno"),
            @ApiResponse(responseCode = "400", description = "Error de request"),
            @ApiResponse(responseCode = "401", description = "No autorizado") })
    @GetMapping(value = "/obtenerBicicletas", produces = "application/json")
    public List<Bicicletas> getBicicleta() {

        return this.bicicletaService.listarTodas();

    }

}
