package dsw.backendSiderandina.dto;

import java.util.List;
import java.util.stream.Collectors;

import dsw.backendSiderandina.model.Cotizacion;
import dsw.backendSiderandina.model.DetalleCotizacion;
import dsw.backendSiderandina.model.Producto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCotizacionResponse {
    private Integer idDetalleCotizacion;
    private Producto producto;
    private Cotizacion cotizacion;
    private Double cantidad;
    private Double precioCotizado;
    private Double montoSubtotalLinea;
    public static DetalleCotizacionResponse fromEntity(DetalleCotizacion detalleCotizacion) {
        return DetalleCotizacionResponse.builder()
                .idDetalleCotizacion(detalleCotizacion.getIdDetalleCotizacion())
                .producto(detalleCotizacion.getProducto())
                .cotizacion(detalleCotizacion.getCotizacion())
                .cantidad(detalleCotizacion.getCantidad())
                .precioCotizado(detalleCotizacion.getPrecioCotizado())
                .montoSubtotalLinea(detalleCotizacion.getMontoSubtotalLinea())
                .build();
    }
    public static List<DetalleCotizacionResponse> fromEntities(List<DetalleCotizacion> listaDetalleCotizacion){
        return listaDetalleCotizacion.stream()
                .map(DetalleCotizacionResponse::fromEntity)
                .collect(Collectors.toList());
    }
}