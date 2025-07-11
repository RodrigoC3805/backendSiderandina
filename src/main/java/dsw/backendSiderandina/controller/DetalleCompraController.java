package dsw.backendSiderandina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dsw.backendSiderandina.model.DetalleCompra;
import dsw.backendSiderandina.model.EstadoDetalleCompra;
import dsw.backendSiderandina.model.EstadoPedido;
import dsw.backendSiderandina.model.PedidoCompra;
import dsw.backendSiderandina.repository.DetalleCompraRepository;
import dsw.backendSiderandina.repository.EstadoDetalleCompraRepository;
import dsw.backendSiderandina.repository.EstadoPedidoRepository;
import dsw.backendSiderandina.repository.PedidoCompraRepository;
import dsw.backendSiderandina.utils.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path="api/almacen/detallecompra")
public class DetalleCompraController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DetalleCompraRepository detalleCompraRepository;
    @Autowired
    EstadoDetalleCompraRepository estadoDetalleCompraRepository;
    @Autowired
    PedidoCompraRepository pedidoCompraRepository;
    @Autowired
    EstadoPedidoRepository estadoPedidoRepository;
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
    
    @PostMapping
    public ResponseEntity<?> createDetallesCompra(@RequestBody List<DetalleCompra> detallesCompra) {
        try {
            List<DetalleCompra> savedDetalles = detalleCompraRepository.saveAll(detallesCompra);
            return ResponseEntity.ok(savedDetalles);
        } catch (Exception e) {
            logger.error("Error al crear detalles de compra", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder().message("Error al crear detalles de compra").build());
        }
    }
    @PutMapping("actualizar-estados")
    public ResponseEntity<?> updateEstadosDetalles(@RequestBody List<DetalleCompra> detallesCompra) {
        try {
            List<DetalleCompra> savedDetalles = detalleCompraRepository.saveAll(detallesCompra);
            PedidoCompra pedidoCompra = detallesCompra.get(0).getPedidoCompra();
            pedidoCompra.setEstadoPedido(estadoPedidoRepository.findById(3).get());
            pedidoCompraRepository.save(pedidoCompra);
            return ResponseEntity.ok(savedDetalles);
        } catch (Exception e) {
            logger.error("Error al crear detalles de compra", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder().message("Error al actualizar detalles de compra").build());
        }
    }
    @GetMapping("estados")
    public ResponseEntity<?> getEstados() {
        try {
            List<EstadoDetalleCompra> estados = estadoDetalleCompraRepository.findAll();
            return ResponseEntity.ok(estados);
        } catch (Exception e) {
            logger.error("Error al obtener estados", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder().message("Error al obtener estados").build());
        }
    }

}
