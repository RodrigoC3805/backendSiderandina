package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer>{

}
