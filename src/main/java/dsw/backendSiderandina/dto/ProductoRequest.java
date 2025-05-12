package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {
    private Integer idProducto;
    private Integer idUnidadesMedida; // ID del tipo de unidad de medida
    private Integer idCatProd; // ID del tipo de categoria de producto
    private Integer sku;
    private String nombre;
    private Double precioVentaBase;
    private Double costoUnitarioBase;
    private Integer stock;
    private Integer stockMin;
    private String urlImagen;
}
