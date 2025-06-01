package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.ContratoRequest;
import dsw.backendSiderandina.dto.ContratoResponse;
import dsw.backendSiderandina.service.ContratoService;
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
@RequestMapping(path="api/rrhh/contrato")
public class ContratoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContratoService contratoService;

    @GetMapping
    public ResponseEntity<?> getContratos(@RequestParam(required = false) Integer idTrabajador) {
        List<ContratoResponse> listaResponse;
        try {
            if (idTrabajador != null) {
                listaResponse = contratoService.listContratosByTrabajador(idTrabajador);
            } else {
                listaResponse = contratoService.listContratos();
            }
        } catch (EntityNotFoundException e) {
             logger.warn("Recurso no encontrado al listar contratos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (Exception e) {
            logger.error("Error inesperado al listar contratos", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("No se encontraron contratos").build());
        }
        return ResponseEntity.ok(listaResponse);
    }

    @GetMapping("/{idContrato}")
    public ResponseEntity<?> getContratoById(@PathVariable Integer idContrato) {
        ContratoResponse response;
        try {
            response = contratoService.getContratoById(idContrato);
        } catch (EntityNotFoundException e) {
            logger.warn("Contrato no encontrado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (Exception e) {
            logger.error("Error inesperado al obtener contrato por ID", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createContrato(@RequestBody ContratoRequest request) {
        ContratoResponse response;
        try {
            response = contratoService.createContrato(request);
        } catch (EntityNotFoundException e) {
            logger.warn("Error al crear contrato: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) 
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (Exception e) {
            logger.error("Error inesperado al crear contrato", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor: " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idContrato}")
    public ResponseEntity<?> updateContrato(@PathVariable Integer idContrato, @RequestBody ContratoRequest request) {
        ContratoResponse response;
        try {
            response = contratoService.updateContrato(idContrato, request);
        } catch (EntityNotFoundException e) {
            logger.warn("Error al actualizar contrato: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND) 
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar contrato", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor: " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idContrato}")
    public ResponseEntity<?> deleteContrato(@PathVariable Integer idContrato) {
        try {
            contratoService.deleteContrato(idContrato);
        } catch (EntityNotFoundException e) {
            logger.warn("Error al eliminar contrato: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (Exception e) {
            logger.error("Error inesperado al eliminar contrato", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor: " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(ErrorResponse.builder().message("Contrato con ID " + idContrato + " eliminado exitosamente.").build());
    }
}