package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoClienteRequest {
    private Integer idTipoCliente;
    private String descripcion;
}