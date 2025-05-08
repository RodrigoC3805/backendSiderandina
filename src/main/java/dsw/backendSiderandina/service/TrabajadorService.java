package dsw.backendSiderandina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.TrabajadorRequest;
import dsw.backendSiderandina.dto.TrabajadorResponse;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import dsw.backendSiderandina.repository.TipoDocumentoRepository;
import dsw.backendSiderandina.repository.TipoTrabajadorRepository;

@Service
public class TrabajadorService {
    @Autowired
    TrabajadorRepository trabajadorRepository;

    @Autowired
    TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    TipoTrabajadorRepository tipoTrabajadorRepository;

    public List<TrabajadorResponse> listTrabajadores() {
        return TrabajadorResponse.fromEntities(trabajadorRepository.findAll());
    }

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
}
