package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.CotizacionRequest;
import dsw.backendSiderandina.dto.CotizacionResponse;
import dsw.backendSiderandina.service.CotizacionService;
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
@RequestMapping(path="api/v1/cotizacion")
public class CotizacionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CotizacionService cotizacionService;

    @GetMapping
    public ResponseEntity<?> getCotizaciones() {
        List<CotizacionResponse> listaResponse;
        try {
            listaResponse = cotizacionService.listCotizaciones();
        } catch (Exception e) {
            logger.error("Error inesperado al listar cotizaciones", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("No se encontraron cotizaciones").build());
        }
        return ResponseEntity.ok(listaResponse);
    }

    @PostMapping
    public ResponseEntity<?> createCotizacion(@RequestBody CotizacionRequest request) {
        CotizacionResponse response;
        try {
            response = cotizacionService.createCotizacion(request);
        } catch (EntityNotFoundException e) {
            logger.warn("Error al crear cotización: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder().message(e.getMessage()).build());
        } catch (Exception e) {
            logger.error("Error inesperado al crear cotización", e);
            return new ResponseEntity<>(ErrorResponse.builder().message("Error interno del servidor al procesar la cotización: " + e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
