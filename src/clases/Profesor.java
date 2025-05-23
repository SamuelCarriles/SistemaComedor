package clases;

public class Profesor extends Trabajador{

    public Profesor(String solapin, String carnetID, String nombre, String correo, String areaT, String cargoOcup) {
        super(solapin, carnetID, nombre, correo,areaT,cargoOcup);
        this.areaT = areaT;
        this.cargoOcup = cargoOcup;
        this.cAlmuerzos=0;
    }
    

}