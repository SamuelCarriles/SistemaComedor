package clases;

public class UsuarioRoot {
    private String nombre;
    private String rol;
    private String password;

    public UsuarioRoot(String nombre, String rol, String password) {
        this.nombre = nombre;
        this.rol = rol;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
