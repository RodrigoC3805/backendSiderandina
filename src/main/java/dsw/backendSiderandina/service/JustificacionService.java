package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.JustificacionAsistenciaRequest;
import dsw.backendSiderandina.dto.JustificacionResponse;
import dsw.backendSiderandina.dto.TrabajadorResponse;
import dsw.backendSiderandina.model.AsistenciaDiaria;
import dsw.backendSiderandina.model.Justificacion;
import dsw.backendSiderandina.repository.AsistenciaDiariaRepository;
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
    AsistenciaDiariaRepository asistenciaDiariaRepository;
    public List<Justificacion> findJustificacionesByTrabajadorId(Integer idTrabajador){
        return justificacionRepository.findByAsistenciaDiariaTrabajadorIdTrabajador(idTrabajador);
    }
    @Transactional
    public JustificacionResponse createJustificacion (JustificacionAsistenciaRequest request, MultipartFile doc) {
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
                    .build();
            justificacion.setDocSustento(docBytes);
            justificacionRepository.save(justificacion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
 
        return JustificacionResponse.fromEntity(justificacion);
    }
}
