package dsw.backendSiderandina.dto;

import java.util.List;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionRequest {
    private Integer idCliente;
    private List<DetalleCotizacionRequest> detalles;
    private Double descuento;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetalleCotizacionRequest {
        private Integer idProducto;
        private Integer cantidad;
    }
}