package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.CategoriaProducto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProductoResponse {
    private Integer idCatProd;
    private String nombre;

    public static CategoriaProductoResponse fromEntity(CategoriaProducto categoria) {
        if (categoria == null) return null;
        return CategoriaProductoResponse.builder()
                .idCatProd(categoria.getIdCatProd())
                .nombre(categoria.getNombre())
                .build();
    }
    public static List<CategoriaProductoResponse> fromEntities(List<CategoriaProducto> categorias) {
        return categorias.stream()
                .map(CategoriaProductoResponse::fromEntity)
                .collect(Collectors.toList());
    }
}