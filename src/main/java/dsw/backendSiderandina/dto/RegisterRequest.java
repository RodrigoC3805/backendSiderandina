package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.Cliente;
import dsw.backendSiderandina.model.Usuario;
import lombok.Data;

@Data
public class RegisterRequest {
    private Usuario usuario;
    private Cliente cliente;
}
