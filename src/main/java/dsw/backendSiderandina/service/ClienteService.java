package dsw.backendSiderandina.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsw.backendSiderandina.model.Cliente;
import dsw.backendSiderandina.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Cliente> findByUsuarioEmail(String email) {
        return clienteRepository.findByUsuarioEmail(email);
    }

    public Optional<Cliente> findByUsuarioIdUsuario(Integer idUsuario) {
        return clienteRepository.findByUsuarioIdUsuario(idUsuario);
    }

}
