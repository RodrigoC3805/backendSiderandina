package dsw.backendSiderandina.dto;

public class ReportePuntualidadDTO {
    private long puntuales;
    private long tardanzas;
    private long faltas;

    public ReportePuntualidadDTO(long puntuales, long tardanzas, long faltas) {
        this.puntuales = puntuales;
        this.tardanzas = tardanzas;
        this.faltas = faltas;
    }

    public long getPuntuales() { return puntuales; }
    public void setPuntuales(long puntuales) { this.puntuales = puntuales; }
    public long getTardanzas() { return tardanzas; }
    public void setTardanzas(long tardanzas) { this.tardanzas = tardanzas; }
    public long getFaltas() { return faltas; }
    public void setFaltas(long faltas) { this.faltas = faltas; }
}