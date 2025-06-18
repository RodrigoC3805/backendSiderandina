package dsw.backendSiderandina.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dsw.backendSiderandina.model.PedidoVenta;

@Repository
public interface PedidoVentaRepository extends JpaRepository<PedidoVenta, Integer> {
    List<PedidoVenta> findByCotizacion_IdCotizacion(Integer idCotizacion);
}
