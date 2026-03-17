package sena.edu.co.zenride.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sena.edu.co.zenride.dto.mapper.IBicicletaMapper;
import sena.edu.co.zenride.dto.request.BicicletaRequestDTO;
import sena.edu.co.zenride.dto.response.BicicletaResponseDTO;
import sena.edu.co.zenride.model.Bicicletas;
import sena.edu.co.zenride.repository.BicicletaRepository;
import sena.edu.co.zenride.services.BicicletaService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BicicletaServiceImpl implements BicicletaService {

    private final BicicletaRepository bicicletaRepository;

    private final IBicicletaMapper iBicicletaMapper;

    @Override
    public List<Bicicletas> listarTodas() {
        return bicicletaRepository.findAll();
    }

    @Override
    public Optional<Bicicletas> buscarPorId(Long id) {
        return bicicletaRepository.findById(id);
    }

    @Override
    public Optional<Bicicletas> buscarPorCodigo(String codigo) {
        return bicicletaRepository.findByCodigoBicicleta(codigo);
    }

    @Override
    public List<Bicicletas> listarPorTipo(Bicicletas.TipoBicicleta tipo) {
        return bicicletaRepository.findByTipoBicicleta(tipo);
    }

    @Override
    public BicicletaResponseDTO guardar(BicicletaRequestDTO bicicletaRequestDTO) {
        Bicicletas bicicleta = bicicletaRepository.save(iBicicletaMapper.toEntity(bicicletaRequestDTO));
        return iBicicletaMapper.toResponse(bicicleta);
    }


    @Override
    public void eliminar(Long id) {
        bicicletaRepository.deleteById(id);
    }
}
