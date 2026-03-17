package sena.edu.co.zenride.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sena.edu.co.zenride.dto.request.ClienteRequestDTO;
import sena.edu.co.zenride.dto.response.ClienteResponseDTO;
import sena.edu.co.zenride.model.Cliente;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IClienteMapper {

    Cliente toEntity(ClienteRequestDTO requestDTO);

    ClienteResponseDTO toResponse(Cliente cliente);
}
