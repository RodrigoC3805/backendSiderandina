package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.ReporteProveedorComprasDTO;
import dsw.backendSiderandina.service.ReporteComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reportes/comercial")
public class ReporteComercialController {

    @Autowired
    private ReporteComercialService reporteComercialService;

    @GetMapping("/proveedores-compras")
    public List<ReporteProveedorComprasDTO> getReporteComprasPorProveedor() {
        return reporteComercialService.obtenerReporteComprasPorProveedor();
    }
}