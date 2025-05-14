package dsw.backendSiderandina.dto;

import java.util.List;

import dsw.backendSiderandina.model.ComprobanteCompra;
import dsw.backendSiderandina.model.DetalleCompra;
import dsw.backendSiderandina.model.Pago;
import dsw.backendSiderandina.model.PedidoCompra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoYDetallesDTO {
    private PedidoCompra pedidoCompra; // Información del pedido principal
    private List<DetalleCompra> detallesCompra; // Lista de detalles asociados al pedido
    private ComprobanteCompra comprobanteCompra; // Información del comprobante de compra
    private Pago pago; // Información del pago asociado al pedido
}