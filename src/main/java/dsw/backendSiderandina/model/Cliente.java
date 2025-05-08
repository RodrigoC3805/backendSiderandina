package dsw.backendSiderandina.model;

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
@Table(name="cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cliente")
    private Integer idCliente;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_tipo_cliente", referencedColumnName = "id_tipo_cliente")
    private TipoCliente tipoCliente;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", referencedColumnName = "id_usuario", nullable = true)
    private Usuario usuario; // Un cliente puede o no tener un usuario de sistema

    @Column(name="ruc")
    private String ruc;

    @Column(name="razon_social")
    private String razonSocial;

    @Column(name="direccion")
    private String direccion;

    @Column(name="telefono")
    private String telefono;
}