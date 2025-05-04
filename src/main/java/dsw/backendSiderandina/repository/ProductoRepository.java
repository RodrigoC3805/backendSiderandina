package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

}
