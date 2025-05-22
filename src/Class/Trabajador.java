package Class;

public class Trabajador extends Usuario{
    protected String areaT;
    protected String cargoOcup;
    protected int cAlmuerzos;
    public Trabajador(String solapin, String carnetID, String areaT, String cargoOcup) {
        super(solapin, carnetID);
        this.areaT = areaT;
        this.cargoOcup = cargoOcup;
        this.cAlmuerzos=0;
    }
    public Trabajador(String solapin, String carnetID, boolean marcado, String correo, String areaT, String cargoOcup) {
        super(solapin, carnetID, marcado, correo);
        this.areaT = areaT;
        this.cargoOcup = cargoOcup;
        this.cAlmuerzos=0;
    }
   

    
    
}
