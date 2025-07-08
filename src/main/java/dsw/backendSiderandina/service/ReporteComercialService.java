package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.ProductoMasVendidoDTO;
import dsw.backendSiderandina.dto.ReportePedidosProveedorDTO;
import dsw.backendSiderandina.dto.ReporteProveedorComprasDTO;
import dsw.backendSiderandina.repository.DetalleCotizacionRepository;
import dsw.backendSiderandina.repository.PedidoCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteComercialService {

    @Autowired
    private PedidoCompraRepository pedidoCompraRepository;

    @Autowired
    private DetalleCotizacionRepository detalleCotizacionRepository;

    public List<ReporteProveedorComprasDTO> obtenerReporteComprasPorProveedor() {
        List<Object[]> raw = pedidoCompraRepository.reporteComprasPorProveedorRaw();
        return raw.stream().map(obj -> new ReporteProveedorComprasDTO(
                (String) obj[0],
                (String) obj[1],
                ((Number) obj[2]).longValue(),
                ((Number) obj[3]).doubleValue(),
                ((Number) obj[4]).doubleValue()
        )).collect(Collectors.toList());
    }

    public List<ReportePedidosProveedorDTO> obtenerCantidadPedidosPorProveedor(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Object[]> resultados = pedidoCompraRepository.cantidadPedidosPorProveedor(fechaInicio, fechaFin);
        return resultados.stream()
            .map(obj -> new ReportePedidosProveedorDTO(
                (String) obj[0],
                ((Number) obj[1]).longValue()
            )).toList();
    }

    public List<ProductoMasVendidoDTO> obtenerProductosMasVendidos() {
        List<Object[]> resultados = detalleCotizacionRepository.productosMasVendidos();
        return resultados.stream()
            .map(obj -> new ProductoMasVendidoDTO(
                ((Number) obj[0]).intValue(),
                (String) obj[1],
                ((Number) obj[2]).intValue(),
                ((Number) obj[3]).doubleValue()
            )).toList();
    }

}