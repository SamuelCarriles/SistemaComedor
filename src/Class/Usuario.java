package Class;

public class Usuario {
    private String solapin;
    private String carnetID;
    protected boolean marcado;
    protected String correo;
   // protected String password;
    protected String nombre;

    public Usuario(String solapin, String carnetID) {
        this.solapin = solapin;
        this.carnetID = carnetID;
        this.marcado = false;

    }

    public Usuario(String solapin, String carnetID, String nombre,String correo) {
        this.solapin = solapin;
        this.carnetID = carnetID;
        this.marcado = false;
        this.correo = correo;
        this.nombre=nombre;
    }

    public String getSolapin() {
        return solapin;
    }

    public String getCarnetID() {
        return carnetID;
    }

}
