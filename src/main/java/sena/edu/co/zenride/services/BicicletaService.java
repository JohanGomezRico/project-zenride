package sena.edu.co.zenride.services;

import sena.edu.co.zenride.model.Bicicletas;

import java.util.List;

public interface BicicletaService {

    public List<Bicicletas> totalBicicletas();

    public Bicicletas buscarPorId(Long idBicicleta);
}
