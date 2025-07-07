package dsw.backendSiderandina.dto;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<CategoriaProductoResponse> fromEntities(List<CategoriaProducto> categoriaProducto) {
        return categoriaProducto.stream()
                .map(CategoriaProductoResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    public static CategoriaProducto toEntity(CategoriaProductoResponse categoriaProductoResponse) {
        return CategoriaProducto.builder()
                .idCatProd(categoriaProductoResponse.getIdCatProd())
                .nombre(categoriaProductoResponse.getNombre())
                .urlImagen(categoriaProductoResponse.getUrlImagen())
                .build();
    }
}
