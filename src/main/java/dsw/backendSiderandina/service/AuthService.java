package dsw.backendSiderandina.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.AuthResponse;
import dsw.backendSiderandina.dto.LoginRequest;
import dsw.backendSiderandina.dto.RegisterRequest;
import dsw.backendSiderandina.model.Usuario;
import dsw.backendSiderandina.repository.ClienteRepository;
import dsw.backendSiderandina.repository.UsuarioRepository;
import dsw.backendSiderandina.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (clienteRepository.existsByRuc(request.getCliente().getRuc())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese RUC");
        }
        if (usuarioRepository.existsByEmail(request.getUsuario().getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        request.getUsuario().setPassword(passwordEncoder.encode(request.getUsuario().getPassword()));
        usuarioRepository.save(request.getUsuario());
        request.getCliente().setUsuario(request.getUsuario());
        clienteRepository.save(request.getCliente());

        String jwtToken = jwtService.generateToken(request.getUsuario());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
    
    public AuthResponse registerWorker(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(usuario);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthResponse login(LoginRequest request) {
        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        String jwtToken = jwtService.generateToken(usuario);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
