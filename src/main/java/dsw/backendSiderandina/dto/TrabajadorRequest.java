package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorRequest {
    private Integer idTrabajador;
    private Integer idTipoDocumento; // ID del tipo de documento
    private Integer idTipoTrabajador; // ID del tipo de trabajador
    private String numeroDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String emailContacto;
}
