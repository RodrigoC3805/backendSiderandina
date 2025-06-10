package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.AsistenciaRequest;
import dsw.backendSiderandina.model.AsistenciaDiaria;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.repository.AsistenciaDiariaRepository;
import dsw.backendSiderandina.repository.TrabajadorRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciaDiariaRepository asistenciaRepo;
    @Autowired
    private TrabajadorRepository trabajadorRepo;

    public AsistenciaDiaria registrarAsistenciaIngreso(AsistenciaRequest req) {
        Trabajador trabajador = trabajadorRepo.findByNumeroDocumento(req.getNumeroDocumento())
            .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        AsistenciaDiaria asistencia = AsistenciaDiaria.builder()
            .trabajador(trabajador)
            .fecha(req.getFecha())
            .horaIngreso(req.getHoraIngreso())
            .build();
        return asistenciaRepo.save(asistencia);
    }

    public AsistenciaDiaria registrarAsistenciaSalida(AsistenciaRequest req) {
        // Busca la asistencia del día para ese trabajador y actualiza la hora de salida
        Trabajador trabajador = trabajadorRepo.findByNumeroDocumento(req.getNumeroDocumento())
            .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        AsistenciaDiaria asistencia = asistenciaRepo.findByTrabajadorAndFecha(trabajador, req.getFecha())
            .orElseThrow(() -> new RuntimeException("No se encontró registro de ingreso"));
        asistencia.setHoraSalida(req.getHoraSalida());
        return asistenciaRepo.save(asistencia);
    }

    public List<AsistenciaDiaria> listarAsistencias() {
    return asistenciaRepo.findAll();
    }

    public List<AsistenciaDiaria> listarAsistenciasPorDocumento(String numeroDocumento) {
        Trabajador trabajador = trabajadorRepo.findByNumeroDocumento(numeroDocumento)
            .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        return asistenciaRepo.findByTrabajador(trabajador);
}
   
}