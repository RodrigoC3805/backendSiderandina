package dsw.backendSiderandina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import dsw.backendSiderandina.model.MetodoPago;

import dsw.backendSiderandina.repository.MetodoPagoRepository;
import dsw.backendSiderandina.utils.ErrorResponse;

@RestController
@RequestMapping(path="api/v1/metodopago")
public class MetodoPagoController {
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MetodoPagoRepository metodoPagoRepository;

    @GetMapping
    public ResponseEntity<?> getMetodoPago() {
        List<MetodoPago> listaMetodoPago = null;
        try {
            listaMetodoPago = metodoPagoRepository.findAll();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaMetodoPago.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("MetodoPago not found").build());
        return ResponseEntity.ok(listaMetodoPago);
    } 
}
