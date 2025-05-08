package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.TipoUsuario;
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
public class TipoUsuarioResponse {
    private Integer idTipoUsuario;
    private String descripcion;

    public static TipoUsuarioResponse fromEntity(TipoUsuario tipoUsuario) {
        if (tipoUsuario == null) return null;
        return TipoUsuarioResponse.builder()
                .idTipoUsuario(tipoUsuario.getIdTipoUsuario())
                .descripcion(tipoUsuario.getDescripcion())
                .build();
    }

    public static List<TipoUsuarioResponse> fromEntities(List<TipoUsuario> tipoUsuarios) {
        return tipoUsuarios.stream()
                .map(TipoUsuarioResponse::fromEntity)
                .collect(Collectors.toList());
    }
}