package dsw.backendSiderandina.repository;

import dsw.backendSiderandina.model.AsistenciaDiaria;
import dsw.backendSiderandina.model.Trabajador;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AsistenciaDiariaRepository extends JpaRepository<AsistenciaDiaria, Integer> {
    // Busca la asistencia de un trabajador en una fecha específica
    Optional<AsistenciaDiaria> findByTrabajadorAndFecha(Trabajador trabajador, LocalDate fecha);
}
