package sena.edu.co.zenride.repository;

import sena.edu.co.zenride.model.InventarioMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioMovimientoRepository extends JpaRepository<InventarioMovimiento, Long> {
    List<InventarioMovimiento> findByBicicleta_IdBicicleta(Long idBicicleta);
}
