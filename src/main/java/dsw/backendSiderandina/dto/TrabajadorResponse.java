package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.model.Usuario;
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
    private String numeroDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String emailContacto;
    private ContratoResponse contrato;
    private Usuario usuario;

public static TrabajadorResponse fromEntity(Trabajador trabajador) {
    return TrabajadorResponse.builder()
            .idTrabajador(trabajador.getIdTrabajador())
            .tipoDocumento(TipoDocumentoResponse.fromEntity(trabajador.getTipoDocumento()))
            .numeroDocumento(trabajador.getNumeroDocumento())
            .apellidoPaterno(trabajador.getApellidoPaterno())
            .apellidoMaterno(trabajador.getApellidoMaterno())
            .nombres(trabajador.getNombres())
            .emailContacto(trabajador.getEmailContacto())
            .contrato(null) // O puedes intentar obtener el contrato si lo tienes en la entidad
            .build();
    }
    public static TrabajadorResponse fromEntity(Trabajador trabajador, dsw.backendSiderandina.model.Contrato contrato) {
        return TrabajadorResponse.builder()
                .idTrabajador(trabajador.getIdTrabajador())
                .tipoDocumento(TipoDocumentoResponse.fromEntity(trabajador.getTipoDocumento()))
                .numeroDocumento(trabajador.getNumeroDocumento())
                .apellidoPaterno(trabajador.getApellidoPaterno())
                .apellidoMaterno(trabajador.getApellidoMaterno())
                .nombres(trabajador.getNombres())
                .emailContacto(trabajador.getEmailContacto())
                .contrato(contrato != null ? ContratoResponse.fromEntity(contrato) : null)
                .build();
    }

    public static List<TrabajadorResponse> fromEntities(List<Trabajador> trabajadores) {
        return trabajadores.stream()
                .map(TrabajadorResponse::fromEntity)
                .collect(Collectors.toList());
    }
    public static Trabajador toEntity (TrabajadorResponse response){
        return Trabajador.builder()
                .idTrabajador(response.getIdTrabajador())
                .apellidoPaterno(response.getApellidoPaterno())
                .apellidoMaterno(response.getApellidoMaterno())
                .nombres(response.getNombres())
                .emailContacto(response.getEmailContacto())
                .numeroDocumento(response.getNumeroDocumento())
                .tipoDocumento(TipoDocumentoResponse.toEntity(response.getTipoDocumento()))
                .usuario(response.getUsuario())
                .build();
    }
}
