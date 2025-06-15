package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.EstadoCotizacion;

@Repository
public interface EstadoCotizacionRepository extends JpaRepository<EstadoCotizacion, Integer> {

}
