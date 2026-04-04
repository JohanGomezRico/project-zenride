package sena.edu.co.zenride.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sena.edu.co.zenride.entities.InventarioMovimiento;
import java.util.List;

@Repository
public interface InventarioMovimientoRepository extends JpaRepository<InventarioMovimiento, Long> {
    // Para buscar movimientos de una bici específica
    List<InventarioMovimiento> findByBicicletaId(Long bicicletaId);
 // Buscar movimientos en un rango de fechas
    List<InventarioMovimiento> findByFechaMovimientoBetween(java.time.LocalDateTime inicio, java.time.LocalDateTime fin);

}