package dsw.backendSiderandina.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.CotizacionRequest;
import dsw.backendSiderandina.dto.CotizacionResponse;
import dsw.backendSiderandina.dto.DetalleCotizacionResponse;
import dsw.backendSiderandina.model.Cliente;
import dsw.backendSiderandina.model.Cotizacion;
import dsw.backendSiderandina.model.DetalleCotizacion;
import dsw.backendSiderandina.model.DetalleCotizacionId;
import dsw.backendSiderandina.model.Producto;
import dsw.backendSiderandina.repository.ClienteRepository;
import dsw.backendSiderandina.repository.CotizacionRepository;
import dsw.backendSiderandina.repository.DetalleCotizacionRepository;
import dsw.backendSiderandina.repository.ProductoRepository;

@Service
public class CotizacionService {
    @Autowired
    CotizacionRepository cotizacionRepository;
    @Autowired
    DetalleCotizacionRepository detalleCotizacionRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ProductoRepository productoRepository;

    public CotizacionResponse crearCotizacion(CotizacionRequest request) {
    // Validación de idCliente
    if (request.getIdCliente() == null) {
        throw new IllegalArgumentException("El idCliente no puede ser null");
    }
    // Validación de detalles
    if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
        throw new IllegalArgumentException("La lista de detalles no puede estar vacía");
    }
    for (CotizacionRequest.DetalleCotizacionRequest det : request.getDetalles()) {
        if (det.getIdProducto() == null) {
            throw new IllegalArgumentException("El idProducto no puede ser null");
        }
    }

    Cliente cliente = clienteRepository.findById(request.getIdCliente())
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

    double subtotal = 0.0;
    double igv = 0.0;
    double descuento = request.getDescuento() != null ? request.getDescuento() : 0.0;
    double total = 0.0;

    Cotizacion cotizacion = Cotizacion.builder()
        .cliente(cliente)
        .codigoCotizacion("COT-" + System.currentTimeMillis())
        .fechaEmision(Timestamp.from(Instant.now()))
        .descuento(descuento)
        .idEstadoCot(1) // Estado inicial
        .build();

    cotizacion = cotizacionRepository.save(cotizacion);

    for (CotizacionRequest.DetalleCotizacionRequest det : request.getDetalles()) {
        Producto producto = productoRepository.findById(det.getIdProducto())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        double precioUnitario = producto.getPrecioVentaBase();
        double sub = det.getCantidad() * precioUnitario;
        subtotal += sub;

        DetalleCotizacion detalle = DetalleCotizacion.builder()
            .id(new DetalleCotizacionId(cotizacion.getIdCotizacion(), producto.getIdProducto()))
            .cotizacion(cotizacion)
            .producto(producto)
            .cantidad(det.getCantidad())
            .build();
        detalleCotizacionRepository.save(detalle);
    }

    igv = subtotal * 0.18; // IGV 18%
    total = subtotal + igv - descuento;

    cotizacion.setMontoSubtotal(subtotal);
    cotizacion.setMontoIgv(igv);
    cotizacion.setMontoTotal(total);
    cotizacionRepository.save(cotizacion);

    return CotizacionResponse.builder()
        .idCotizacion(cotizacion.getIdCotizacion())
        .codigoCotizacion(cotizacion.getCodigoCotizacion())
        .fechaEmision(cotizacion.getFechaEmision())
        .montoSubtotal(cotizacion.getMontoSubtotal())
        .montoIgv(cotizacion.getMontoIgv())
        .montoTotal(cotizacion.getMontoTotal())
        .descuento(cotizacion.getDescuento())
        .idCliente(cliente.getIdCliente())
        .idEstadoCot(cotizacion.getIdEstadoCot())
        .detalles(request.getDetalles().stream().map(det -> {
            Producto producto = productoRepository.findById(det.getIdProducto()).orElse(null);
            return DetalleCotizacionResponse.builder()
                .idProducto(det.getIdProducto())
                .nombreProducto(producto != null ? producto.getNombre() : "")
                .cantidad(det.getCantidad())
                .build();
        }).collect(Collectors.toList()))
        .build();
}

    public List<CotizacionResponse> listarCotizaciones() {
        return cotizacionRepository.findAll().stream()
            .map(c -> CotizacionResponse.builder()
                .idCotizacion(c.getIdCotizacion())
                .codigoCotizacion(c.getCodigoCotizacion())
                .fechaEmision(c.getFechaEmision())
                .montoSubtotal(c.getMontoSubtotal())
                .montoIgv(c.getMontoIgv())
                .montoTotal(c.getMontoTotal())
                .descuento(c.getDescuento())
                .idCliente(c.getCliente() != null ? c.getCliente().getIdCliente() : null)
                .idEstadoCot(c.getIdEstadoCot())
                .detalles(
                    c.getDetalles() != null
                    ? c.getDetalles().stream().map(det -> DetalleCotizacionResponse.builder()
                        .idProducto(det.getProducto().getIdProducto())
                        .nombreProducto(det.getProducto().getNombre())
                        .cantidad(det.getCantidad())
                        .build()
                    ).collect(Collectors.toList())
                    : null
                )
                .build())
            .collect(Collectors.toList());
    }

    public List<CotizacionResponse> listarCotizacionesPorCliente(Integer idCliente) {
    return cotizacionRepository.findAll().stream()
        .filter(c -> c.getCliente() != null && c.getCliente().getIdCliente().equals(idCliente))
        .map(c -> CotizacionResponse.builder()
            .idCotizacion(c.getIdCotizacion())
            .codigoCotizacion(c.getCodigoCotizacion())
            .fechaEmision(c.getFechaEmision())
            .idEstadoCot(c.getIdEstadoCot())
            .build())
        .collect(Collectors.toList());
}
}