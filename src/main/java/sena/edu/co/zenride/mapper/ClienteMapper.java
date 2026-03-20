package sena.edu.co.zenride.mapper;

import org.mapstruct.Mapper;
import sena.edu.co.zenride.dto.request.ClienteRequestDTO;
import sena.edu.co.zenride.dto.response.ClienteResponseDTO;
import sena.edu.co.zenride.entities.Cliente;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteResponseDTO toResponseDTO(Cliente entidad);
    Cliente toEntity(ClienteRequestDTO request);
    List<ClienteResponseDTO> toResponseList(List<Cliente> entidades);
}