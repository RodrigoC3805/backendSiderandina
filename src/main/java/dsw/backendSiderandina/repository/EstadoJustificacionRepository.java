package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.EstadoJustificacion;

@Repository
public interface EstadoJustificacionRepository extends JpaRepository<EstadoJustificacion, Integer>{
    
}
