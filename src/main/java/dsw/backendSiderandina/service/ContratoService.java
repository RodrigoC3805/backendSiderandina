package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.ContratoRequest;
import dsw.backendSiderandina.dto.ContratoResponse;
import dsw.backendSiderandina.model.Contrato;
import dsw.backendSiderandina.model.EstadoContrato;
import dsw.backendSiderandina.model.Trabajador; 
import dsw.backendSiderandina.repository.ContratoRepository;
import dsw.backendSiderandina.repository.EstadoContratoRepository;
import dsw.backendSiderandina.repository.TrabajadorRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ContratoService {
    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private EstadoContratoRepository estadoContratoRepository; 

    public List<ContratoResponse> listContratos() {
        return ContratoResponse.fromEntities(contratoRepository.findAll());
    }

    public List<ContratoResponse> listContratosByTrabajador(Integer idTrabajador) {
        Trabajador trabajador = trabajadorRepository.findById(idTrabajador)
            .orElseThrow(() -> new EntityNotFoundException("Trabajador no encontrado con ID: " + idTrabajador));
        return ContratoResponse.fromEntities(contratoRepository.findByTrabajadorIdTrabajador(idTrabajador));
    }


    @Transactional
    public ContratoResponse createContrato(ContratoRequest request) {
        Trabajador trabajador = trabajadorRepository.findById(request.getIdTrabajador())
                .orElseThrow(() -> new EntityNotFoundException("Trabajador no encontrado con ID: " + request.getIdTrabajador()));

        EstadoContrato estadoContrato = estadoContratoRepository.findById(request.getIdEstadoContrato())
                .orElseThrow(() -> new EntityNotFoundException("Estado de Contrato no encontrado con ID: " + request.getIdEstadoContrato()));

        Contrato contrato = Contrato.builder()
                .trabajador(trabajador)
                .estadoContrato(estadoContrato)
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .remuneracion(request.getRemuneracion())
                .build();
        
        Contrato savedContrato = contratoRepository.save(contrato);
        return ContratoResponse.fromEntity(savedContrato);
    }

    public ContratoResponse getContratoById(Integer idContrato) {
        Contrato contrato = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new EntityNotFoundException("Contrato no encontrado con ID: " + idContrato));
        return ContratoResponse.fromEntity(contrato);
    }

    @Transactional
    public ContratoResponse updateContrato(Integer idContrato, ContratoRequest request) {
        Contrato contratoExistente = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new EntityNotFoundException("Contrato no encontrado con ID: " + idContrato));

        Trabajador trabajador = trabajadorRepository.findById(request.getIdTrabajador())
                .orElseThrow(() -> new EntityNotFoundException("Trabajador no encontrado con ID: " + request.getIdTrabajador()));

        EstadoContrato estadoContrato = estadoContratoRepository.findById(request.getIdEstadoContrato())
                .orElseThrow(() -> new EntityNotFoundException("Estado de Contrato no encontrado con ID: " + request.getIdEstadoContrato()));

        contratoExistente.setTrabajador(trabajador);
        contratoExistente.setEstadoContrato(estadoContrato);
        contratoExistente.setFechaInicio(request.getFechaInicio());
        contratoExistente.setFechaFin(request.getFechaFin());
        contratoExistente.setRemuneracion(request.getRemuneracion());
        
        Contrato updatedContrato = contratoRepository.save(contratoExistente);
        return ContratoResponse.fromEntity(updatedContrato);
    }

    @Transactional
    public void deleteContrato(Integer idContrato) {
        if (!contratoRepository.existsById(idContrato)) {
            throw new EntityNotFoundException("Contrato no encontrado con ID: " + idContrato);
        }
        contratoRepository.deleteById(idContrato);
    }
}