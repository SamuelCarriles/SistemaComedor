package clases;

public class UsuarioAdmin extends Usuario{

    public UsuarioAdmin(String solapin, String carnetID, String nombre, String correo) {
        super(solapin, carnetID, nombre, correo);
        this.rol="administrador";
    }
}
