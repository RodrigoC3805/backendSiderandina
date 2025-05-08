package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.TipoTrabajador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoTrabajadorResponse {
    private Integer idTipoTrabajador;
    private String descripcion;

    public static TipoTrabajadorResponse fromEntity(TipoTrabajador tipoTrabajador) {
        return TipoTrabajadorResponse.builder()
                .idTipoTrabajador(tipoTrabajador.getIdTipoTrabajador())
                .descripcion(tipoTrabajador.getDescripcion())
                .build();
    }
}
