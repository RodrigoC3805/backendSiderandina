package dsw.backendSiderandina.dto;

public class ReporteAsistenciaDTO {
    private String nombreCompleto;
    private String fecha;
    private double horasTrabajadas;

    public ReporteAsistenciaDTO(String nombreCompleto, String fecha, double horasTrabajadas) {
        this.nombreCompleto = nombreCompleto;
        this.fecha = fecha;
        this.horasTrabajadas = horasTrabajadas;
    }

    // Getters y setters
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public double getHorasTrabajadas() { return horasTrabajadas; }
    public void setHorasTrabajadas(double horasTrabajadas) { this.horasTrabajadas = horasTrabajadas; }
}