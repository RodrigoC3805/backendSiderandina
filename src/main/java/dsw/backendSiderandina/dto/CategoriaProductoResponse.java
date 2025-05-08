package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.CategoriaProducto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProductoResponse {
    private Integer idCatProd;
    private String nombre;
    private String urlImagen;

    public static CategoriaProductoResponse fromEntity(CategoriaProducto categoriaProducto) {
        return CategoriaProductoResponse.builder()
        .idCatProd(categoriaProducto.getIdCatProd())
        .nombre(categoriaProducto.getNombre())
        .urlImagen(categoriaProducto.getUrlImagen())
        .build();
    }
}
