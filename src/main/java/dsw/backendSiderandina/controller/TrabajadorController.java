package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.TrabajadorListItem;
import dsw.backendSiderandina.dto.TrabajadorRequest;
import dsw.backendSiderandina.dto.TrabajadorResponse;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import dsw.backendSiderandina.service.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rrhh/trabajador")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @Autowired
    private TrabajadorRepository trabajadorRepository; 

    @PostMapping
    public ResponseEntity<TrabajadorResponse> crearTrabajador(@RequestBody TrabajadorRequest request) {
        TrabajadorResponse trabajador = trabajadorService.createTrabajador(request);
        return ResponseEntity.ok(trabajador);
    }

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
    
    @PutMapping("/{idTrabajador}")
    public ResponseEntity<TrabajadorResponse> actualizarTrabajador(
            @PathVariable Integer idTrabajador,
            @RequestBody TrabajadorRequest request) {
        TrabajadorResponse trabajadorActualizado = trabajadorService.updateTrabajador(idTrabajador, request);
        return ResponseEntity.ok(trabajadorActualizado);
    }
    
    @DeleteMapping("/{idTrabajador}")
    public ResponseEntity<Void> eliminarTrabajador(@PathVariable Integer idTrabajador) {
        trabajadorService.eliminarTrabajador(idTrabajador);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNumeroDocumento(@RequestParam String numeroDocumento) {
        Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByNumeroDocumento(numeroDocumento);
        if (trabajadorOpt.isPresent()) {
            return ResponseEntity.ok(TrabajadorResponse.fromEntity(trabajadorOpt.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }
    }
}