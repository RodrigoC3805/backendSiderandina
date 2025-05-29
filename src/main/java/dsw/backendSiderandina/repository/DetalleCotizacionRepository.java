package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dsw.backendSiderandina.model.DetalleCotizacion;
import dsw.backendSiderandina.model.DetalleCotizacionId;

@Repository
public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, DetalleCotizacionId> {
}