package dsw.backendSiderandina.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="categoria_producto")
public class CategoriaProducto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_cat_prod")
    private Integer idCatProd;
    private String nombre;
    @Column(name="url_imagen")
    private String urlImagen;
}
