package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.TipoComprobante;

@Repository
public interface TipoComprobanteRepository extends JpaRepository<TipoComprobante, Integer>{

}
