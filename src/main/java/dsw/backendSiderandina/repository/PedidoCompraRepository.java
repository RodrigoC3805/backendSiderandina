package dsw.backendSiderandina.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.PedidoCompra;

@Repository
public interface PedidoCompraRepository extends JpaRepository<PedidoCompra, Integer> {
    // Buscar por proveedor
    List<PedidoCompra> findByProveedor_IdProveedor(Integer idProveedor);

    // Buscar por proveedor y estado
    List<PedidoCompra> findByProveedor_IdProveedorAndEstadoPedido_IdEstadoPedido(Integer idProveedor, Integer idEstadoPedido);

    // Buscar por proveedor y ordenar por fecha
    List<PedidoCompra> findByProveedor_IdProveedorOrderByFechaPedidoDesc(Integer idProveedor);

    @Query("SELECT p FROM PedidoCompra p WHERE p.estadoPedido.idEstadoPedido <> 1 ORDER BY p.estadoPedido.idEstadoPedido ASC, p.fechaPedido DESC")
    List<PedidoCompra> findAllPedidosEnviadosYEntregados();
}
