package dsw.backendSiderandina.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.PedidoCompraRequest;
import dsw.backendSiderandina.dto.PedidoCompraResponse;
import dsw.backendSiderandina.model.ComprobanteCompra;
import dsw.backendSiderandina.model.DetalleCompra;
import dsw.backendSiderandina.model.EstadoPedido;
import dsw.backendSiderandina.model.Pago;
import dsw.backendSiderandina.model.PedidoCompra;
import dsw.backendSiderandina.model.Proveedor;
import dsw.backendSiderandina.repository.ComprobanteCompraRepository;
import dsw.backendSiderandina.repository.DetalleCompraRepository;
import dsw.backendSiderandina.repository.EstadoPedidoRepository;
import dsw.backendSiderandina.repository.PagoRepository;
import dsw.backendSiderandina.repository.PedidoCompraRepository;
import dsw.backendSiderandina.repository.ProveedorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PedidoCompraService {
    @Autowired
    PedidoCompraRepository pedidoCompraRepository;
    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    EstadoPedidoRepository estadoPedidoRepository;
    @Autowired
    DetalleCompraRepository detalleCompraRepository;
    @Autowired
    ComprobanteCompraRepository comprobanteCompraRepository;
    @Autowired
    PagoRepository pagoRepository;

    public List<PedidoCompraResponse> listPedidosCompra() {
        return PedidoCompraResponse.fromEntities(pedidoCompraRepository.findAll());
    }

    public PedidoCompraResponse createPedidoCompra(PedidoCompraRequest request) {
        PedidoCompra pedidoCompra = PedidoCompra.builder()
                .fechaPedido(Timestamp.from(Instant.now()))
                .montoTotal(request.getMontoTotal())
                .proveedor(proveedorRepository.findById(request.getIdProveedor())
                        .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado")))
                .estadoPedido(estadoPedidoRepository.findById(request.getIdEstadoPedido())
                        .orElseThrow(() -> new EntityNotFoundException("Estado de pedido no encontrado")))
                .build();
        PedidoCompra savedPedido = pedidoCompraRepository.save(pedidoCompra);
        return PedidoCompraResponse.fromEntity(savedPedido);
    }

    @Transactional
    public PedidoCompra createPedidoCompraConDetalles(PedidoCompra pedidoCompra, List<DetalleCompra> detallesCompra,
            Pago pago, ComprobanteCompra comprobanteCompra) {
        // 1. Validar y asociar EstadoPedido
        if (pedidoCompra.getEstadoPedido() == null || pedidoCompra.getEstadoPedido().getIdEstadoPedido() == null) {
            throw new RuntimeException("El objeto EstadoPedido o su ID no pueden ser nulos.");
        }
        // Buscar EstadoPedido en la base de datos
        EstadoPedido estadoPedido = estadoPedidoRepository.findById(pedidoCompra.getEstadoPedido().getIdEstadoPedido())
                .orElseThrow(() -> new RuntimeException(
                        "EstadoPedido no encontrado con el ID: " + pedidoCompra.getEstadoPedido().getIdEstadoPedido()));
        pedidoCompra.setEstadoPedido(estadoPedido);

        // 2. Validar y asociar Proveedor
        if (pedidoCompra.getProveedor() == null || pedidoCompra.getProveedor().getIdProveedor() == null) {
            throw new RuntimeException("El objeto Proveedor o su ID no pueden ser nulos.");
        }
        // Buscar Proveedor en la base de datos
        Proveedor proveedor = proveedorRepository.findById(pedidoCompra.getProveedor().getIdProveedor())
                .orElseThrow(() -> new RuntimeException(
                        "Proveedor no encontrado con el ID: " + pedidoCompra.getProveedor().getIdProveedor()));
        pedidoCompra.setProveedor(proveedor);

        // 3. Guardar PedidoCompra
        PedidoCompra savedPedido = pedidoCompraRepository.save(pedidoCompra);

        // 4. Asociar y guardar DetallesCompra
        for (DetalleCompra detalle : detallesCompra) {
            detalle.setPedidoCompra(savedPedido); // Asociar el pedido al detalle
            detalleCompraRepository.save(detalle);
        }

        // 5. Guardar el Pago (si existe)
        if (pago != null) {
            pagoRepository.save(pago);
        }

        // 6. Guardar el ComprobanteCompra (si existe)
        if (comprobanteCompra != null) {
            comprobanteCompra.setPedidoCompra(savedPedido); // Asociar el pedido al comprobante
            comprobanteCompraRepository.save(comprobanteCompra);
        }

        return savedPedido;
    }
}
