package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import dsw.backendSiderandina.model.DetalleCotizacion;

import java.util.List;

@Repository
public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Integer> {
    @Query(value = """
        SELECT 
            p.id_producto,
            p.nombre AS producto,
            p.sku,
            SUM(d.cantidad) AS total_unidades_vendidas
        FROM 
            detalle_cotizacion d
        JOIN 
            producto p ON d.id_producto = p.id_producto
        GROUP BY 
            p.id_producto, p.nombre, p.sku
        ORDER BY 
            total_unidades_vendidas DESC
        """, nativeQuery = true)
    List<Object[]> productosMasVendidos();
}