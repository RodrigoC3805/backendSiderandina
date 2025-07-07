package dsw.backendSiderandina.dto;

public class ReporteProveedorComprasDTO {
    private String ruc;
    private String razonSocial;
    private Long totalPedidos;
    private Double montoTotalCompras;
    private Double promedioPorPedido;

    public ReporteProveedorComprasDTO(String ruc, String razonSocial, Long totalPedidos, Double montoTotalCompras, Double promedioPorPedido) {
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.totalPedidos = totalPedidos;
        this.montoTotalCompras = montoTotalCompras;
        this.promedioPorPedido = promedioPorPedido;
    }

    // Getters y setters
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public Long getTotalPedidos() { return totalPedidos; }
    public void setTotalPedidos(Long totalPedidos) { this.totalPedidos = totalPedidos; }
    public Double getMontoTotalCompras() { return montoTotalCompras; }
    public void setMontoTotalCompras(Double montoTotalCompras) { this.montoTotalCompras = montoTotalCompras; }
    public Double getPromedioPorPedido() { return promedioPorPedido; }
    public void setPromedioPorPedido(Double promedioPorPedido) { this.promedioPorPedido = promedioPorPedido; }
}