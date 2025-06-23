package dsw.backendSiderandina.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.AuthResponse;
import dsw.backendSiderandina.dto.LoginRequest;
import dsw.backendSiderandina.dto.RegisterProveedorRequest;
import dsw.backendSiderandina.dto.RegisterRequest;
import dsw.backendSiderandina.dto.RegisterWorkerRequest;
import dsw.backendSiderandina.repository.ClienteRepository;
import dsw.backendSiderandina.repository.ProveedorRepository;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import dsw.backendSiderandina.repository.UsuarioRepository;
import dsw.backendSiderandina.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ProveedorRepository proveedorRepository;
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
        request.getUsuario().getTipoUsuario().setIdTipoUsuario(7);
        usuarioRepository.save(request.getUsuario());
        request.getCliente().setUsuario(request.getUsuario());
        request.getCliente().getTipoCliente().setIdTipoCliente(1);;
        clienteRepository.save(request.getCliente());

        String jwtToken = jwtService.generateToken(request.getUsuario());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Transactional
    public AuthResponse registerWorker(RegisterWorkerRequest request) {
        if (trabajadorRepository.existsByNumeroDocumento(request.getTrabajador().getNumeroDocumento()))
            throw new IllegalArgumentException("Ya existe un trabajador con ese número de documento");
        if (usuarioRepository.existsByEmail(request.getUsuario().getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        if (request.getUsuario().getPassword() == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }
        request.getUsuario().setPassword(passwordEncoder.encode(request.getUsuario().getPassword()));
        usuarioRepository.save(request.getUsuario());
        request.getTrabajador().setUsuario(request.getUsuario());
        trabajadorRepository.save(request.getTrabajador());
        String jwtToken = jwtService.generateToken(request.getUsuario());
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
    
    @Transactional
    public AuthResponse registerProveedor(RegisterProveedorRequest request) {
        if (proveedorRepository.existsByRuc(request.getProveedor().getRuc()))
            throw new IllegalArgumentException("Ya existe un proveedor con ese ruc");
        if (usuarioRepository.existsByEmail(request.getUsuario().getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        if (request.getUsuario().getPassword() == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }
        request.getUsuario().setPassword(passwordEncoder.encode(request.getUsuario().getPassword()));
        usuarioRepository.save(request.getUsuario());
        request.getProveedor().setUsuario(request.getUsuario());
        proveedorRepository.save(request.getProveedor());
        String jwtToken = jwtService.generateToken(request.getUsuario());
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
