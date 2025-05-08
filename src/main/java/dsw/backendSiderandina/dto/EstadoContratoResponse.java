package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.EstadoContrato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoContratoResponse {
    private Integer idEstadoContrato;
    private String descripcion;

    public static EstadoContratoResponse fromEntity(EstadoContrato estadoContrato) {
        return EstadoContratoResponse.builder()
                .idEstadoContrato(estadoContrato.getIdEstadoContrato())
                .descripcion(estadoContrato.getDescripcion())
                .build();
    }
}
