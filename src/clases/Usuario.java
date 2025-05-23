package clases;
import java.io.Serializable;
public class Usuario implements Serializable{
    private String solapin;
    private String carnetID;
    protected boolean marcado;
    protected String correo;
    protected String rol;
    protected String password;
    protected String nombre;

    public Usuario(String solapin, String carnetID, String nombre,String correo) {
        this.solapin = solapin;
        this.carnetID = carnetID;
        this.marcado = false;
        this.correo = correo;
        this.nombre=nombre;
        this.rol="cliente";
        this.password = "";
    }

    public String getSolapin() {
        return solapin;
    }

    public String getCarnetID() {
        return carnetID;
    }

    public void setSolapin(String solapin) {
        this.solapin = solapin;
    }

    public void setCarnetID(String carnetID) {
        this.carnetID = carnetID;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
