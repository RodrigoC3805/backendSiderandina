package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.JustificacionAsistenciaRequest;
import dsw.backendSiderandina.dto.JustificacionResponse;
import dsw.backendSiderandina.dto.TrabajadorResponse;
import dsw.backendSiderandina.model.AsistenciaDiaria;
import dsw.backendSiderandina.model.Justificacion;
import dsw.backendSiderandina.model.MotivoJustificacion;
import dsw.backendSiderandina.repository.AsistenciaDiariaRepository;
import dsw.backendSiderandina.repository.EstadoJustificacionRepository;
import dsw.backendSiderandina.repository.JustificacionRepository;
import dsw.backendSiderandina.repository.MotivoJustificacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class JustificacionService {
    @Autowired
    private JustificacionRepository justificacionRepository;
    @Autowired
    private TrabajadorService trabajadorService;
    @Autowired
    private MotivoJustificacionRepository motivoJustificacionRepository;
    @Autowired
    private AsistenciaDiariaRepository asistenciaDiariaRepository;
    @Autowired
    private EstadoJustificacionRepository estadoJustificacionRepository;
    @Transactional
    public JustificacionResponse createJustificacion(JustificacionAsistenciaRequest request, MultipartFile doc) {
        AsistenciaDiaria asistenciaDiaria = AsistenciaDiaria
                .builder()
                .trabajador(trabajadorService.getTrabajadorEntity(request.getIdTrabajador()))
                .fecha(request.getDia_asistencia())
                .horaIngreso(request.getHora_entrada())
                .horaSalida(request.getHora_salida())
                .build();
        asistenciaDiariaRepository.save(asistenciaDiaria);
        Justificacion justificacion;
        try {
            byte[] docBytes = doc.getBytes();

            justificacion = Justificacion
                    .builder()
                    .asistenciaDiaria(asistenciaDiaria)
                    .motivoJustificacion(
                            motivoJustificacionRepository.findById(request.getIdMotivoJustificacion()).get())
                    .fechaSolicitud(LocalDate.now())
                    .estadoJustificacion(estadoJustificacionRepository.findById(3).orElseThrow(
                            () -> new RuntimeException(
                                    "EstadoJustificacion no encontrado")))
                    .build();
            justificacion.setDocSustento(docBytes);
            justificacionRepository.save(justificacion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JustificacionResponse.fromEntity(justificacion);
    }

    public List<MotivoJustificacion> getMotivoJustificacion() {
        return motivoJustificacionRepository.findAll();
    }
    public List<JustificacionResponse> getMisJustificaciones(Integer idTrabajador) {
        List<JustificacionResponse> justificaciones = justificacionRepository.findMisJustificacionesNoPDF(idTrabajador);
        return justificaciones;
    }
    public byte[] getDocSustento(Integer idJustificacion) {
        Justificacion justificacion = justificacionRepository.findById(idJustificacion)
                .orElseThrow(() -> new RuntimeException("Justificación no encontrada"));
        return justificacion.getDocSustento();
    }
}
