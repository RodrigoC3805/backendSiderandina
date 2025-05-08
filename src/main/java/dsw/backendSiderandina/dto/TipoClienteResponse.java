package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.TipoCliente;
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
public class TipoClienteResponse {
    private Integer idTipoCliente;
    private String descripcion;

    public static TipoClienteResponse fromEntity(TipoCliente tipoCliente) {
        if (tipoCliente == null) return null;
        return TipoClienteResponse.builder()
                .idTipoCliente(tipoCliente.getIdTipoCliente())
                .descripcion(tipoCliente.getDescripcion())
                .build();
    }
    public static List<TipoClienteResponse> fromEntities(List<TipoCliente> tipoClientes) {
        return tipoClientes.stream()
                .map(TipoClienteResponse::fromEntity)
                .collect(Collectors.toList());
    }
}