package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.AsistenciaRequest;
import dsw.backendSiderandina.model.AsistenciaDiaria;
import dsw.backendSiderandina.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rrhh/asistencia")
public class AsistenciaController {
    @Autowired
    private AsistenciaService asistenciaService;

    @PostMapping("/ingreso")
    public AsistenciaDiaria registrarIngreso(@RequestBody AsistenciaRequest req) {
        return asistenciaService.registrarAsistenciaIngreso(req);
    }

    @PostMapping("/salida")
    public AsistenciaDiaria registrarSalida(@RequestBody AsistenciaRequest req) {
        return asistenciaService.registrarAsistenciaSalida(req);
    }

    @GetMapping
    public ResponseEntity<?> listarAsistencias() {
        return ResponseEntity.ok(asistenciaService.listarAsistencias());
    }
}