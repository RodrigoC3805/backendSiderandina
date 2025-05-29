package dsw.backendSiderandina.model;

import java.sql.Timestamp;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cotizacion")
public class Cotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotizacion")
    private Integer idCotizacion;

    @Column(name = "codigo_cotizacion")
    private String codigoCotizacion;

    @Column(name = "fecha_emision")
    private Timestamp fechaEmision;

    @Column(name = "monto_subtotal")
    private Double montoSubtotal;

    @Column(name = "monto_igv")
    private Double montoIgv;

    @Column(name = "monto_total")
    private Double montoTotal;

    @Column(name = "descuento")
    private Double descuento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    @Column(name = "id_estado_cot")
    private Integer idEstadoCot;

    @OneToMany(mappedBy = "cotizacion", cascade = CascadeType.ALL)
    private List<DetalleCotizacion> detalles;
}