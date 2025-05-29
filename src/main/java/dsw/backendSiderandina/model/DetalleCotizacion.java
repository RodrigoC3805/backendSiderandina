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

    @EmbeddedId
    private DetalleCotizacionId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idCotizacion")
    @JoinColumn(name = "id_cotizacion", referencedColumnName = "id_cotizacion")
    private Cotizacion cotizacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    private Producto producto;

    @Column(name = "cantidad")
    private Integer cantidad;
}