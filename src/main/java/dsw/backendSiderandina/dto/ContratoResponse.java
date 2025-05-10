package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.Contrato;
// Asumiendo que TrabajadorResponse.java ya existe y está en este paquete o importable
// import dsw.backendSiderandina.dto.TrabajadorResponse; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratoResponse {
    private Integer idContrato;
    private TrabajadorResponse trabajador; // Usar el DTO de respuesta para Trabajador
    private EstadoContratoResponse estadoContrato;
    private Date fechaInicio;
    private Date fechaFin;
    private Double remuneracion;

    public static ContratoResponse fromEntity(Contrato contrato) {
        if (contrato == null) return null;
        return ContratoResponse.builder()
                .idContrato(contrato.getIdContrato())
                .trabajador(TrabajadorResponse.fromEntity(contrato.getTrabajador())) // Convertir entidad Trabajador a DTO
                .estadoContrato(EstadoContratoResponse.fromEntity(contrato.getEstadoContrato()))
                .fechaInicio(contrato.getFechaInicio())
                .fechaFin(contrato.getFechaFin())
                .remuneracion(contrato.getRemuneracion())
                .build();
    }

    public static List<ContratoResponse> fromEntities(List<Contrato> contratos) {
        return contratos.stream()
                .map(ContratoResponse::fromEntity)
                .collect(Collectors.toList());
    }
}