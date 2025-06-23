package dsw.backendSiderandina.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dsw.backendSiderandina.dto.AuthResponse;
import dsw.backendSiderandina.dto.LoginRequest;
import dsw.backendSiderandina.dto.RegisterProveedorRequest;
import dsw.backendSiderandina.dto.RegisterRequest;
import dsw.backendSiderandina.dto.RegisterWorkerRequest;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.model.Usuario;
import dsw.backendSiderandina.service.AuthService;
import dsw.backendSiderandina.service.TrabajadorService;
import dsw.backendSiderandina.utils.ErrorResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final TrabajadorService trabajadorService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        logger.info(">register: " + request);
        return ResponseEntity.ok(authService.register(request));
    }
    
    @PostMapping("/registerworker")
    public ResponseEntity<AuthResponse> registerWorker(@RequestBody RegisterWorkerRequest request) {
        logger.info(">register: " + request);
        return ResponseEntity.ok(authService.registerWorker(request));
    }
    
    @PostMapping("/registerproveedor")
    public ResponseEntity<AuthResponse> registerproveedor(@RequestBody RegisterProveedorRequest request) {
        logger.info(">register: " + request);
        return ResponseEntity.ok(authService.registerProveedor(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/findtrabajadorbyuseremail")
    public ResponseEntity<?> findByUserEmail(@RequestParam String email) {
        Optional<Trabajador> trabajador = null;
        try {
            trabajador = trabajadorService.findByUsuarioEmail(email);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (trabajador.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("Trabajador not found").build());
        return ResponseEntity.ok(trabajador.get());
    }
}
