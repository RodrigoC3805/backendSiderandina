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
public class PedidoCompraRequest {
    private Integer idPedidoCompra;
    private Integer codigoCompra;
    private Timestamp fechaPedido;
    private Double montoTotal;
    private Integer idProveedor;
    private Integer idEstadoPedido;
}
