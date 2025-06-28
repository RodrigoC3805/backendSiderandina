package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import dsw.backendSiderandina.model.UnidadesMedida;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnidadesMedidaResponse {
    private Integer idUnidadesMedida;
    private String descripcion;

    public static UnidadesMedidaResponse fromEntity(UnidadesMedida unidadesMedida) {
        return UnidadesMedidaResponse.builder()
                .idUnidadesMedida(unidadesMedida.getIdUnidadesMedida())
                .descripcion(unidadesMedida.getDescripcion())
                .build();
    }
    
    public static UnidadesMedida toEntity(UnidadesMedidaResponse unidadesMedidaResponse) {
        return UnidadesMedida.builder()
                .idUnidadesMedida(unidadesMedidaResponse.getIdUnidadesMedida())
                .descripcion(unidadesMedidaResponse.getDescripcion())
                .build();
    }
}
