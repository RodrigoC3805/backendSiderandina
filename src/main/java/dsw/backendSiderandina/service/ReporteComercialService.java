package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.ReporteProveedorComprasDTO;
import dsw.backendSiderandina.repository.PedidoCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteComercialService {

    @Autowired
    private PedidoCompraRepository pedidoCompraRepository;

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
}