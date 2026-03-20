package sena.edu.co.zenride.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sena.edu.co.zenride.dtos.request.BicicletaRequestDTO;
import sena.edu.co.zenride.dtos.response.BicicletaResponseDTO;
import sena.edu.co.zenride.entities.Bicicleta;

import java.util.List;

@Mapper(componentModel = "spring") // Importante para que Spring lo pueda inyectar
public interface BicicletaMapper {

    // Convierte lo que llega de Angular a la Entidad para la DB
    Bicicleta toEntity(BicicletaRequestDTO request);

    // Convierte la Entidad de la DB a lo que Angular va a mostrar
    BicicletaResponseDTO toResponseDTO(Bicicleta entidad);

    // Convierte una lista completa (útil para el Listar Todas)
    List<BicicletaResponseDTO> toResponseDTOList(List<Bicicleta> lista);
}
