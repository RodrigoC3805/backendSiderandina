package dsw.backendSiderandina.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dsw.backendSiderandina.model.Cliente;
import dsw.backendSiderandina.service.ClienteService;
import dsw.backendSiderandina.utils.ErrorResponse;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/findclientebyuseremail")
    public ResponseEntity<?> findByUserEmail(@RequestParam String email) {
        Optional<Cliente> cliente = null;
        try {
            cliente = clienteService.findByUsuarioEmail(email);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cliente.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("Cliente not found").build());
        return ResponseEntity.ok(cliente.get());
    }
    
}
