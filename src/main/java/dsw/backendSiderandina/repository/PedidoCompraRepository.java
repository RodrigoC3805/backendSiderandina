package dsw.backendSiderandina.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT " +
            "p.ruc as ruc, " +
            "p.razon_social as razonSocial, " +
            "COUNT(pc.id_pedido_compra) as totalPedidos, " +
            "SUM(pc.monto_total) as montoTotalCompras, " +
            "AVG(pc.monto_total) as promedioPorPedido " +
            "FROM pedido_compra pc " +
            "JOIN proveedor p ON pc.id_proveedor = p.id_proveedor " +
            "GROUP BY p.ruc, p.razon_social " +
            "ORDER BY montoTotalCompras DESC",
            nativeQuery = true)
    List<Object[]> reporteComprasPorProveedorRaw();

    @Query(value = """
        SELECT 
            p.razon_social,
            COUNT(pc.id_pedido_compra) AS cantidad_pedidos
        FROM 
            pedido_compra pc
        JOIN proveedor p ON pc.id_proveedor = p.id_proveedor
        WHERE
            pc.fecha_pedido BETWEEN :fechaInicio AND :fechaFin
        GROUP BY 
            p.razon_social
        ORDER BY 
            cantidad_pedidos DESC
        """, nativeQuery = true)
    List<Object[]> cantidadPedidosPorProveedor(
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin
    );

}
