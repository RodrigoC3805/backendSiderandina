package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.TrabajadorListItem;
import dsw.backendSiderandina.dto.TrabajadorRequest;
import dsw.backendSiderandina.dto.TrabajadorResponse;
import dsw.backendSiderandina.service.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trabajador")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping("/trabajadores")
    public ResponseEntity<List<TrabajadorListItem>> getTrabajadores() {
        List<TrabajadorListItem> lista = trabajadorService.listarTrabajadores();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{idTrabajador}")
    public ResponseEntity<TrabajadorResponse> getTrabajador(@PathVariable Integer idTrabajador) {
        TrabajadorResponse trabajador = trabajadorService.getTrabajador(idTrabajador);
        return ResponseEntity.ok(trabajador);
    }
}