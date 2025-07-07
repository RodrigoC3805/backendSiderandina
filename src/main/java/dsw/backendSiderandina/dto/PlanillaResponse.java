package dsw.backendSiderandina.dto;

import java.sql.Timestamp;

public class PlanillaResponse {
    private Integer idPlanilla;
    private Integer mes;
    private Integer anio;
    private Timestamp fechaGeneracion;
    private Double totalSueldos;

    // Getters y setters
    public Integer getIdPlanilla() {
        return idPlanilla;
    }
    public void setIdPlanilla(Integer idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public Integer getMes() {
        return mes;
    }
    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Timestamp getFechaGeneracion() {
        return fechaGeneracion;
    }
    public void setFechaGeneracion(Timestamp fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public Double getTotalSueldos() {
        return totalSueldos;
    }
    public void setTotalSueldos(Double totalSueldos) {
        this.totalSueldos = totalSueldos;
    }
}