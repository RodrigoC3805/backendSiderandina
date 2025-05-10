package dsw.backendSiderandina.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.TrabajadorRequest;
import dsw.backendSiderandina.dto.TrabajadorResponse;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import dsw.backendSiderandina.repository.TipoDocumentoRepository;
import dsw.backendSiderandina.repository.TipoTrabajadorRepository;
import dsw.backendSiderandina.dto.EmpleadoListItem;
import dsw.backendSiderandina.model.Contrato;
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

    public List<EmpleadoListItem> listarEmpleados() {
        List<Trabajador> trabajadores = trabajadorRepository.findAll();
        return trabajadores.stream().map(trabajador -> {
            // Obtener contrato activo (puedes ajustar la lógica según tu modelo)
            Contrato contratoActivo = contratoRepository.findFirstByTrabajadorIdTrabajadorAndEstadoContratoDescripcion(
                trabajador.getIdTrabajador(), "Activo"
            );
            String cargo = trabajador.getTipoTrabajador() != null ? trabajador.getTipoTrabajador().getDescripcion() : "";
            Double sueldo = contratoActivo != null ? contratoActivo.getRemuneracion().doubleValue() : null;
            String estadoContrato = contratoActivo != null ? "Activo" : "Inactivo";
            return new EmpleadoListItem(
                trabajador.getIdTrabajador(),
                trabajador.getNombres() + " " + trabajador.getApellidoPaterno() + " " + trabajador.getApellidoMaterno(),
                trabajador.getTipoDocumento() != null ? trabajador.getTipoDocumento().getDescripcion() : "",
                trabajador.getNumeroDocumento(),
                cargo,
                sueldo,
                "Soles",
                estadoContrato
            );
        }).collect(Collectors.toList());
    }
}