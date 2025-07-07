package dsw.backendSiderandina.repository;

import dsw.backendSiderandina.model.DetallePlanilla;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetallePlanillaRepository extends JpaRepository<DetallePlanilla, Integer> {
    List<DetallePlanilla> findByPlanilla_IdPlanilla(Integer idPlanilla);
}