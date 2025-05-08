package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.Cliente;
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
public class ClienteResponse {
    private Integer idCliente;
    private TipoClienteResponse tipoCliente;
    private UsuarioResponse usuario;
    private String ruc;
    private String razonSocial;
    private String direccion;
    private String telefono;

    public static ClienteResponse fromEntity(Cliente cliente) {
        if (cliente == null) return null;
        return ClienteResponse.builder()
                .idCliente(cliente.getIdCliente())
                .tipoCliente(TipoClienteResponse.fromEntity(cliente.getTipoCliente()))
                .usuario(UsuarioResponse.fromEntity(cliente.getUsuario()))
                .ruc(cliente.getRuc())
                .razonSocial(cliente.getRazonSocial())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .build();
    }
    public static List<ClienteResponse> fromEntities(List<Cliente> clientes) {
        return clientes.stream()
                .map(ClienteResponse::fromEntity)
                .collect(Collectors.toList());
    }
}