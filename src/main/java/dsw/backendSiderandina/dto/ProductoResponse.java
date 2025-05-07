package dsw.backendSiderandina.dto;

import java.util.List;
import java.util.stream.Collectors;

import dsw.backendSiderandina.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {
    private Integer idProducto;
    private Integer sku;
    private String nombre;
    private Double precioVentaBase;
    private Double costoUnitarioBase;
    private Integer stock;
    private Integer stockMin;

    public static ProductoResponse fromEntity(Producto producto) {
        return ProductoResponse.builder()
                .idProducto(producto.getIdProducto())
                .nombre(producto.getNombre())
                .sku(producto.getSku())
                .precioVentaBase(producto.getPrecioVentaBase())
                .costoUnitarioBase(producto.getCostoUnitarioBase())
                .stock(producto.getStock())
                .stockMin(producto.getStockMin())
                .build();
    }

     public static List<ProductoResponse> fromEntities(List<Producto> producto) {
        return producto.stream()
            .map(ProductoResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
