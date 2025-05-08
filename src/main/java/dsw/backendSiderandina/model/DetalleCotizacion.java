package dsw.backendSiderandina.model;

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
@Table(name="detalle_cotizacion")
public class DetalleCotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detalle_cot")
    private Integer idDetalleCot;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_cotizacion", referencedColumnName = "id_cotizacion")
    private Cotizacion cotizacion;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_producto", referencedColumnName = "id_producto")
    private Producto producto;

    @Column(name="cantidad")
    private Double cantidad; // numeric(10,2)

    @Column(name="monto_subtotal_linea")
    private Double montoSubtotalLinea; // numeric(12,2)
}