package dsw.backendSiderandina.dto;

public class ReportePedidosProveedorDTO {
    private String razonSocial;
    private Long cantidadPedidos;

    public ReportePedidosProveedorDTO(String razonSocial, Long cantidadPedidos) {
        this.razonSocial = razonSocial;
        this.cantidadPedidos = cantidadPedidos;
    }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public Long getCantidadPedidos() { return cantidadPedidos; }
    public void setCantidadPedidos(Long cantidadPedidos) { this.cantidadPedidos = cantidadPedidos; }
}