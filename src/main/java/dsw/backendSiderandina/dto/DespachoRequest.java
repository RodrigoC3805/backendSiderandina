package dsw.backendSiderandina.dto;

import lombok.Data;

@Data
public class DespachoRequest {
    private Integer idPedidoVenta;
    private String fechaProgramada;
    private String estado;
}