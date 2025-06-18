package dsw.backendSiderandina.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import dsw.backendSiderandina.model.PedidoVenta;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoVentaResponse {
    private Integer idPedidoVenta;
    private Integer idCotizacion;
    private Integer idEstadoPedido;
    private String codigoVenta;
    private Timestamp fechaPedido;
    private String direccionEntrega;

    public static PedidoVentaResponse fromEntity(PedidoVenta pedidoVenta) {
        return PedidoVentaResponse.builder()
                .idPedidoVenta(pedidoVenta.getIdPedidoVenta())
                .idCotizacion(pedidoVenta.getCotizacion().getIdCotizacion())
                .idEstadoPedido(pedidoVenta.getEstadoPedido().getIdEstadoPedido())
                .codigoVenta(pedidoVenta.getCodigoVenta())
                .fechaPedido(pedidoVenta.getFechaPedido())
                .direccionEntrega(pedidoVenta.getDireccionEntrega())
                .build();
    }
}
