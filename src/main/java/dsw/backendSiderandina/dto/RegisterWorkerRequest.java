package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.model.Usuario;
import lombok.Data;

@Data
public class RegisterWorkerRequest {
    private Trabajador trabajador;
    private Usuario usuario;
}
