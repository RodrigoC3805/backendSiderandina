package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.PedidoCompra;

@Repository
public interface PedidoCompraRepository extends JpaRepository<PedidoCompra, Integer>{

}
