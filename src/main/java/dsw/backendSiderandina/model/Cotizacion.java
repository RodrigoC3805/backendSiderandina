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
@Table(name="cotizacion")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cotizacion")
    private Integer idCotizacion;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_estado_cot", referencedColumnName = "id_estado_cot")
    private EstadoCotizacion estadoCotizacion;

    @Column(name="codigo_cotizacion")
    private String codigoCotizacion;

    @Column(name="fecha_emision")
    private Timestamp fechaEmision;

    @Column(name="monto_subtotal")
    private Double montoSubtotal;

    @Column(name="monto_igv")
    private Double montoIgv;

    @Column(name="monto_total")
    private Double montoTotal;

    @Column(name="descuento")
    private Double descuento;
}