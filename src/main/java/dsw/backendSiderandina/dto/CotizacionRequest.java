package dsw.backendSiderandina.dto;

import java.sql.Timestamp;
// import java.util.List; // No se necesita para detalles
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionRequest {
    private Integer idCotizacion;
    private Integer idCliente;
    private Integer idEstadoCot;
    private String codigoCotizacion;
    private Timestamp fechaEmision;
    private Double montoSubtotal;
    private Double montoIgv;
    private Double montoTotal;
    private Double descuento;
}