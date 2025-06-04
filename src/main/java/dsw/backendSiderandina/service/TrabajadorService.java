package dsw.backendSiderandina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.TrabajadorRequest;
import dsw.backendSiderandina.dto.TrabajadorResponse;
import dsw.backendSiderandina.dto.TrabajadorListItem;
import dsw.backendSiderandina.model.EstadoContrato;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.model.Cliente;
import dsw.backendSiderandina.model.Contrato;
import dsw.backendSiderandina.repository.EstadoContratoRepository;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import dsw.backendSiderandina.repository.TipoDocumentoRepository;
import dsw.backendSiderandina.repository.TipoTrabajadorRepository;
import dsw.backendSiderandina.repository.ContratoRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Autowired
    EstadoContratoRepository estadoContratoRepository;

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

        // Crear contrato si se envían los datos
        if (trabajadorRequest.getFechaInicioContrato() != null && trabajadorRequest.getSueldo() != null) {
            EstadoContrato estadoContrato = estadoContratoRepository.findById(trabajadorRequest.getIdEstadoContrato()).orElse(null);
            Contrato contrato = Contrato.builder()
                .trabajador(trabajador)
                .estadoContrato(estadoContrato)
                .fechaInicio(trabajadorRequest.getFechaInicioContrato())
                .fechaFin(trabajadorRequest.getFechaFinContrato())
                .remuneracion(trabajadorRequest.getSueldo())
                .build();
            contratoRepository.save(contrato);
        }

        return TrabajadorResponse.fromEntity(trabajador);
    }

    public TrabajadorResponse getTrabajador(Integer idTrabajador) {
        Trabajador trabajador = trabajadorRepository.findById(idTrabajador)
            .orElseThrow(() -> new EntityNotFoundException("Trabajador no encontrado con ID: " + idTrabajador));
        // Busca el contrato más relevante
        Contrato contrato = contratoRepository.findTopByTrabajadorIdTrabajadorAndEstadoContratoDescripcionOrderByFechaFinDesc(
            trabajador.getIdTrabajador(), "Activo"
        );
        if (contrato == null) {
            contrato = contratoRepository.findTopByTrabajadorIdTrabajadorOrderByFechaFinDesc(trabajador.getIdTrabajador());
        }
        return TrabajadorResponse.fromEntity(trabajador, contrato);
    }
    
    public void eliminarTrabajador(Integer idTrabajador) {
        // Elimina primero los contratos asociados
        contratoRepository.deleteAll(contratoRepository.findByTrabajadorIdTrabajador(idTrabajador));
        // Luego elimina el trabajador
        trabajadorRepository.deleteById(idTrabajador);
    }

    // Este es el método que tu frontend debe consumir
    public List<TrabajadorListItem> listarTrabajadores() {
        return trabajadorRepository.findAll().stream()
            .map(trabajador -> {
                // Busca el contrato "Activo"
                Contrato contrato = contratoRepository.findTopByTrabajadorIdTrabajadorAndEstadoContratoDescripcionOrderByFechaFinDesc(
                    trabajador.getIdTrabajador(), "Activo"
                );
                // Si no hay contrato "Activo", busca el más reciente
                if (contrato == null) {
                    contrato = contratoRepository.findTopByTrabajadorIdTrabajadorOrderByFechaFinDesc(trabajador.getIdTrabajador());
                }
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

    public TrabajadorResponse updateTrabajador(Integer idTrabajador, TrabajadorRequest request) {
        Trabajador trabajador = trabajadorRepository.findById(idTrabajador)
                .orElseThrow(() -> new EntityNotFoundException("Trabajador no encontrado con ID: " + idTrabajador));

        trabajador.setTipoDocumento(tipoDocumentoRepository.findById(request.getIdTipoDocumento()).orElse(null));
        trabajador.setTipoTrabajador(tipoTrabajadorRepository.findById(request.getIdTipoTrabajador()).orElse(null));
        trabajador.setNumeroDocumento(request.getNumeroDocumento());
        trabajador.setApellidoPaterno(request.getApellidoPaterno());
        trabajador.setApellidoMaterno(request.getApellidoMaterno());
        trabajador.setNombres(request.getNombres());
        trabajador.setEmailContacto(request.getEmailContacto());

        trabajador = trabajadorRepository.save(trabajador);

        // Actualiza o crea el contrato
        Contrato contrato = contratoRepository.findTopByTrabajadorIdTrabajadorOrderByFechaFinDesc(idTrabajador);
        if (contrato == null) {
            contrato = new Contrato();
            contrato.setTrabajador(trabajador);
        }
        contrato.setFechaInicio(request.getFechaInicioContrato());
        contrato.setFechaFin(request.getFechaFinContrato());
        contrato.setRemuneracion(request.getSueldo());
        contrato.setEstadoContrato(estadoContratoRepository.findById(request.getIdEstadoContrato()).orElse(null));
        contratoRepository.save(contrato);

        return TrabajadorResponse.fromEntity(trabajador, contrato);
    }
    public Optional<Trabajador> findByUsuarioEmail(String email) {
        return trabajadorRepository.findByUsuarioEmail(email);
    }
    
}