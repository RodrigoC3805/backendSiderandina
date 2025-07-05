package dsw.backendSiderandina.service;

import dsw.backendSiderandina.model.Despacho;
import dsw.backendSiderandina.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DespachoService {
    @Autowired
    private DespachoRepository despachoRepository;

    public Despacho programarDespacho(Integer idPedidoVenta, Timestamp fechaProgramada) {
        Despacho despacho = new Despacho();
        despacho.setIdPedidoVenta(idPedidoVenta);
        despacho.setFechaProgramada(fechaProgramada);
        despacho.setEstado("Pendiente");
        return despachoRepository.save(despacho);
    }

    public List<Despacho> listarTodos() {
        return despachoRepository.findAll();
    }

    public Despacho actualizarEstado(Integer id, String estado) {
        Despacho despacho = despachoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado"));
        despacho.setEstado(estado);
        return despachoRepository.save(despacho);
    }
}