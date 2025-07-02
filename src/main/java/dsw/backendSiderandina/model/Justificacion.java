package dsw.backendSiderandina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="justificacion")
public class Justificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_justificacion")
    private Integer idJustificacion;
    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;
    //@Lob
    @Column(name = "doc_sustento", columnDefinition = "bytea")
    private byte[] docSustento;

    @ManyToOne
    @JoinColumn(name = "id_asistencia_diaria", referencedColumnName = "id_asistencia_diaria")
    private AsistenciaDiaria asistenciaDiaria;
    @ManyToOne
    @JoinColumn(name = "id_motivo", referencedColumnName = "id_motivo_justificacion")
    private MotivoJustificacion motivoJustificacion;
}
