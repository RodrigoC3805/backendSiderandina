package dsw.backendSiderandina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dsw.backendSiderandina.dto.PedidoVentaRequest;
import dsw.backendSiderandina.dto.PedidoVentaResponse;
import dsw.backendSiderandina.service.PedidoVentaService;

@RestController
@RequestMapping("/api/cliente/pedidoventa")
public class PedidoVentaController {
    @Autowired
    PedidoVentaService pedidoVentaService;

    @PostMapping
    public ResponseEntity<?> crearPedidoVenta(@RequestBody PedidoVentaRequest request) {
        try {
            PedidoVentaResponse response = pedidoVentaService.crearPedidoVenta(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear pedido de venta: " + e.getMessage());
        }
    }
}
