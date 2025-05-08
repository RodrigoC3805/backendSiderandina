package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
    private Integer idUsuario; // Opcional
    private Integer idTipoUsuario;
    private String email;
    private String password; // En una app real, manejar la seguridad del password
}