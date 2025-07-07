package dsw.backendSiderandina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.dto.JustificacionResponse;
import dsw.backendSiderandina.model.Justificacion;

@Repository
public interface JustificacionRepository extends JpaRepository<Justificacion, Integer> {
    List<Justificacion> findByAsistenciaDiariaTrabajadorIdTrabajador(Integer idTrabajador);

    @Query("SELECT new dsw.backendSiderandina.dto.JustificacionResponse(" +
            "j.idJustificacion, " +
            "j.asistenciaDiaria, " +
            "j.motivoJustificacion, " +
            "j.fechaSolicitud, " +
            "j.estadoJustificacion) " +
            "FROM Justificacion j " +
            "WHERE j.asistenciaDiaria.trabajador.idTrabajador = :idTrabajador")
    List<JustificacionResponse> findMisJustificacionesNoPDF(@Param("idTrabajador") Integer idTrabajador);
}
