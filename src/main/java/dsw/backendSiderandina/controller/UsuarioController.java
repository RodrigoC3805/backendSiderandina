package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.UsuarioRequest;
import dsw.backendSiderandina.dto.UsuarioResponse;
import dsw.backendSiderandina.service.UsuarioService;
import dsw.backendSiderandina.utils.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping(path="api/v1/usuario")
public class UsuarioController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> getUsuarios() {
        List<UsuarioResponse> listaResponse;
        try {
            listaResponse = usuarioService.listUsuarios();
        } catch (Exception e) {
            logger.error("Error inesperado al listar usuarios", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("No se encontraron usuarios").build());
        }
        return ResponseEntity.ok(listaResponse);
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioRequest request) {
        UsuarioResponse response;
        try {
            response = usuarioService.createUsuario(request);
        } catch (EntityNotFoundException e) {
            logger.warn("Error al crear usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (Exception e) {
            logger.error("Error inesperado al crear usuario", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor: " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}