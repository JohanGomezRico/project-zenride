package sena.edu.co.zenride.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import sena.edu.co.zenride.dto.request.InventarioMovimientoRequestDTO;
import sena.edu.co.zenride.dto.response.InventarioMovimientoResponseDTO;
import sena.edu.co.zenride.model.InventarioMovimiento;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IInventarioMovimientoMapper {

    @Mapping(source = "idBicicleta", target = "bicicleta.idBicicleta")
    InventarioMovimiento toEntity(InventarioMovimientoRequestDTO requestDTO);

    @Mapping(source = "bicicleta.modeloBicicleta", target = "modeloBicicleta")
    InventarioMovimientoResponseDTO toResponse(InventarioMovimiento movimiento);
}
