package dsw.backendSiderandina.model;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_planilla")
public class DetallePlanilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_planilla")
    private Integer idDetallePlanilla;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_planilla")
    private Planilla planilla;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_trabajador")
    private Trabajador trabajador;

    @Column(name = "sueldo_base")
    private Double sueldoBase;

    @Column(name = "bonos")
    private Double bonos;

    @Column(name = "descuentos")
    private Double descuentos;

    @Column(name = "sueldo_neto")
    private Double sueldoNeto;

    // Getters y setters
    public Integer getIdDetallePlanilla() {
        return idDetallePlanilla;
    }
    public void setIdDetallePlanilla(Integer idDetallePlanilla) {
        this.idDetallePlanilla = idDetallePlanilla;
    }

    public Planilla getPlanilla() {
        return planilla;
    }
    public void setPlanilla(Planilla planilla) {
        this.planilla = planilla;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public Double getSueldoBase() {
        return sueldoBase;
    }
    public void setSueldoBase(Double sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public Double getBonos() {
        return bonos;
    }
    public void setBonos(Double bonos) {
        this.bonos = bonos;
    }

    public Double getDescuentos() {
        return descuentos;
    }
    public void setDescuentos(Double descuentos) {
        this.descuentos = descuentos;
    }

    public Double getSueldoNeto() {
        return sueldoNeto;
    }
    public void setSueldoNeto(Double sueldoNeto) {
        this.sueldoNeto = sueldoNeto;
    }
}