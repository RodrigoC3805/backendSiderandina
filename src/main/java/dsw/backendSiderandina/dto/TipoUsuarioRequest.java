package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuarioRequest {
    private Integer idTipoUsuario; // Opcional para creación, necesario para actualización
    private String descripcion;
}