package dsw.backendSiderandina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.model.MetodoPago;
import dsw.backendSiderandina.model.Pago;
import dsw.backendSiderandina.repository.MetodoPagoRepository;
import dsw.backendSiderandina.repository.PagoRepository;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public Pago registrarPago(Integer idMetodoPago, Double montoPagado) {
        MetodoPago metodoPago = metodoPagoRepository.findById(idMetodoPago)
            .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        Pago pago = Pago.builder()
            .metodoPago(metodoPago)
            .montoPagado(montoPagado)
            .build();
        return pagoRepository.save(pago);
    }
}