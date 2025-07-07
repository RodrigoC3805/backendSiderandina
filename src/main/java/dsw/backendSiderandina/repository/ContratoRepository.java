package dsw.backendSiderandina.repository;

import dsw.backendSiderandina.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    List<Contrato> findByTrabajadorIdTrabajador(Integer idTrabajador);
    Contrato findTopByTrabajadorIdTrabajadorAndEstadoContratoDescripcionOrderByFechaFinDesc(Integer idTrabajador, String descripcion);
    Contrato findTopByTrabajadorIdTrabajadorOrderByFechaFinDesc(Integer idTrabajador);
    @Query("SELECT c FROM Contrato c WHERE :fecha BETWEEN c.fechaInicio AND c.fechaFin")
    List<Contrato> findContratosVigentes(@Param("fecha") Date fecha);
}