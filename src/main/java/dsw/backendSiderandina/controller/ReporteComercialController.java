package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.ProductoMasVendidoDTO;
import dsw.backendSiderandina.dto.ReportePedidosProveedorDTO;
import dsw.backendSiderandina.dto.ReporteProveedorComprasDTO;
import dsw.backendSiderandina.service.ReporteComercialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/pedidos-por-proveedor")
    public List<ReportePedidosProveedorDTO> getPedidosPorProveedor(
        @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return reporteComercialService.obtenerCantidadPedidosPorProveedor(fechaInicio, fechaFin);
    }

    @GetMapping("/productos-mas-vendidos")
    public List<ProductoMasVendidoDTO> getProductosMasVendidos() {
        return reporteComercialService.obtenerProductosMasVendidos();
    }

}