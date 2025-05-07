package dsw.backendSiderandina.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
