package dsw.backendSiderandina.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DetalleCotizacionId implements Serializable {
    @Column(name = "id_cotizacion")
    private Integer idCotizacion;

    @Column(name = "id_producto")
    private Integer idProducto;
}