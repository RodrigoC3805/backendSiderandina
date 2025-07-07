package dsw.backendSiderandina.dto;

public class ReporteHorasExtrasDTO {
    private String nombreCompleto;
    private double horasExtras;

    public ReporteHorasExtrasDTO(String nombreCompleto, double horasExtras) {
        this.nombreCompleto = nombreCompleto;
        this.horasExtras = horasExtras;
    }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public double getHorasExtras() { return horasExtras; }
    public void setHorasExtras(double horasExtras) { this.horasExtras = horasExtras; }
}