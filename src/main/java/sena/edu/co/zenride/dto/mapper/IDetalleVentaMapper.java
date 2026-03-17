package sena.edu.co.zenride.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import sena.edu.co.zenride.dto.request.DetalleVentaRequestDTO;
import sena.edu.co.zenride.dto.response.DetalleVentaResponseDTO;
import sena.edu.co.zenride.model.DetalleVenta;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDetalleVentaMapper {

    // Mapeamos el ID que manda Angular hacia el objeto Bicicleta de la entidad
    @Mapping(source = "idBicicleta", target = "bicicleta.idBicicleta")
    DetalleVenta toEntity(DetalleVentaRequestDTO requestDTO);

    // Aplanamos los datos de la bicicleta para la respuesta
    @Mapping(source = "bicicleta.marcaBicicleta", target = "marcaBicicleta")
    @Mapping(source = "bicicleta.modeloBicicleta", target = "modeloBicicleta")
    @Mapping(source = "precioUnitarioVenta", target = "precioUnitario")
    DetalleVentaResponseDTO toResponse(DetalleVenta detalleVenta);
}
