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
    @Column(name = "id_asistencia_diaria")
    private Integer idAsistencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_trabajador", referencedColumnName = "id_trabajador")
    private Trabajador trabajador;
    
    @Column(name="dia_asistencia")
    private LocalDate fecha;
    @Column(name="hora_entrada")
    private LocalTime horaIngreso;
    @Column(name="hora_salida")
    private LocalTime horaSalida;
}