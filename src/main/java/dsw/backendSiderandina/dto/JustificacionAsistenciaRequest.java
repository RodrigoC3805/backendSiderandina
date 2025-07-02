package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JustificacionAsistenciaRequest {
    // Para un nuevo registro en asistencia_diaria
    private Integer idTrabajador;
    private LocalDate dia_asistencia;
    private LocalTime hora_entrada;
    private LocalTime hora_salida;

    // Para un nuevo registro de justificacion

    private LocalDate fechaSolicitud;
    private Integer idMotivoJustificacion;
}
