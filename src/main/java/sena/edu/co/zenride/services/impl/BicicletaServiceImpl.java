package sena.edu.co.zenride.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sena.edu.co.zenride.model.Bicicletas;
import sena.edu.co.zenride.repository.BicicletaRepository;
import sena.edu.co.zenride.services.BicicletaService;

import java.util.List;

@Service
public class BicicletaServiceImpl implements BicicletaService {

    @Autowired
    private BicicletaRepository bicicletaRepository;


    @Override
    public List<Bicicletas> totalBicicletas() {
        return bicicletaRepository.findAll();
    }

    @Override
    public Bicicletas buscarPorId(Long idBicicleta) {
        return null;
    }
}
