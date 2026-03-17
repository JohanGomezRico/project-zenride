package sena.edu.co.zenride.services;

import sena.edu.co.zenride.dto.request.BicicletaRequestDTO;
import sena.edu.co.zenride.dto.response.BicicletaResponseDTO;
import sena.edu.co.zenride.model.Bicicletas;
import java.util.List;
import java.util.Optional;

public interface BicicletaService {
    List<Bicicletas> listarTodas();
    Optional<Bicicletas> buscarPorId(Long id);
    BicicletaResponseDTO guardar(BicicletaRequestDTO bicicletaRequestDTO);
    void eliminar(Long id);

    // Requerimiento: Buscar por código único (útil para el buscador del Front)
    Optional<Bicicletas> buscarPorCodigo(String codigo);

    // Útil para filtros en la tienda virtual/inventario
    List<Bicicletas> listarPorTipo(Bicicletas.TipoBicicleta tipo);
}
