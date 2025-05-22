package dsw.backendSiderandina.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteCompraRequest {
    private Integer idComprobanteCompra;
    private Integer numeroComprobante;
    private Timestamp fechaEmision;
    private Integer idPedidoCompra;
    private Integer idPago;
    private Integer idTipoComprobante;
}
