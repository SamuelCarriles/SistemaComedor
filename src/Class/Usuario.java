package Class;

public class Usuario {
    private String solapin;
    private String carnetID;
    protected boolean marcado;
    protected String correo;

    public Usuario(String solapin, String carnetID) {
        this.solapin = solapin;
        this.carnetID = carnetID;
    }

    public Usuario(String solapin, String carnetID, boolean marcado, String correo) {
        this.solapin = solapin;
        this.carnetID = carnetID;
        this.marcado = marcado;
        this.correo = correo;
    }

    public String getSolapin() {
        return solapin;
    }

    public String getCarnetID() {
        return carnetID;
    }

}
