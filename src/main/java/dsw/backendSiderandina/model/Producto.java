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
@Table(name="producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Integer idProducto;
    private Integer sku;
    private String nombre;
    @Column(name="precio_venta_base")
    private Double precioVentaBase;
    @Column(name="costo_unitario_base")
    private Double costoUnitarioBase;
    private Integer stock;
    @Column(name="stock_min")
    private Integer stockMin;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name= "id_cat_prod",referencedColumnName = "id_cat_prod")
    private CategoriaProducto idCatProd;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_unidades_medida", referencedColumnName = "id_unidades_medida")
    private UnidadesMedida idUnidadesMedida;
}
