package sena.edu.co.zenride.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sena.edu.co.zenride.dto.request.VentaRequestDTO;
import sena.edu.co.zenride.dto.request.DetalleVentaRequestDTO;
import sena.edu.co.zenride.dto.response.VentaResponseDTO;
import sena.edu.co.zenride.entities.*;
import sena.edu.co.zenride.mapper.VentaMapper;
import sena.edu.co.zenride.repository.*;
import sena.edu.co.zenride.services.IVentaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaServiceImpl implements IVentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private BicicletaRepository bicicletaRepository;
    @Autowired private InventarioMovimientoRepository movimientoRepository;
    @Autowired private VentaMapper ventaMapper;

    @Override
    @Transactional
    public VentaResponseDTO realizarVenta(VentaRequestDTO request) {
        // 1. Buscar Cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFechaVenta(LocalDateTime.now());

        List<DetalleVenta> detalles = new ArrayList<>();
        double totalFactura = 0;

        // 2. Procesar detalles desde el DTO
        for (DetalleVentaRequestDTO item : request.getDetalles()) {
            Bicicleta bici = bicicletaRepository.findById(item.getBicicletaId())
                    .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada"));

            if (bici.getStockActual() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + bici.getModelo());
            }

            // Actualizar Stock
            bici.setStockActual(bici.getStockActual() - item.getCantidad());
            bicicletaRepository.save(bici);

            // Crear Detalle de Entidad
            DetalleVenta detalle = new DetalleVenta();
            detalle.setBicicleta(bici);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitarioVenta(bici.getPrecioVenta());
            detalle.setSubtotal(bici.getPrecioVenta() * item.getCantidad());
            detalle.setVenta(venta);
            detalles.add(detalle);

            totalFactura += detalle.getSubtotal();

            // Auditoría de movimiento
            registrarSalida(bici, item.getCantidad());
        }

        venta.setDetalles(detalles);
        venta.setTotalVenta(totalFactura);

        // 3. Guardar y convertir a ResponseDTO
        Venta guardada = ventaRepository.save(venta);
        return ventaMapper.toResponseDTO(guardada);
    }

    private void registrarSalida(Bicicleta bici, Integer cantidad) {
        InventarioMovimiento mov = new InventarioMovimiento();
        mov.setBicicleta(bici);
        mov.setCantidad(cantidad);
        mov.setTipoMovimiento(InventarioMovimiento.TipoMovimiento.SALIDA);
        mov.setResponsableOperacion("Sistema ZenRide");
        movimientoRepository.save(mov);
    }

    @Override
    public List<VentaResponseDTO> listarVentas() {
        return ventaMapper.toResponseList(ventaRepository.findAll());
    }
}