package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.UsuarioRequest;
import dsw.backendSiderandina.dto.UsuarioResponse;
import dsw.backendSiderandina.model.TipoUsuario;
import dsw.backendSiderandina.model.Usuario;
import dsw.backendSiderandina.repository.TipoUsuarioRepository;
import dsw.backendSiderandina.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public List<UsuarioResponse> listUsuarios() {
        return UsuarioResponse.fromEntities(usuarioRepository.findAll());
    }

    public UsuarioResponse createUsuario(UsuarioRequest request) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(request.getIdTipoUsuario())
                .orElseThrow(() -> new EntityNotFoundException("TipoUsuario no encontrado con ID: " + request.getIdTipoUsuario()));

        Usuario usuario = Usuario.builder()
                .tipoUsuario(tipoUsuario)
                .email(request.getEmail())
                .password(request.getPassword()) // Considerar encriptación
                .build();
        usuario = usuarioRepository.save(usuario);
        return UsuarioResponse.fromEntity(usuario);
    }
}