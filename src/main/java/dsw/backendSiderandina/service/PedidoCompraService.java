package dsw.backendSiderandina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.dto.PedidoCompraResponse;
import dsw.backendSiderandina.repository.EstadoPedidoRepository;
import dsw.backendSiderandina.repository.PedidoCompraRepository;
import dsw.backendSiderandina.repository.ProveedorRepository;

@Service
public class PedidoCompraService {
    @Autowired
    PedidoCompraRepository pedidoCompraRepository;
    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    EstadoPedidoRepository estadoPedidoRepository;

    public List<PedidoCompraResponse> listPedidosCompra(){
        return PedidoCompraResponse.fromEntities(pedidoCompraRepository.findAll());
    }
}
