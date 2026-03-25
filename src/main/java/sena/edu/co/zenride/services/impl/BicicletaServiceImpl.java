package sena.edu.co.zenride.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sena.edu.co.zenride.dto.request.BicicletaRequestDTO;
import sena.edu.co.zenride.dto.response.BicicletaResponseDTO;
import sena.edu.co.zenride.entities.Bicicleta;
import sena.edu.co.zenride.entities.InventarioMovimiento;
import sena.edu.co.zenride.mapper.BicicletaMapper;
import sena.edu.co.zenride.repository.BicicletaRepository;
import sena.edu.co.zenride.repository.InventarioMovimientoRepository;
import sena.edu.co.zenride.services.IBicicletaService;

import java.util.List;

@Service
public class BicicletaServiceImpl implements IBicicletaService {

    @Autowired
    private BicicletaRepository bicicletaRepository;

    @Autowired
    private InventarioMovimientoRepository movimientoRepository;

    @Autowired
    private BicicletaMapper bicicletaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BicicletaResponseDTO> listarTodas() {
        List<Bicicleta> bicicletas = bicicletaRepository.findAll();
        List<BicicletaResponseDTO> dtos = bicicletaMapper.toResponseDTOList(bicicletas);

        // Lógica para el "Gran Total" de la categoría (Ej: 25 unidades de RUTA)
        for (BicicletaResponseDTO dto : dtos) {
            Integer sumaTotal = dtos.stream()
                    .filter(b -> b.getTipo() != null && b.getTipo().equalsIgnoreCase(dto.getTipo()))
                    .mapToInt(BicicletaResponseDTO::getStockActual)
                    .sum();

            dto.setStockTotalPorTipo(sumaTotal);
        }

        return dtos;
    }

    @Override
    @Transactional
    public void registrarEntrada(String codigoBicicleta, Integer cantidad, String responsable, String tipoMovimiento) {
        // CAMBIO CLAVE: Ahora buscamos por el campo 'codigo' (String) y no por Long ID
        Bicicleta bici = bicicletaRepository.findByCodigo(codigoBicicleta)
                .orElseThrow(() -> new RuntimeException("Bicicleta con código " + codigoBicicleta + " no encontrada"));

        InventarioMovimiento.TipoMovimiento movimiento = null;

        // Actualización de stock individual (LTD o Lotin)
        if (tipoMovimiento.equalsIgnoreCase("ENTRADA")){
            movimiento = InventarioMovimiento.TipoMovimiento.ENTRADA;
            bici.setStockActual(bici.getStockActual() + cantidad);
        } else if (tipoMovimiento.equalsIgnoreCase("SALIDA")) {
            movimiento = InventarioMovimiento.TipoMovimiento.SALIDA;
            bici.setStockActual(bici.getStockActual() - cantidad);
        }

        bicicletaRepository.save(bici);

        // Registro de Auditoría
        InventarioMovimiento mov = new InventarioMovimiento();
        mov.setBicicleta(bici);
        mov.setCantidad(cantidad);
        mov.setTipoMovimiento(movimiento);
        mov.setResponsableOperacion(responsable);
        mov.setDescripcion("Movimiento registrado por código: " + codigoBicicleta);
        movimientoRepository.save(mov);
    }

    // Los demás métodos se mantienen igual para la gestión administrativa
    @Override
    @Transactional(readOnly = true)
    public BicicletaResponseDTO buscarPorId(Long id) {
        Bicicleta bici = bicicletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada"));
        return bicicletaMapper.toResponseDTO(bici);
    }

    @Override
    @Transactional
    public BicicletaResponseDTO guardar(BicicletaRequestDTO request) {
        Bicicleta bicicleta = bicicletaMapper.toEntity(request);
        Bicicleta guardada = bicicletaRepository.save(bicicleta);
        return bicicletaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        bicicletaRepository.deleteById(id);
    }
}