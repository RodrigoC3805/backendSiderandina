package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.Trabajador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorResponse {
    private Integer idTrabajador;
    private TipoDocumentoResponse tipoDocumento; // Respuesta del tipo de documento
    private TipoTrabajadorResponse tipoTrabajador; // Respuesta del tipo de trabajador
    private String numeroDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String emailContacto;

    public static TrabajadorResponse fromEntity(Trabajador trabajador) {
        return TrabajadorResponse.builder()
                .idTrabajador(trabajador.getIdTrabajador())
                .tipoDocumento(TipoDocumentoResponse.fromEntity(trabajador.getTipoDocumento()))
                .tipoTrabajador(TipoTrabajadorResponse.fromEntity(trabajador.getTipoTrabajador()))
                .numeroDocumento(trabajador.getNumeroDocumento())
                .apellidoPaterno(trabajador.getApellidoPaterno())
                .apellidoMaterno(trabajador.getApellidoMaterno())
                .nombres(trabajador.getNombres())
                .emailContacto(trabajador.getEmailContacto())
                .build();
    }

    public static List<TrabajadorResponse> fromEntities(List<Trabajador> trabajadores) {
        return trabajadores.stream()
                .map(TrabajadorResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
