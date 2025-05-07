package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.ComprobanteCompra;

@Repository
public interface ComprobanteCompraRepository extends JpaRepository<ComprobanteCompra, Integer> {

}
