package sena.edu.co.zenride.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sena.edu.co.zenride.dto.response.InventarioMovimientoResponseDTO;
import sena.edu.co.zenride.entities.InventarioMovimiento;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarioMovimientoMapper {

    @Mapping(source = "bicicleta.modelo", target = "nombreBicicleta")
    @Mapping(source = "bicicleta.codigo", target = "codigoBicicleta")
    InventarioMovimientoResponseDTO toResponseDTO(InventarioMovimiento entidad);

    // Este método soluciona el error de "Incompatible Bounds"
    List<InventarioMovimientoResponseDTO> toResponseList(List<InventarioMovimiento> entidades);
}