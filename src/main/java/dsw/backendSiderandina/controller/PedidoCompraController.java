package dsw.backendSiderandina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dsw.backendSiderandina.dto.PedidoCompraRequest;
import dsw.backendSiderandina.dto.PedidoCompraResponse;
import dsw.backendSiderandina.dto.PedidoYDetallesDTO;
import dsw.backendSiderandina.model.ComprobanteCompra;
import dsw.backendSiderandina.model.DetalleCompra;
import dsw.backendSiderandina.model.Pago;
import dsw.backendSiderandina.model.PedidoCompra;
import dsw.backendSiderandina.service.PedidoCompraService;
import dsw.backendSiderandina.utils.ErrorResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path="api/v1/pedidocompra")
public class PedidoCompraController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    PedidoCompraService pedidoCompraService;

    @GetMapping
    public ResponseEntity<?> getPedidosCompra() {
        List<PedidoCompraResponse> listaPedidoCompraResponse = null;
        try {
            listaPedidoCompraResponse = pedidoCompraService.listPedidosCompra();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaPedidoCompraResponse.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("PedidoCompra not found").build());
        return ResponseEntity.ok(listaPedidoCompraResponse);
    }
    @PostMapping
    public ResponseEntity<?> createPedidoCompra(@RequestBody PedidoCompraRequest pedidoCompraRequest) {
        PedidoCompraResponse pedidoCompraResponse;
        try {
            pedidoCompraResponse = pedidoCompraService.createPedidoCompra(pedidoCompraRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if (pedidoCompraResponse == null) 
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("PedidoCompra not inserted").build());
        return ResponseEntity.ok(pedidoCompraResponse);
    }
    @PostMapping("/comprar")
    public ResponseEntity<?> crearPedidoConDetalles(@RequestBody PedidoYDetallesDTO pedidoYDetallesDTO) {
        try {
            PedidoCompra pedido = pedidoYDetallesDTO.getPedidoCompra();
            List<DetalleCompra> detalles = pedidoYDetallesDTO.getDetallesCompra();
            Pago pago = pedidoYDetallesDTO.getPago();
            ComprobanteCompra comprobante = pedidoYDetallesDTO.getComprobanteCompra();

            PedidoCompra savedPedido = pedidoCompraService.createPedidoCompraConDetalles(
                    pedido, detalles, pago, comprobante);

            return ResponseEntity.ok(savedPedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el pedido con sus detalles: " + e.getMessage());
        }
    }
}
