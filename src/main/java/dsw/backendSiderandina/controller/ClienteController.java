package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.ClienteRequest;
import dsw.backendSiderandina.dto.ClienteResponse;
import dsw.backendSiderandina.service.ClienteService;
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
@RequestMapping(path="api/v1/cliente")
public class ClienteController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> getClientes() {
        List<ClienteResponse> listaResponse;
        try {
            listaResponse = clienteService.listClientes();
        } catch (Exception e) {
            logger.error("Error inesperado al listar clientes", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("No se encontraron clientes").build());
        }
        return ResponseEntity.ok(listaResponse);
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody ClienteRequest request) {
        ClienteResponse response;
        try {
            response = clienteService.createCliente(request);
        } catch (EntityNotFoundException e) {
            logger.warn("Error al crear cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (Exception e) {
            logger.error("Error inesperado al crear cliente", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor: " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}