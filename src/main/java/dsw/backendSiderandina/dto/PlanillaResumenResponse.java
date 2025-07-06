package dsw.backendSiderandina.dto;

public class PlanillaResumenResponse {
    private Integer idPlanilla;
    private Integer mes;
    private Integer anio;
    private String fechaGeneracion;
    private Double totalSueldos;
    private Long cantidadTrabajadores;

    public PlanillaResumenResponse(Integer idPlanilla, Integer mes, Integer anio, String fechaGeneracion, Double totalSueldos, Long cantidadTrabajadores) {
        this.idPlanilla = idPlanilla;
        this.mes = mes;
        this.anio = anio;
        this.fechaGeneracion = fechaGeneracion;
        this.totalSueldos = totalSueldos;
        this.cantidadTrabajadores = cantidadTrabajadores;
    }

    // Getters y setters
    public Integer getIdPlanilla() { return idPlanilla; }
    public void setIdPlanilla(Integer idPlanilla) { this.idPlanilla = idPlanilla; }
    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public String getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(String fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }
    public Double getTotalSueldos() { return totalSueldos; }
    public void setTotalSueldos(Double totalSueldos) { this.totalSueldos = totalSueldos; }
    public Long getCantidadTrabajadores() { return cantidadTrabajadores; }
    public void setCantidadTrabajadores(Long cantidadTrabajadores) { this.cantidadTrabajadores = cantidadTrabajadores; }
}