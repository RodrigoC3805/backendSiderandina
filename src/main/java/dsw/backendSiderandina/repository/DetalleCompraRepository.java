package dsw.backendSiderandina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.DetalleCompra;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Integer>{
    List<DetalleCompra> findByPedidoCompra_IdPedidoCompra(Integer idPedidoCompra);
}
