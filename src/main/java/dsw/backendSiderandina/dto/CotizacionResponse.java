package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.Cotizacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionResponse {
    private Integer idCotizacion;
    private ClienteResponse cliente;
    private EstadoCotizacionResponse estadoCotizacion;
    private String codigoCotizacion;
    private Timestamp fechaEmision;
    private Double montoSubtotal;
    private Double montoIgv;
    private Double montoTotal;
    private Double descuento;
    // Se elimina: private List<DetalleCotizacionResponse> detalles;

    public static CotizacionResponse fromEntity(Cotizacion cotizacion) {
        if (cotizacion == null) return null;
        return CotizacionResponse.builder()
                .idCotizacion(cotizacion.getIdCotizacion())
                .cliente(ClienteResponse.fromEntity(cotizacion.getCliente()))
                .estadoCotizacion(EstadoCotizacionResponse.fromEntity(cotizacion.getEstadoCotizacion()))
                .codigoCotizacion(cotizacion.getCodigoCotizacion())
                .fechaEmision(cotizacion.getFechaEmision())
                .montoSubtotal(cotizacion.getMontoSubtotal())
                .montoIgv(cotizacion.getMontoIgv())
                .montoTotal(cotizacion.getMontoTotal())
                .descuento(cotizacion.getDescuento())
                .build();
    }

    public static List<CotizacionResponse> fromEntities(List<Cotizacion> cotizaciones) {
        return cotizaciones.stream()
                .map(CotizacionResponse::fromEntity)
                .collect(Collectors.toList());
    }
}