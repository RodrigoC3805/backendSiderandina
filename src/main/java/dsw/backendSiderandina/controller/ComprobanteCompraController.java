package dsw.backendSiderandina.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dsw.backendSiderandina.model.ComprobanteCompra;
import dsw.backendSiderandina.model.PedidoCompra;
import dsw.backendSiderandina.repository.ComprobanteCompraRepository;
import dsw.backendSiderandina.utils.ErrorResponse;

@RestController
@RequestMapping(path="api/v1/comprobantecompra")
public class ComprobanteCompraController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ComprobanteCompraRepository comprobanteCompraRepository;

    @GetMapping("/find")
    public ResponseEntity<?> getComprobanteComprabyPedidoId(@RequestBody PedidoCompra pedidoCompra) {
        ComprobanteCompra comprobanteCompra = null;
        try {
            comprobanteCompra = comprobanteCompraRepository.findByPedidoCompra_IdPedidoCompra(pedidoCompra.getIdPedidoCompra());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (comprobanteCompra==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("Comprobante not found").build());
        return ResponseEntity.ok(comprobanteCompra);
    }
}
