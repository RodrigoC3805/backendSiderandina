package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.AsistenciaDiaria;
import dsw.backendSiderandina.model.EstadoJustificacion;
import dsw.backendSiderandina.model.Justificacion;
import dsw.backendSiderandina.model.MotivoJustificacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JustificacionResponse {
    private Integer idJustificacion;
    private AsistenciaDiaria asistenciaDiaria;
    private MotivoJustificacion motivoJustificacion;
    private LocalDate fechaSolicitud;
    private EstadoJustificacion estadoJustificacion;

    public static JustificacionResponse fromEntity(Justificacion justificacion){
        return JustificacionResponse.builder()
                .idJustificacion(justificacion.getIdJustificacion())
                .fechaSolicitud(justificacion.getFechaSolicitud())
                .asistenciaDiaria(justificacion.getAsistenciaDiaria())
                .motivoJustificacion(justificacion.getMotivoJustificacion())
                .estadoJustificacion(justificacion.getEstadoJustificacion())
                .build();
    }
}
