package dsw.backendSiderandina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dsw.backendSiderandina.dto.ProductoRequest;
import dsw.backendSiderandina.dto.ProductoResponse;
import dsw.backendSiderandina.service.ProductoService;
import dsw.backendSiderandina.utils.ErrorResponse;

@RestController
@RequestMapping(path="api/v1/producto")
public class ProductoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> getProductos() {
        List<ProductoResponse> listaProductoResponse = null;
        try {
            listaProductoResponse = productoService.listProductos();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaProductoResponse.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("Producto not found"));
        return ResponseEntity.ok(listaProductoResponse);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findProductoById(@RequestBody ProductoRequest productoRequest) {
        logger.info(">find " + productoRequest.toString());
        ProductoResponse productoResponse;
        try {
            productoResponse=productoService.findProducto(productoRequest.getIdProducto());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (productoResponse==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("Producto not found"));
        return ResponseEntity.ok(productoResponse);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {
    List<ProductoResponse> productos;
    try {
        productos = productoService.buscarPorNombre(nombre);
    } catch (Exception e) {
        logger.error("Error inesperado", e);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (productos == null || productos.isEmpty())
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder().message("Producto not found"));
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<?> insertProducto(@RequestBody ProductoRequest productoRequest) {
        logger.info(">insert " + productoRequest.toString());
        ProductoResponse productoResponse;
        try {
            productoResponse = productoService.insertProducto(productoRequest);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (productoResponse==null) 
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("Producto not found"));
        return ResponseEntity.ok(productoResponse);
    }

    @PutMapping
    public ResponseEntity<?> updateProducto(@RequestBody ProductoRequest productoRequest) {
        logger.info(">update " + productoRequest.toString());
        ProductoResponse productoResponse;
        try {
            productoResponse = productoService.updateProducto(productoRequest);
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (productoResponse == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("Producto not found"));
        return ResponseEntity.ok(productoResponse);
    }

     @DeleteMapping
    public ResponseEntity<?> deleteProducto(@RequestBody ProductoRequest productoRequest) {
        logger.info(">delete " + productoRequest.toString());
        ProductoResponse productoResponse;
        try {
            productoResponse=productoService.findProducto(productoRequest.getIdProducto());
            if (productoResponse==null)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().message("Producto not found for deletion"));
            productoService.deleteProducto(productoResponse.getIdProducto());
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(productoResponse);
    }

    

}
