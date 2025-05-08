package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.ClienteRequest;
import dsw.backendSiderandina.dto.ClienteResponse;
import dsw.backendSiderandina.model.Cliente;
import dsw.backendSiderandina.model.TipoCliente;
import dsw.backendSiderandina.model.Usuario;
import dsw.backendSiderandina.repository.ClienteRepository;
import dsw.backendSiderandina.repository.TipoClienteRepository;
import dsw.backendSiderandina.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TipoClienteRepository tipoClienteRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ClienteResponse> listClientes() {
        return ClienteResponse.fromEntities(clienteRepository.findAll());
    }

    public ClienteResponse createCliente(ClienteRequest request) {
        TipoCliente tipoCliente = tipoClienteRepository.findById(request.getIdTipoCliente())
                .orElseThrow(() -> new EntityNotFoundException("TipoCliente no encontrado con ID: " + request.getIdTipoCliente()));

        Usuario usuario = null;
        if (request.getIdUsuario() != null) {
            usuario = usuarioRepository.findById(request.getIdUsuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + request.getIdUsuario()));
        }

        Cliente cliente = Cliente.builder()
                .tipoCliente(tipoCliente)
                .usuario(usuario)
                .ruc(request.getRuc())
                .razonSocial(request.getRazonSocial())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .build();
        cliente = clienteRepository.save(cliente);
        return ClienteResponse.fromEntity(cliente);
    }
}
