package dsw.backendSiderandina.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "planilla")
public class Planilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_planilla")
    private Integer idPlanilla;

    @Column(name = "mes")
    private Integer mes; // 1-12

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "fecha_generacion")
    private Timestamp fechaGeneracion;

    @Column(name = "total_sueldos")
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