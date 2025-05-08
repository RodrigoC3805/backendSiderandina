package dsw.backendSiderandina.repository;

import dsw.backendSiderandina.model.EstadoCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoCotizacionRepository extends JpaRepository<EstadoCotizacion, Integer> {}
