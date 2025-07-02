package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumentoResponse {
    private Integer idTipoDocumento;
    private String descripcion;

    public static TipoDocumentoResponse fromEntity(TipoDocumento tipoDocumento) {
        return TipoDocumentoResponse.builder()
                .idTipoDocumento(tipoDocumento.getIdTipoDocumento())
                .descripcion(tipoDocumento.getDescripcion())
                .build();
    }
    public static TipoDocumento toEntity(TipoDocumentoResponse response){
        return TipoDocumento.builder()
                .idTipoDocumento(response.getIdTipoDocumento())
                .descripcion(response.getDescripcion())
                .build();
    }

}
