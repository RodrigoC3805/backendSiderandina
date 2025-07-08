package dsw.backendSiderandina.dto;

public class ProductoMasVendidoDTO {
    private Integer idProducto;
    private String producto;
    private Integer sku;
    private Double totalUnidadesVendidas;

    public ProductoMasVendidoDTO(Integer idProducto, String producto, Integer sku, Double totalUnidadesVendidas) {
        this.idProducto = idProducto;
        this.producto = producto;
        this.sku = sku;
        this.totalUnidadesVendidas = totalUnidadesVendidas;
    }

    // Getters y setters
    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }
    public Integer getSku() { return sku; }
    public void setSku(Integer sku) { this.sku = sku; }
    public Double getTotalUnidadesVendidas() { return totalUnidadesVendidas; }
    public void setTotalUnidadesVendidas(Double totalUnidadesVendidas) { this.totalUnidadesVendidas = totalUnidadesVendidas; }
}
