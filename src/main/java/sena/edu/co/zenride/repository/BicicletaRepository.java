package sena.edu.co.zenride.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sena.edu.co.zenride.model.Bicicletas;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface BicicletaRepository extends JpaRepository<Bicicletas,Long> {

    Optional<Bicicletas> findByprecioVenta(BigDecimal precio);



}
