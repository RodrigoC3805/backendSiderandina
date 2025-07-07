package dsw.backendSiderandina.repository;

import dsw.backendSiderandina.dto.PlanillaResumenResponse;
import dsw.backendSiderandina.model.Planilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanillaRepository extends JpaRepository<Planilla, Integer> {
    List<Planilla> findByMesAndAnio(Integer mes, Integer anio);
    List<Planilla> findByAnio(Integer anio);
    @Query("SELECT new dsw.backendSiderandina.dto.PlanillaResumenResponse(" +
        "p.idPlanilla, p.mes, p.anio, CAST(p.fechaGeneracion AS string), p.totalSueldos, COUNT(dp.idDetallePlanilla)) " +
        "FROM Planilla p LEFT JOIN DetallePlanilla dp ON dp.planilla.idPlanilla = p.idPlanilla " +
        "GROUP BY p.idPlanilla, p.mes, p.anio, p.fechaGeneracion, p.totalSueldos " +
        "ORDER BY p.anio DESC, p.mes DESC, p.fechaGeneracion DESC")
    List<PlanillaResumenResponse> findPlanillasConCantidadTrabajadores();
}