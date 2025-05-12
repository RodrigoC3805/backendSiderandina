package dsw.backendSiderandina.model;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="contrato")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_contrato")
    private Integer idContrato;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_trabajador", referencedColumnName = "id_trabajador")
    private Trabajador trabajador; // Asumiendo que Trabajador.java ya existe

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_estado_contrato", referencedColumnName = "id_estado_contrato")
    private EstadoContrato estadoContrato;

    @Column(name="fecha_inicio")
    private Date fechaInicio;

    @Column(name="fecha_fin")
    private Date fechaFin;

    @Column(name="remuneracion")
    private Double remuneracion;
}