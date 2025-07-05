package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.DespachoResponse;
import dsw.backendSiderandina.model.Despacho;
import dsw.backendSiderandina.repository.PedidoVentaRepository;
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
    @Autowired
    private PedidoVentaRepository pedidoVentaRepository;


    @PostMapping("/programar")
    public Despacho programar(@RequestParam Integer idPedidoVenta, @RequestParam String fechaProgramada) {
        try {
            
            String fecha = fechaProgramada.trim() + " 00:00:00";
            return despachoService.programarDespacho(idPedidoVenta, Timestamp.valueOf(fecha));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al programar despacho: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/estado")
    public Despacho actualizarEstado(@PathVariable Integer id, @RequestParam String estado) {
        return despachoService.actualizarEstado(id, estado);
    }

    @GetMapping
    public List<DespachoResponse> listarTodos() {
        List<Despacho> despachos = despachoService.listarTodos();
        return despachos.stream()
            .map(d -> new DespachoResponse(d, pedidoVentaRepository.findById(d.getIdPedidoVenta()).orElse(null)))
            .toList();
    }

    // filtrar por estado
    @GetMapping("/estado")
    public List<DespachoResponse> listarPorEstado(@RequestParam String estado) {
        List<Despacho> despachos = despachoService.listarPorEstado(estado);
        return despachos.stream()
            .map(d -> new DespachoResponse(d, pedidoVentaRepository.findById(d.getIdPedidoVenta()).orElse(null)))
            .toList();
    }
}