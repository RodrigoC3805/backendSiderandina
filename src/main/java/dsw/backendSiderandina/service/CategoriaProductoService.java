package dsw.backendSiderandina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.model.CategoriaProducto;
import dsw.backendSiderandina.repository.CategoriaProductoRepository;

@Service
public class CategoriaProductoService {
    @Autowired
    CategoriaProductoRepository categoriaProductoRepository;

    public List<CategoriaProducto> getCategoriaProducto() {
        return categoriaProductoRepository.findAll();
    }
}
