package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.Usuario;
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
public class UsuarioResponse {
    private Integer idUsuario;
    private TipoUsuarioResponse tipoUsuario;
    private String email;
    // No incluir password en la respuesta

    public static UsuarioResponse fromEntity(Usuario usuario) {
        if (usuario == null) return null;
        return UsuarioResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .tipoUsuario(TipoUsuarioResponse.fromEntity(usuario.getTipoUsuario()))
                .email(usuario.getEmail())
                .build();
    }

    public static List<UsuarioResponse> fromEntities(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(UsuarioResponse::fromEntity)
                .collect(Collectors.toList());
    }
}