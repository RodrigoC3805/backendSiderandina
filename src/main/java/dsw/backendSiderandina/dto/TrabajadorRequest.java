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
public class TrabajadorRequest {
    private Integer idTrabajador;
    private Integer idTipoDocumento;
    private String numeroDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String emailContacto;
    private Date fechaInicioContrato;
    private Date fechaFinContrato;
    private Double sueldo;
    private Integer idEstadoContrato; 
}
