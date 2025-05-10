package dsw.backendSiderandina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.TrabajadorRequest;
import dsw.backendSiderandina.dto.TrabajadorResponse;
import dsw.backendSiderandina.dto.TrabajadorListItem;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.model.Contrato;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import dsw.backendSiderandina.repository.TipoDocumentoRepository;
import dsw.backendSiderandina.repository.TipoTrabajadorRepository;
import dsw.backendSiderandina.repository.ContratoRepository;

@Service
public class TrabajadorService {
    @Autowired
    TrabajadorRepository trabajadorRepository;

    @Autowired
    TipoDocumentoRepository tipoDocumentoRepository;
    
    @Autowired
    TipoTrabajadorRepository tipoTrabajadorRepository;

    @Autowired
    ContratoRepository contratoRepository;

    public TrabajadorResponse createTrabajador(TrabajadorRequest trabajadorRequest) {
        Trabajador trabajador = Trabajador.builder()
                .tipoDocumento(tipoDocumentoRepository.findById(trabajadorRequest.getIdTipoDocumento()).orElse(null))
                .tipoTrabajador(tipoTrabajadorRepository.findById(trabajadorRequest.getIdTipoTrabajador()).orElse(null))
                .numeroDocumento(trabajadorRequest.getNumeroDocumento())
                .apellidoPaterno(trabajadorRequest.getApellidoPaterno())
                .apellidoMaterno(trabajadorRequest.getApellidoMaterno())
                .nombres(trabajadorRequest.getNombres())
                .emailContacto(trabajadorRequest.getEmailContacto())
                .build();

        trabajador = trabajadorRepository.save(trabajador);
        return TrabajadorResponse.fromEntity(trabajador);
    }

    // Este es el método que tu frontend debe consumir
    public List<TrabajadorListItem> listarTrabajadores() {
        return trabajadorRepository.findAll().stream()
            .map(trabajador -> {
                // Busca el contrato activo del trabajador
                Contrato contrato = contratoRepository.findFirstByTrabajadorIdTrabajadorAndEstadoContratoDescripcion(
                    trabajador.getIdTrabajador(), "Activo"
                );
                Double sueldo = contrato != null && contrato.getRemuneracion() != null ? contrato.getRemuneracion() : 0.0;
                String moneda = "Soles"; // Cambia si tienes el campo en tu modelo
                String estadoContrato = contrato != null && contrato.getEstadoContrato() != null
                        ? contrato.getEstadoContrato().getDescripcion()
                        : "";

                return new TrabajadorListItem(
                    trabajador.getIdTrabajador(),
                    trabajador.getNombres() + " " + trabajador.getApellidoPaterno() + " " + trabajador.getApellidoMaterno(),
                    trabajador.getTipoDocumento() != null ? trabajador.getTipoDocumento().getDescripcion() : "",
                    trabajador.getNumeroDocumento(),
                    trabajador.getTipoTrabajador() != null ? trabajador.getTipoTrabajador().getDescripcion() : "",
                    sueldo,
                    moneda,
                    estadoContrato
                );
            })
            .toList();
    }
}