package dsw.backendSiderandina.repository;

import dsw.backendSiderandina.model.MotivoJustificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotivoJustificacionRepository extends JpaRepository<MotivoJustificacion, Integer> {
}
