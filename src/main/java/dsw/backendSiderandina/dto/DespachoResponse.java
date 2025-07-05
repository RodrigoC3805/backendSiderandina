package dsw.backendSiderandina.dto;

import lombok.Data;

@Data
public class DespachoResponse {
    private Integer idDespacho;
    private Integer idPedidoVenta;
    private String codigoVenta;
    private String fechaProgramada;
    private String estado;
    private Double montoTotalCotizacion;
}