package dsw.backendSiderandina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dsw.backendSiderandina.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    Proveedor findByEmail(String email);
}