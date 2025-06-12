package dsw.backendSiderandina.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalle_cotizacion")
public class DetalleCotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_cot")
    private Integer idDetalleCotizacion;
    private Double cantidad;
    @Column(name = "precio_cotizado")
    private Double precioCotizado;
    @Column(name = "monto_subtotal_linea")
    private Double montoSubtotalLinea;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cotizacion", referencedColumnName = "id_cotizacion")
    private Cotizacion cotizacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    private Producto producto;
}