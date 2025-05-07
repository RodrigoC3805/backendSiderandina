package dsw.backendSiderandina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dsw.backendSiderandina.dto.PedidoCompraResponse;
import dsw.backendSiderandina.service.PedidoCompraService;
import dsw.backendSiderandina.utils.ErrorResponse;

import org.springframework.web.bind.annotation.GetMapping;


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
    
}
