package sena.edu.co.zenride.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import sena.edu.co.zenride.dto.request.VentaRequestDTO;
import sena.edu.co.zenride.dto.response.VentaResponseDTO;
import sena.edu.co.zenride.model.Venta;

@Mapper(componentModel = "spring",
        uses = {IDetalleVentaMapper.class}, // Clave para que mapee la lista de detalles
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IVentaMapper {

    @Mapping(source = "idCliente", target = "cliente.id")
    Venta toEntity(VentaRequestDTO requestDTO);

    @Mapping(source = "id", target = "idVenta")
    @Mapping(source = "cliente.nombre", target = "nombreCliente")
    VentaResponseDTO toResponse(Venta venta);
}