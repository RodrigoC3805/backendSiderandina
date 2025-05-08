package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
    private Integer idCliente;
    private Integer idTipoCliente;
    private Integer idUsuario; // Opcional, si el cliente tiene cuenta de usuario
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String telefono;
}