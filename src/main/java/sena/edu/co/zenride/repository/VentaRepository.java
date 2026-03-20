package sena.edu.co.zenride.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sena.edu.co.zenride.entities.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Aquí podrías luego agregar búsquedas por rango de fechas
}