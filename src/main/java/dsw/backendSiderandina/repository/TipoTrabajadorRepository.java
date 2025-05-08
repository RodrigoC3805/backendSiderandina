package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.TipoTrabajador;

@Repository
public interface TipoTrabajadorRepository extends JpaRepository<TipoTrabajador, Integer> {

}
