package sena.edu.co.zenride.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sena.edu.co.zenride.dto.response.InventarioMovimientoResponseDTO;
import sena.edu.co.zenride.entities.InventarioMovimiento;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarioMovimientoMapper {

    // CAMBIO: El target debe ser 'bicicletaCodigo' (como está en tu DTO)
    @Mapping(source = "bicicleta.codigo", target = "bicicletaCodigo")
    // Opcional: Si quieres mostrar el modelo, asegúrate que el DTO tenga 'nombreBicicleta'
    @Mapping(source = "bicicleta.modelo", target = "nombreBicicleta")

    @Mapping(source = "descripcion", target = "descripcion")
    InventarioMovimientoResponseDTO toResponseDTO(InventarioMovimiento entidad);

    List<InventarioMovimientoResponseDTO> toResponseList(List<InventarioMovimiento> entidades);
}