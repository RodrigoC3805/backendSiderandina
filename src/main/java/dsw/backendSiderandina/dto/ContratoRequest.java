package dsw.backendSiderandina.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratoRequest {
    private Integer idContrato; // Opcional para creación
    private Integer idTrabajador;
    private Integer idEstadoContrato;
    private Date fechaInicio;
    private Date fechaFin;
    private Double remuneracion;
}