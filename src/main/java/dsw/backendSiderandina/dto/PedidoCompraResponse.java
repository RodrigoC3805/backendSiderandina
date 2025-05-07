package dsw.backendSiderandina.dto;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import dsw.backendSiderandina.model.EstadoPedido;
import dsw.backendSiderandina.model.PedidoCompra;
import dsw.backendSiderandina.model.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCompraResponse {
    private Integer idPedidoCompra;
    private Integer codigoCompra;
    private Timestamp fechaPedido;
    private Double montoTotal;
    private Proveedor proveedor;
    private EstadoPedido estadoPedido;

    public static PedidoCompraResponse fromEntity(PedidoCompra pedidoCompra) {
        return PedidoCompraResponse.builder()
                .idPedidoCompra(pedidoCompra.getIdPedidoCompra())
                .codigoCompra(pedidoCompra.getCodigoCompra())
                .fechaPedido(pedidoCompra.getFechaPedido())
                .montoTotal(pedidoCompra.getMontoTotal())
                .proveedor(pedidoCompra.getProveedor())
                .estadoPedido(pedidoCompra.getEstadoPedido())
                .build();
    }
    public static List<PedidoCompraResponse> fromEntities(List<PedidoCompra> pedidoCompra){
        return pedidoCompra.stream()
                .map(PedidoCompraResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
