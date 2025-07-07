package dsw.backendSiderandina.dto;

import java.sql.Timestamp;
import dsw.backendSiderandina.model.Despacho;
import dsw.backendSiderandina.model.PedidoVenta;

public class DespachoResponse {
    private Integer idDespacho;
    private Integer idPedidoVenta;
    private String codigoVenta;
    private Timestamp fechaProgramada;
    private String estado;

    public DespachoResponse() {}

    public DespachoResponse(Despacho despacho, PedidoVenta pedidoVenta) {
        this.idDespacho = despacho.getIdDespacho();
        this.idPedidoVenta = despacho.getIdPedidoVenta();
        this.codigoVenta = pedidoVenta != null ? pedidoVenta.getCodigoVenta() : "";
        this.fechaProgramada = despacho.getFechaProgramada();
        this.estado = despacho.getEstado();
    }

    // getters y setters
    public Integer getIdDespacho() { return idDespacho; }
    public void setIdDespacho(Integer idDespacho) { this.idDespacho = idDespacho; }
    public Integer getIdPedidoVenta() { return idPedidoVenta; }
    public void setIdPedidoVenta(Integer idPedidoVenta) { this.idPedidoVenta = idPedidoVenta; }
    public String getCodigoVenta() { return codigoVenta; }
    public void setCodigoVenta(String codigoVenta) { this.codigoVenta = codigoVenta; }
    public Timestamp getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(Timestamp fechaProgramada) { this.fechaProgramada = fechaProgramada; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}