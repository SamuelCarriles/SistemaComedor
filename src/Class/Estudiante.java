package Class;

public class Estudiante extends Usuario{
    private String grupo;

    public Estudiante(String solapin, String carnetID, String grupo) {
        super(solapin, carnetID);
        this.grupo = grupo;
    }

    public Estudiante(String solapin, String carnetID, boolean marcado, String correo, String grupo) {
        super(solapin, carnetID, marcado, correo);
        this.grupo = grupo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    
}
