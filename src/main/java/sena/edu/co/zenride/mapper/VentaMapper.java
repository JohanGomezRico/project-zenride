package sena.edu.co.zenride.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sena.edu.co.zenride.dto.response.VentaResponseDTO;
import sena.edu.co.zenride.entities.Venta;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VentaMapper {
    @Mapping(source = "cliente.nombre", target = "nombreCliente")
    VentaResponseDTO toResponseDTO(Venta entidad);

    List<VentaResponseDTO> toResponseList(List<Venta> entidades);
}