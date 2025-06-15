package dsw.backendSiderandina.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import dsw.backendSiderandina.model.Cliente;
import dsw.backendSiderandina.model.Cotizacion;
import dsw.backendSiderandina.model.EstadoCotizacion;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionResponse {
    private Integer idCotizacion;
    private String codigoCotizacion;
    private Timestamp fechaEmision;
    private Double montoSubtotal;
    private Double montoIgv;
    private Double montoTotal;
    private Double descuento;
    private Cliente cliente;
    private EstadoCotizacion estadoCotizacion;
    private List<DetalleCotizacionResponse> detalles;

    public static CotizacionResponse fromEntity(Cotizacion cotizacion) {
        return CotizacionResponse.builder()
                .idCotizacion(cotizacion.getIdCotizacion())
                .codigoCotizacion(cotizacion.getCodigoCotizacion())
                .fechaEmision(cotizacion.getFechaEmision())
                .montoSubtotal(cotizacion.getMontoSubtotal())
                .montoIgv(cotizacion.getMontoIgv())
                .montoTotal(cotizacion.getMontoTotal())
                .descuento(cotizacion.getDescuento())
                .cliente(cotizacion.getCliente())
                .estadoCotizacion(cotizacion.getEstadoCotizacion())
                .detalles(DetalleCotizacionResponse.fromEntities(cotizacion.getDetalles()))
                .build();
    }
    public static List<CotizacionResponse> fromEntities(List<Cotizacion> listaCotizacion){
        return listaCotizacion.stream()
                .map(CotizacionResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
}