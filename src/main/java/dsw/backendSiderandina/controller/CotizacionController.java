package dsw.backendSiderandina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dsw.backendSiderandina.dto.CotizacionRequest;
import dsw.backendSiderandina.dto.CotizacionResponse;
import dsw.backendSiderandina.service.CotizacionService;

@RestController
@RequestMapping(path = "api/v1/cotizacion")
public class CotizacionController {
    @Autowired
    CotizacionService cotizacionService;

    @PostMapping
    public ResponseEntity<CotizacionResponse> crearCotizacion(@RequestBody CotizacionRequest request) {
        CotizacionResponse response = cotizacionService.crearCotizacion(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> listarCotizaciones() {
        return ResponseEntity.ok(cotizacionService.listarCotizaciones());
    }
}