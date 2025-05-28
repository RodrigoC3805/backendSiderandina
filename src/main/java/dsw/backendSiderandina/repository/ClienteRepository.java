package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByRuc(String ruc);
}
