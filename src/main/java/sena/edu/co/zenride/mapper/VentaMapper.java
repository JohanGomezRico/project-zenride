package sena.edu.co.zenride.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sena.edu.co.zenride.dto.response.DetalleVentaResponseDTO;
import sena.edu.co.zenride.dto.response.VentaResponseDTO;
import sena.edu.co.zenride.entities.DetalleVenta;
import sena.edu.co.zenride.entities.Venta;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VentaMapper {

    @Mapping(source = "cliente.nombre", target = "nombreCliente")
    @Mapping(source = "cliente.documento", target = "documento") // 👈 ESTA ES LA LÍNEA QUE FALTA
    VentaResponseDTO toResponseDTO(Venta entidad);

    @Mapping(source = "bicicleta.codigo", target = "codigoBicicleta")
    @Mapping(source = "bicicleta.modelo", target = "modeloBicicleta")// 👈 Aprovecha para asegurar la marca
    @Mapping(source = "precioUnitarioVenta", target = "precioVenta")
    DetalleVentaResponseDTO toDetalleResponseDTO(DetalleVenta detalle);

    List<VentaResponseDTO> toResponseList(List<Venta> entidades);
}