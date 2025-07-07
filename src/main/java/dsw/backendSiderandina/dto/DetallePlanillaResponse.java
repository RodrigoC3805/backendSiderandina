package dsw.backendSiderandina.dto;

public class DetallePlanillaResponse {
    private Integer idDetallePlanilla;
    private Integer idTrabajador;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Double sueldoBase;
    private Double bonos;
    private Double descuentos;
    private Double sueldoNeto;

    public Integer getIdDetallePlanilla() {
        return idDetallePlanilla;
    }
    public void setIdDetallePlanilla(Integer idDetallePlanilla) {
        this.idDetallePlanilla = idDetallePlanilla;
    }

    public Integer getIdTrabajador() {
        return idTrabajador;
    }
    public void setIdTrabajador(Integer idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
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