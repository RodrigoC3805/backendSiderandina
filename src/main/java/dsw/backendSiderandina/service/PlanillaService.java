package dsw.backendSiderandina.service;

import dsw.backendSiderandina.model.Planilla;
import dsw.backendSiderandina.dto.PlanillaResumenResponse;
import dsw.backendSiderandina.model.Contrato;
import dsw.backendSiderandina.model.DetallePlanilla;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.repository.PlanillaRepository;
import dsw.backendSiderandina.repository.DetallePlanillaRepository;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import dsw.backendSiderandina.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dsw.backendSiderandina.dto.PlanillaResumenResponse;
import dsw.backendSiderandina.dto.DetallePlanillaResponse;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
public class PlanillaService {
    @Autowired
    private PlanillaRepository planillaRepository;
    @Autowired
    private DetallePlanillaRepository detallePlanillaRepository;
    @Autowired
    private TrabajadorRepository trabajadorRepository;
    @Autowired
    private ContratoRepository contratoRepository; // Asegúrate de tener este autowired

    public Planilla generarPlanillaMensual(Integer mes, Integer anio) {
        Planilla planilla = new Planilla();
        planilla.setMes(mes);
        planilla.setAnio(anio);
        planilla.setFechaGeneracion(new Timestamp(System.currentTimeMillis()));

        // Fecha de referencia: primer día del mes de la planilla
        LocalDate fechaReferencia = LocalDate.of(anio, mes, 1);
        Date fechaSql = Date.valueOf(fechaReferencia);

        // Solo contratos vigentes en la fecha de la planilla
        List<Contrato> contratosVigentes = contratoRepository.findContratosVigentes(fechaSql);

        double totalSueldos = 0.0;
        planilla = planillaRepository.save(planilla);

        for (Contrato contrato : contratosVigentes) {
            Trabajador t = contrato.getTrabajador();
            if (t == null) continue; // Seguridad extra

            double sueldoBase = contrato.getRemuneracion() != null ? contrato.getRemuneracion() : 0.0;
            double bonos = 0.0; // Aquí puedes agregar lógica de bonos si aplica
            double descuentos = 0.0; // Aquí puedes agregar lógica de descuentos si aplica
            double sueldoNeto = sueldoBase + bonos - descuentos;

            DetallePlanilla detalle = new DetallePlanilla();
            detalle.setPlanilla(planilla);
            detalle.setTrabajador(t);
            detalle.setSueldoBase(sueldoBase);
            detalle.setBonos(bonos);
            detalle.setDescuentos(descuentos);
            detalle.setSueldoNeto(sueldoNeto);

            detallePlanillaRepository.save(detalle);
            totalSueldos += sueldoNeto;
        }

        planilla.setTotalSueldos(totalSueldos);
        return planillaRepository.save(planilla);
    }
    public List<Planilla> listarPlanillas(Integer anio) {
        if (anio != null) {
            return planillaRepository.findByAnio(anio);
        }
        return planillaRepository.findAll();
    }

    public List<DetallePlanillaResponse> listarDetallePlanilla(Integer idPlanilla) {
        List<DetallePlanilla> detalles = detallePlanillaRepository.findByPlanilla_IdPlanilla(idPlanilla);
        List<DetallePlanillaResponse> respuesta = new ArrayList<>();
        for (DetallePlanilla d : detalles) {
            DetallePlanillaResponse dto = new DetallePlanillaResponse();
            dto.setIdDetallePlanilla(d.getIdDetallePlanilla());
            dto.setIdTrabajador(d.getTrabajador() != null ? d.getTrabajador().getIdTrabajador() : null);
            dto.setNombres(d.getTrabajador() != null ? d.getTrabajador().getNombres() : "");
            dto.setApellidoPaterno(d.getTrabajador() != null ? d.getTrabajador().getApellidoPaterno() : "");
            dto.setApellidoMaterno(d.getTrabajador() != null ? d.getTrabajador().getApellidoMaterno() : "");
            dto.setSueldoBase(d.getSueldoBase());
            dto.setBonos(d.getBonos());
            dto.setDescuentos(d.getDescuentos());
            dto.setSueldoNeto(d.getSueldoNeto());
            respuesta.add(dto);
        }
        return respuesta;
    }
    
    public List<PlanillaResumenResponse> listarPlanillasResumen(Integer anio) {
        if (anio != null) {
            return planillaRepository.findPlanillasConCantidadTrabajadoresByAnio(anio);
        }
        return planillaRepository.findPlanillasConCantidadTrabajadores();
    }


}