package dsw.backendSiderandina.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoContratoRequest {
    private Integer idEstadoContrato;
    private String descripcion;
}