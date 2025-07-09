package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.model.Planilla;
import dsw.backendSiderandina.dto.DetallePlanillaResponse;
import dsw.backendSiderandina.dto.PlanillaResumenResponse;
import dsw.backendSiderandina.model.DetallePlanilla;
import dsw.backendSiderandina.service.PlanillaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dsw.backendSiderandina.dto.DetallePlanillaResponse;

import java.util.List;

@RestController
@RequestMapping("/api/rrhh/planilla")
public class PlanillaController {
    @Autowired
    private PlanillaService planillaService;

    // Generar planilla mensual
    @PostMapping("/generar")
    public Planilla generarPlanilla(@RequestBody dsw.backendSiderandina.dto.PlanillaRequest request) {
        return planillaService.generarPlanillaMensual(request.getMes(), request.getAnio());
    }

    // Ver detalle de una planilla
    @GetMapping("/{idPlanilla}/detalle")
    public List<DetallePlanillaResponse> listarDetallePlanilla(@PathVariable Integer idPlanilla) {
        return planillaService.listarDetallePlanilla(idPlanilla);
    }
    // Ver históricos de planillas 
    @GetMapping
    public List<PlanillaResumenResponse> listarPlanillasResumen(@RequestParam(required = false) Integer anio) {
        return planillaService.listarPlanillasResumen(anio);
    }
}