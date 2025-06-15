package dsw.backendSiderandina.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrabajadorListItem {
    private Integer idTrabajador;
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private Double sueldo;
    private String moneda;
    private String estadoContrato;
}