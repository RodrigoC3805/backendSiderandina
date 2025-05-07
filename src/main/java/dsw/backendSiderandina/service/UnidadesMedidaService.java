package dsw.backendSiderandina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.model.UnidadesMedida;
import dsw.backendSiderandina.repository.UnidadesMedidaRepository;

@Service
public class UnidadesMedidaService {
    @Autowired
    UnidadesMedidaRepository unidadesMedidaRepository;

    public List<UnidadesMedida> getUnidadesMedida() {
        return unidadesMedidaRepository.findAll();
    }
}
