package sena.edu.co.zenride.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sena.edu.co.zenride.dto.request.BicicletaRequestDTO;
import sena.edu.co.zenride.dto.response.BicicletaResponseDTO;
import sena.edu.co.zenride.model.Bicicletas;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBicicletaMapper {
    Bicicletas toEntity (BicicletaRequestDTO bicicletaRequestDTO);

    BicicletaResponseDTO toResponse(Bicicletas bicicletas);
}
