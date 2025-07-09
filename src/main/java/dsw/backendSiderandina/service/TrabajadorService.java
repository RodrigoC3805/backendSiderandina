package dsw.backendSiderandina.service;

import java.time.LocalDate;
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
import dsw.backendSiderandina.model.Planilla;
import dsw.backendSiderandina.model.DetallePlanilla;
import dsw.backendSiderandina.repository.EstadoContratoRepository;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import dsw.backendSiderandina.repository.TipoDocumentoRepository;
import dsw.backendSiderandina.repository.TipoTrabajadorRepository;
import dsw.backendSiderandina.repository.ContratoRepository;
import dsw.backendSiderandina.repository.PlanillaRepository;
import dsw.backendSiderandina.repository.DetallePlanillaRepository;
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

    @Autowired
    PlanillaRepository planillaRepository;
    @Autowired
    DetallePlanillaRepository detallePlanillaRepository;

    public TrabajadorResponse createTrabajador(TrabajadorRequest trabajadorRequest) {
        Trabajador trabajador = Trabajador.builder()
                .tipoDocumento(tipoDocumentoRepository.findById(trabajadorRequest.getIdTipoDocumento()).orElse(null))
                .numeroDocumento(trabajadorRequest.getNumeroDocumento())
                .apellidoPaterno(trabajadorRequest.getApellidoPaterno())
                .apellidoMaterno(trabajadorRequest.getApellidoMaterno())
                .nombres(trabajadorRequest.getNombres())
                .emailContacto(trabajadorRequest.getEmailContacto())
                .build();

        trabajador = trabajadorRepository.save(trabajador);

        Contrato contrato = null;
        // Crear contrato si se envían los datos
        if (trabajadorRequest.getFechaInicioContrato() != null && trabajadorRequest.getSueldo() != null) {
            EstadoContrato estadoContrato = estadoContratoRepository.findById(trabajadorRequest.getIdEstadoContrato()).orElse(null);
            contrato = Contrato.builder()
                .trabajador(trabajador)
                .estadoContrato(estadoContrato)
                .fechaInicio(trabajadorRequest.getFechaInicioContrato())
                .fechaFin(trabajadorRequest.getFechaFinContrato())
                .remuneracion(trabajadorRequest.getSueldo())
                .build();
            contratoRepository.save(contrato);
        }

        // AGREGADO: asociar a planilla existente del mes/año actual y actualizar totalSueldos
        if (contrato != null) {
            LocalDate hoy = LocalDate.now();
            int mes = hoy.getMonthValue();
            int anio = hoy.getYear();
            List<Planilla> planillas = planillaRepository.findByMesAndAnio(mes, anio);
            if (!planillas.isEmpty()) {
                Planilla planilla = planillas.get(0);
                DetallePlanilla detalle = new DetallePlanilla();
                detalle.setPlanilla(planilla);
                detalle.setTrabajador(trabajador);
                detalle.setSueldoBase(contrato.getRemuneracion());
                detalle.setBonos(0.0);
                detalle.setDescuentos(0.0);
                detalle.setSueldoNeto(contrato.getRemuneracion());
                detallePlanillaRepository.save(detalle);

                // Recalcular total de sueldos
                List<DetallePlanilla> detalles = detallePlanillaRepository.findByPlanilla_IdPlanilla(planilla.getIdPlanilla());
                double totalSueldos = detalles.stream()
                    .mapToDouble(d -> d.getSueldoNeto() != null ? d.getSueldoNeto() : 0.0)
                    .sum();
                planilla.setTotalSueldos(totalSueldos);
                planillaRepository.save(planilla);
            }
        }

        return TrabajadorResponse.fromEntity(trabajador);
    }

    // ...resto del código sin cambios...
    public TrabajadorResponse getTrabajador(Integer idTrabajador) {
        Trabajador trabajador = trabajadorRepository.findById(idTrabajador)
            .orElseThrow(() -> new EntityNotFoundException("Trabajador no encontrado con ID: " + idTrabajador));
        Contrato contrato = contratoRepository.findTopByTrabajadorIdTrabajadorAndEstadoContratoDescripcionOrderByFechaFinDesc(
            trabajador.getIdTrabajador(), "Activo"
        );
        if (contrato == null) {
            contrato = contratoRepository.findTopByTrabajadorIdTrabajadorOrderByFechaFinDesc(trabajador.getIdTrabajador());
        }
        return TrabajadorResponse.fromEntity(trabajador, contrato);
    }
    
    public void eliminarTrabajador(Integer idTrabajador) {
        contratoRepository.deleteAll(contratoRepository.findByTrabajadorIdTrabajador(idTrabajador));
        trabajadorRepository.deleteById(idTrabajador);
    }

    public List<TrabajadorListItem> listarTrabajadores() {
        return trabajadorRepository.findAll().stream()
            .map(trabajador -> {
                Contrato contrato = contratoRepository.findTopByTrabajadorIdTrabajadorAndEstadoContratoDescripcionOrderByFechaFinDesc(
                    trabajador.getIdTrabajador(), "Activo"
                );
                if (contrato == null) {
                    contrato = contratoRepository.findTopByTrabajadorIdTrabajadorOrderByFechaFinDesc(trabajador.getIdTrabajador());
                }
                Double sueldo = contrato != null && contrato.getRemuneracion() != null ? contrato.getRemuneracion() : 0.0;
                String moneda = "Soles";
                String estadoContrato = contrato != null && contrato.getEstadoContrato() != null
                        ? contrato.getEstadoContrato().getDescripcion()
                        : "";

                return new TrabajadorListItem(
                    trabajador.getIdTrabajador(),
                    trabajador.getNombres() + " " + trabajador.getApellidoPaterno() + " " + trabajador.getApellidoMaterno(),
                    trabajador.getTipoDocumento() != null ? trabajador.getTipoDocumento().getDescripcion() : "",
                    trabajador.getNumeroDocumento(),
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
        trabajador.setNumeroDocumento(request.getNumeroDocumento());
        trabajador.setApellidoPaterno(request.getApellidoPaterno());
        trabajador.setApellidoMaterno(request.getApellidoMaterno());
        trabajador.setNombres(request.getNombres());
        trabajador.setEmailContacto(request.getEmailContacto());

        trabajador = trabajadorRepository.save(trabajador);

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
    public Trabajador getTrabajadorEntity(Integer id) {
        return trabajadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
    }
}