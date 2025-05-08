package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.UnidadesMedida;
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
public class UnidadesMedidaResponse {
    private Integer idUnidadesMedida;
    private String descripcion;

    public static UnidadesMedidaResponse fromEntity(UnidadesMedida unidad) {
        if (unidad == null) return null;
        return UnidadesMedidaResponse.builder()
                .idUnidadesMedida(unidad.getIdUnidadesMedida())
                .descripcion(unidad.getDescripcion())
                .build();
    }
    public static List<UnidadesMedidaResponse> fromEntities(List<UnidadesMedida> unidades) {
        return unidades.stream()
                .map(UnidadesMedidaResponse::fromEntity)
                .collect(Collectors.toList());
    }
}