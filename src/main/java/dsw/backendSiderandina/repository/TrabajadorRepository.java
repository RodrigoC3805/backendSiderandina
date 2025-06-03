package dsw.backendSiderandina.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsw.backendSiderandina.model.Trabajador;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer> {
    Optional<Trabajador> findByNumeroDocumento(String numeroDocumento);
}
