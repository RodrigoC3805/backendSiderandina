package dsw.backendSiderandina.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dsw.backendSiderandina.dto.CotizacionRequest;
import dsw.backendSiderandina.dto.CotizacionResponse;
import dsw.backendSiderandina.dto.DetalleCotizacionResponse;
import dsw.backendSiderandina.model.Cliente;
import dsw.backendSiderandina.model.Cotizacion;
import dsw.backendSiderandina.model.DetalleCotizacion;
import dsw.backendSiderandina.model.Producto;
import dsw.backendSiderandina.repository.ClienteRepository;
import dsw.backendSiderandina.repository.CotizacionRepository;
import dsw.backendSiderandina.repository.DetalleCotizacionRepository;
import dsw.backendSiderandina.repository.EstadoCotizacionRepository;
import dsw.backendSiderandina.repository.ProductoRepository;

@Service
public class CotizacionService {
    @Autowired
    CotizacionRepository cotizacionRepository;
    @Autowired
    EstadoCotizacionRepository estadoCotizacionRepository;
    @Autowired
    DetalleCotizacionRepository detalleCotizacionRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ProductoRepository productoRepository;

    public CotizacionResponse crearCotizacion(CotizacionRequest request) {
        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Cotizacion cotizacion = Cotizacion.builder()
                .cliente(cliente)
                .codigoCotizacion("COT-" + System.currentTimeMillis())
                .fechaEmision(Timestamp.from(Instant.now()))
                .descuento(0.0)
                .estadoCotizacion(estadoCotizacionRepository.findById(
                        1)
                        .get()) // Estado inicial
                .build();

        cotizacion = cotizacionRepository.save(cotizacion);

        for (CotizacionRequest.DetalleCotizacionRequest det : request.getDetalles()) {
            Producto producto = productoRepository.findById(det.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            // double precioUnitario = producto.getPrecioVentaBase();
            // double sub = det.getCantidad() * precioUnitario;
            // subtotal += sub;

            DetalleCotizacion detalle = DetalleCotizacion.builder()
                    .cotizacion(cotizacion)
                    .producto(producto)
                    .cantidad(det.getCantidad())
                    .build();
            detalleCotizacionRepository.save(detalle);
        }

        // igv = subtotal * 0.18; // IGV 18%
        // total = subtotal + igv - descuento;

        cotizacion.setMontoSubtotal((double) 0);
        cotizacion.setMontoIgv((double) 0);
        cotizacion.setMontoTotal((double) 0);
        cotizacionRepository.save(cotizacion);
        ;
        return CotizacionResponse.builder()
                .fechaEmision(cotizacion.getFechaEmision())
                .montoSubtotal(cotizacion.getMontoSubtotal())
                .montoIgv(cotizacion.getMontoIgv())
                .montoTotal(cotizacion.getMontoTotal())
                .descuento(cotizacion.getDescuento())
                .cliente(cliente)
                .estadoCotizacion(estadoCotizacionRepository.findById(
                        1).get())
                .detalles(request.getDetalles().stream().map(det -> {
                    Producto producto = productoRepository.findById(det.getIdProducto()).orElse(null);
                    DetalleCotizacionResponse.DetalleCotizacionResponseBuilder builder = DetalleCotizacionResponse
                            .builder();
                    builder.producto(producto);
                    builder.cantidad(det.getCantidad());
                    return builder.build();
                }).collect(Collectors.toList()))
                .build();
    }

    public List<CotizacionResponse> listarCotizaciones() {
        return CotizacionResponse.fromEntities(cotizacionRepository.findAll());
    }

    public List<CotizacionResponse> listarCotizacionesPorCliente(Integer idCliente) {
        return cotizacionRepository.findAll().stream()
                .filter(c -> c.getCliente() != null && c.getCliente().getIdCliente().equals(idCliente))
                .map(c -> CotizacionResponse.fromEntity(c))
                .collect(Collectors.toList());
    }

    @Transactional
    public void actualizarPreciosYEstado(Integer idCotizacion,
            List<CotizacionRequest.DetalleCotizacionRequest> detalles) {
        Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));

        // Actualiza precios de los detalles
        for (CotizacionRequest.DetalleCotizacionRequest det : detalles) {
            if(det.getCantidad() >= productoRepository.findById(det.getIdProducto()).get().getStock()) {
                throw new IllegalArgumentException("No hay suficiente stock");
            }
            DetalleCotizacion detalle = detalleCotizacionRepository.findById(det.getIdDetalleCotizacion())
                    .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
            detalle.setPrecioCotizado(det.getPrecioCotizado());
            detalle.setMontoSubtotalLinea(det.getCantidad() * det.getPrecioCotizado());
            detalleCotizacionRepository.save(detalle);
        }

        // Recalcula totales
        double subtotal = detalles.stream().mapToDouble(d -> d.getCantidad() * d.getPrecioCotizado()).sum();
        double igv = subtotal * 0.18;
        double descuento = cotizacion.getDescuento() != null ? cotizacion.getDescuento() : 0.0;
        double total = subtotal + igv - descuento;

        cotizacion.setMontoSubtotal(subtotal);
        cotizacion.setMontoIgv(igv);
        cotizacion.setMontoTotal(total);

        // Cambia estado a "Entregada"
        cotizacion.setEstadoCotizacion(estadoCotizacionRepository.findById(2).get());
        cotizacionRepository.save(cotizacion);
    }
    @Transactional
    public void rechazarCotizacion(Integer idCotizacion, List<CotizacionRequest.DetalleCotizacionRequest> detalles) {
        Cotizacion cotizacion = cotizacionRepository.findById(idCotizacion)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));
        // Actualiza precios de los detalles
        for (CotizacionRequest.DetalleCotizacionRequest det : detalles) {
            DetalleCotizacion detalle = detalleCotizacionRepository.findById(det.getIdDetalleCotizacion())
                    .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
            detalle.setPrecioCotizado(0.0);
            detalle.setMontoSubtotalLinea(0.0);
            detalleCotizacionRepository.save(detalle);
        }
        cotizacion.setMontoSubtotal(0.0);
        cotizacion.setMontoIgv(0.0);
        cotizacion.setMontoTotal(0.0);
        // Cambia estado a "Rechazada"
        cotizacion.setEstadoCotizacion(estadoCotizacionRepository.findById(3).get());
        cotizacionRepository.save(cotizacion);
    }
}