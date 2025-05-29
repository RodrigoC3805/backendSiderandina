package dsw.backendSiderandina.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCotizacionResponse {
    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidad;
}