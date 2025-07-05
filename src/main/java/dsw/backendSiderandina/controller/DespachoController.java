package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.model.Despacho;
import dsw.backendSiderandina.service.DespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/ventas/despacho")
public class DespachoController {
    @Autowired
    private DespachoService despachoService;

    @PostMapping("/programar")
    public Despacho programar(@RequestParam Integer idPedidoVenta, @RequestParam String fechaProgramada) {
        return despachoService.programarDespacho(idPedidoVenta, Timestamp.valueOf(fechaProgramada));
    }

    @PutMapping("/{id}/estado")
    public Despacho actualizarEstado(@PathVariable Integer id, @RequestParam String estado) {
        return despachoService.actualizarEstado(id, estado);
    }

    @GetMapping
    public List<Despacho> listarTodos() {
        return despachoService.listarTodos();
    }
}