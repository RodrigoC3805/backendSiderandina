package dsw.backendSiderandina.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AsistenciaRequest {
    private String numeroDocumento;
    private LocalDate fecha;
    private LocalTime horaIngreso;
    private LocalTime horaSalida;
}