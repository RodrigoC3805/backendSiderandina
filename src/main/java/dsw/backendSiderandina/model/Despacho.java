package dsw.backendSiderandina.model;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
@Table(name = "despacho")
public class Despacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDespacho;

    @Column(nullable = false)
    private Integer idPedidoVenta;

    @Column
    private Timestamp fechaProgramada;

    @Column
    private String estado;
    // Getters y setters
    public Integer getIdDespacho() { return idDespacho; }
    public void setIdDespacho(Integer idDespacho) { this.idDespacho = idDespacho; }

    public Integer getIdPedidoVenta() { return idPedidoVenta; }
    public void setIdPedidoVenta(Integer idPedidoVenta) { this.idPedidoVenta = idPedidoVenta; }

    public Timestamp getFechaProgramada() { return fechaProgramada; }
    public void setFechaProgramada(Timestamp fechaProgramada) { this.fechaProgramada = fechaProgramada; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}