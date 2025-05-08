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

import dsw.backendSiderandina.dto.CategoriaProductoResponse;
import dsw.backendSiderandina.service.CategoriaProductoService;
import dsw.backendSiderandina.utils.ErrorResponse;

@RestController
@RequestMapping(path="api/v1/categoriaproducto")
public class CategoriaProductoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CategoriaProductoService categoriaProductoService;

    @GetMapping
    public ResponseEntity<?> getCategoriasProducto() {
        List<CategoriaProductoResponse> listaCategoriaProductoResponse = null;
        try {
            listaCategoriaProductoResponse = categoriaProductoService.getCategoriaProducto();
        } catch (Exception e) {
            logger.error("Error inesperado", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (listaCategoriaProductoResponse.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder().message("CategoriaProducto not found").build());
        return ResponseEntity.ok(listaCategoriaProductoResponse);
    }
}
