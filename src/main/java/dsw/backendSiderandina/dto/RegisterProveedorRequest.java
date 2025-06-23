package dsw.backendSiderandina.dto;

import dsw.backendSiderandina.model.Proveedor;
import dsw.backendSiderandina.model.Usuario;
import lombok.Data;

@Data
public class RegisterProveedorRequest {
    Usuario usuario;
    Proveedor proveedor;
}
