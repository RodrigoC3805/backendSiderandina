package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.CotizacionRequest;
import dsw.backendSiderandina.dto.CotizacionResponse;
import dsw.backendSiderandina.model.*;
import dsw.backendSiderandina.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CotizacionService {
    @Autowired
    private CotizacionRepository cotizacionRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EstadoCotizacionRepository estadoCotizacionRepository;

    public List<CotizacionResponse> listCotizaciones() {
        List<Cotizacion> cotizaciones = cotizacionRepository.findAll();
        return cotizaciones.stream()
                           .map(CotizacionResponse::fromEntity)
                           .collect(Collectors.toList());
    }

    @Transactional 
    public CotizacionResponse createCotizacion(CotizacionRequest request) {
        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + request.getIdCliente()));
        EstadoCotizacion estado = estadoCotizacionRepository.findById(request.getIdEstadoCot())
                .orElseThrow(() -> new EntityNotFoundException("Estado de cotización no encontrado: " + request.getIdEstadoCot()));

        Cotizacion cotizacion = Cotizacion.builder()
                .cliente(cliente)
                .estadoCotizacion(estado)
                .codigoCotizacion(request.getCodigoCotizacion())
                .fechaEmision(request.getFechaEmision() != null ? request.getFechaEmision() : new java.sql.Timestamp(System.currentTimeMillis()))
                .montoSubtotal(request.getMontoSubtotal())
                .montoIgv(request.getMontoIgv())
                .montoTotal(request.getMontoTotal())
                .descuento(request.getDescuento())
                .build();
        
        Cotizacion savedCotizacion = cotizacionRepository.save(cotizacion);
        
        return CotizacionResponse.fromEntity(savedCotizacion);
    }
}
