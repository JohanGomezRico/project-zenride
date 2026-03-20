package sena.edu.co.zenride.services;

import sena.edu.co.zenride.dto.request.VentaRequestDTO;
import sena.edu.co.zenride.dto.response.VentaResponseDTO;
import java.util.List;

public interface IVentaService {
    // Debe coincidir con lo que el Controller envía y recibe
    VentaResponseDTO realizarVenta(VentaRequestDTO request);
    List<VentaResponseDTO> listarVentas();
}