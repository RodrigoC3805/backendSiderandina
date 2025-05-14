package dsw.backendSiderandina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    List<Producto> findByNombreContainingIgnoreCase(String nombre); //buscar producto por nombre
}
