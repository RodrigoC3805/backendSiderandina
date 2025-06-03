package dsw.backendSiderandina.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asistencia_diaria")
public class AsistenciaDiaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAsistencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_trabajador", referencedColumnName = "id_trabajador")
    private Trabajador trabajador;

    private LocalDate fecha;
    private LocalTime horaIngreso;
    private LocalTime horaSalida;
}