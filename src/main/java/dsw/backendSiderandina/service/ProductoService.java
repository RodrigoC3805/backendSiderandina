package dsw.backendSiderandina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.ProductoRequest;
import dsw.backendSiderandina.dto.ProductoResponse;
import dsw.backendSiderandina.model.Producto;
import dsw.backendSiderandina.repository.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    ProductoRepository productoRepository;
    public List<ProductoResponse> listProductos() {
        return ProductoResponse.fromEntities(productoRepository.findAll());
    }

    public ProductoResponse insertProducto(ProductoRequest productoRequest) {
        Producto producto = new Producto(
            productoRequest.getIdProducto(),
            productoRequest.getSku(),
            productoRequest.getNombre(),
            productoRequest.getPrecioVentaBase(),
            productoRequest.getCostoUnitarioBase(),
            productoRequest.getStock(),
            productoRequest.getStockMin(),
            productoRequest.getUrlImagen(),
            null, // Assuming CategoriaProducto and UnidadesMedida are set later
            null
        );
        producto = productoRepository.save(producto);
        return ProductoResponse.fromEntity(producto);
    }

    public ProductoResponse updateProducto(ProductoRequest productoRequest) {
        Producto producto = new Producto(
            productoRequest.getIdProducto(),
            productoRequest.getSku(),
            productoRequest.getNombre(),
            productoRequest.getPrecioVentaBase(),
            productoRequest.getCostoUnitarioBase(),
            productoRequest.getStock(),
            productoRequest.getStockMin(),
            productoRequest.getUrlImagen(),
            null, // Assuming CategoriaProducto and UnidadesMedida are set later
            null
        );
        producto = productoRepository.save(producto);
        return ProductoResponse.fromEntity(producto);
    }

    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    public ProductoResponse findProducto(Integer id) {
        Optional<Producto> result = productoRepository.findById(id);
        if (!result.isPresent())
            return null;
        return ProductoResponse.fromEntity(result.get());
    }
    
    // metodo para buscar productos por nombre
    public List<ProductoResponse> buscarPorNombre(String nombre) {
        return ProductoResponse.fromEntities(productoRepository.findByNombreContainingIgnoreCase(nombre));
    }

}
