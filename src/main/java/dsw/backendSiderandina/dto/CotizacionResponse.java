package dsw.backendSiderandina.dto;

import java.sql.Timestamp;
import java.util.List;
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
    private Integer idCliente;
    private Integer idEstadoCot;
    private List<DetalleCotizacionResponse> detalles;
}