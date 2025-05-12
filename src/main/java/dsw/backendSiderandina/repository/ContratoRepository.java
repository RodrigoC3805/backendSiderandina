package dsw.backendSiderandina.repository;

import dsw.backendSiderandina.model.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    List<Contrato> findByTrabajadorIdTrabajador(Integer idTrabajador);
    Contrato findTopByTrabajadorIdTrabajadorAndEstadoContratoDescripcionOrderByFechaFinDesc(Integer idTrabajador, String descripcion);
    Contrato findTopByTrabajadorIdTrabajadorOrderByFechaFinDesc(Integer idTrabajador);
}