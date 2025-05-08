package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumentoRequest {
    private Integer idTipoDocumento;
    private String descripcion;
}
