package dsw.backendSiderandina.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="pedido_compra")
public class PedidoCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pedido_compra")
    private Integer idPedidoCompra;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="codigo_compra")
    private Integer codigoCompra;
    @Column(name="fecha_pedido")
    private Timestamp fechaPedido;
    @Column(name="monto_total")
    private Double montoTotal;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_proveedor", referencedColumnName = "id_proveedor")
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_pedido", referencedColumnName = "id_estado_pedido")
    private EstadoPedido estadoPedido;

}
