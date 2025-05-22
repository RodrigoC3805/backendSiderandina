package dsw.backendSiderandina.dto;

import java.sql.Timestamp;

import dsw.backendSiderandina.model.ComprobanteCompra;
import dsw.backendSiderandina.model.Pago;
import dsw.backendSiderandina.model.PedidoCompra;
import dsw.backendSiderandina.model.TipoComprobante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComprobanteCompraResponse {
    private Integer idComprobanteCompra;
    private Integer numeroComprobante;
    private Timestamp fechaEmision;
    private PedidoCompra pedidoCompra;
    private Pago pago;
    private TipoComprobante tipoComprobante;

    public static ComprobanteCompraResponse fromEntity(ComprobanteCompra comprobanteCompra) {
        return ComprobanteCompraResponse.builder().idComprobanteCompra(comprobanteCompra.getIdComprobanteCompra()).
        fechaEmision(comprobanteCompra.getFechaEmision())
        .numeroComprobante(comprobanteCompra.getNumeroComprobante()).
        pedidoCompra(comprobanteCompra.getPedidoCompra()).
        pago(comprobanteCompra.getPago()).
                tipoComprobante(comprobanteCompra.getTipoComprobante()).build();
    }
}
