package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.EstadoCotizacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCotizacionResponse {
    private Integer idEstadoCot;
    private String descripcion;

    public static EstadoCotizacionResponse fromEntity(EstadoCotizacion estadoCotizacion) {
        if (estadoCotizacion == null) return null;
        return EstadoCotizacionResponse.builder()
                .idEstadoCot(estadoCotizacion.getIdEstadoCot())
                .descripcion(estadoCotizacion.getDescripcion())
                .build();
    }
    public static List<EstadoCotizacionResponse> fromEntities(List<EstadoCotizacion> estados) {
        return estados.stream()
                .map(EstadoCotizacionResponse::fromEntity)
                .collect(Collectors.toList());
    }
}