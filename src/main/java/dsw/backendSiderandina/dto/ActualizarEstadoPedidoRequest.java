package dsw.backendSiderandina.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoPedidoRequest {
    private Integer idPedidoCompra;
    private Integer idEstadoPedido;
}