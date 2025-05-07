package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.EstadoDetalleCompra;

@Repository
public interface EstadoDetalleCompraRepository extends JpaRepository<EstadoDetalleCompra, Integer>{

}
