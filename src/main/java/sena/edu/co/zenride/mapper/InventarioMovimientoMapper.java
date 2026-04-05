package sena.edu.co.zenride.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sena.edu.co.zenride.dto.response.InventarioMovimientoResponseDTO;
import sena.edu.co.zenride.entities.InventarioMovimiento;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarioMovimientoMapper {

    @Mapping(source = "bicicleta.codigo", target = "bicicletaCodigo")
    // Cambiamos 'modelo' por 'marca' o lo que tengas en tu Entidad Bicicleta
    @Mapping(source = "bicicleta.marca", target = "nombreBicicleta")
    @Mapping(source = "descripcion", target = "descripcion")
    InventarioMovimientoResponseDTO toResponseDTO(InventarioMovimiento entidad);

    List<InventarioMovimientoResponseDTO> toResponseList(List<InventarioMovimiento> entidades);
}