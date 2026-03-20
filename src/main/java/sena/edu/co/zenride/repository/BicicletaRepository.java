package sena.edu.co.zenride.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sena.edu.co.zenride.entities.Bicicleta;
import java.util.Optional;

@Repository
public interface BicicletaRepository extends JpaRepository<Bicicleta, Long> {
    // Método personalizado para buscar por el código de barras/referencia
    Optional<Bicicleta> findByCodigo(String codigo);
}