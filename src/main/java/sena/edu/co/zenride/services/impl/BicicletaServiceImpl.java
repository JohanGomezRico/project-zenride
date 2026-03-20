package sena.edu.co.zenride.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sena.edu.co.zenride.dtos.request.BicicletaRequestDTO;
import sena.edu.co.zenride.dtos.response.BicicletaResponseDTO;
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
    private BicicletaMapper bicicletaMapper; // Inyectamos el Mapper

    @Override
    public List<BicicletaResponseDTO> listarTodas() {
        List<Bicicleta> bicicletas = bicicletaRepository.findAll();
        // Usamos el mapper para convertir la lista de entidades a DTOs
        return bicicletaMapper.toResponseDTOList(bicicletas);
    }

    @Override
    public BicicletaResponseDTO buscarPorId(Long id) {
        Bicicleta bici = bicicletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada"));
        return bicicletaMapper.toResponseDTO(bici);
    }

    @Override
    @Transactional
    public BicicletaResponseDTO guardar(BicicletaRequestDTO request) {
        // Convertimos el DTO que llega de Angular a una Entidad para MySQL
        Bicicleta bicicleta = bicicletaMapper.toEntity(request);
        Bicicleta guardada = bicicletaRepository.save(bicicleta);
        // Devolvemos el DTO de respuesta
        return bicicletaMapper.toResponseDTO(guardada);
    }

    @Override
    @Transactional
    public void registrarEntrada(Long idBicicleta, Integer cantidad, String responsable) {
        // Buscamos la entidad original
        Bicicleta bici = bicicletaRepository.findById(idBicicleta)
                .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada"));

        // Lógica de negocio (Stock)
        bici.setStockActual(bici.getStockActual() + cantidad);
        bicicletaRepository.save(bici);

        // Registro de Auditoría
        InventarioMovimiento mov = new InventarioMovimiento();
        mov.setBicicleta(bici);
        mov.setCantidad(cantidad);
        mov.setTipoMovimiento(InventarioMovimiento.TipoMovimiento.ENTRADA);
        mov.setResponsableOperacion(responsable);
        mov.setDescripcion("Ingreso de mercancía al almacén");
        movimientoRepository.save(mov);
    }

    @Override
    public void eliminar(Long id) {
        bicicletaRepository.deleteById(id);
    }
}