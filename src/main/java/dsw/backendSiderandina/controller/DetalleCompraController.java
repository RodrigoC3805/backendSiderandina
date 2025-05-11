package dsw.backendSiderandina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dsw.backendSiderandina.model.DetalleCompra;
import dsw.backendSiderandina.model.PedidoCompra;
import dsw.backendSiderandina.repository.DetalleCompraRepository;
import dsw.backendSiderandina.utils.ErrorResponse;

@RestController
@RequestMapping(path="api/v1/detallecompra")
public class DetalleCompraController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DetalleCompraRepository detalleCompraRepository;

    @PostMapping("/find")
    public ResponseEntity<?> getDetalleComprabyCompraId(@RequestBody PedidoCompra pedidoCompra) {
        List<DetalleCompra> listaDetalleCompra = null;
        try{
            listaDetalleCompra = detalleCompraRepository.findByPedidoCompra_IdPedidoCompra(pedidoCompra.getIdPedidoCompra());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaDetalleCompra.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("DetalleCompra not found").build());
        return ResponseEntity.ok(listaDetalleCompra);
    }
    
}
