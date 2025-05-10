package dsw.backendSiderandina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dsw.backendSiderandina.dto.EmpleadoListItem;
import dsw.backendSiderandina.dto.TrabajadorRequest;
import dsw.backendSiderandina.dto.TrabajadorResponse;
import dsw.backendSiderandina.service.TrabajadorService;
import dsw.backendSiderandina.utils.ErrorResponse;

@RestController
@RequestMapping(path="api/v1/trabajador")
public class TrabajadorController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping ("/empleados")
    public ResponseEntity<?> getTrabajadores() {
        List<TrabajadorResponse> listaTrabajadoresResponse;
        try {
            listaTrabajadoresResponse = trabajadorService.listTrabajadores();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaTrabajadoresResponse.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("Trabajadores not found").build());
        return ResponseEntity.ok(listaTrabajadoresResponse);
    }

    @PostMapping
    public ResponseEntity<?> createTrabajador(@RequestBody TrabajadorRequest trabajadorRequest) {
        TrabajadorResponse trabajadorResponse;
        try {
            trabajadorResponse = trabajadorService.createTrabajador(trabajadorRequest);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(trabajadorResponse);
    }
}
