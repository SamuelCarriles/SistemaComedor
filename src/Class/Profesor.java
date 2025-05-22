package Class;

public class Profesor extends Trabajador{

       public Profesor(String solapin, String carnetID, String areaT, String cargoOcup) {
        super(solapin, carnetID,areaT,cargoOcup);
        this.areaT = areaT;
        this.cargoOcup = cargoOcup;
        this.cAlmuerzos=0;
    }
    public Profesor(String solapin, String carnetID, boolean marcado, String correo, String areaT, String cargoOcup) {
        super(solapin, carnetID, marcado, correo,areaT,cargoOcup);
        this.areaT = areaT;
        this.cargoOcup = cargoOcup;
        this.cAlmuerzos=0;
    }
}