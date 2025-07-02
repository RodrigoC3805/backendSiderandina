package dsw.backendSiderandina.repository;

import dsw.backendSiderandina.model.Justificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JustificacionRepository extends JpaRepository<Justificacion, Integer> {
    List<Justificacion> findByAsistenciaDiariaTrabajadorIdTrabajador(Integer idTrabajador);
}
