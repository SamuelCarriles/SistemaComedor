package clases;

public class Trabajador extends Usuario{
    protected String areaT;
    protected String cargoOcup;
    protected int cAlmuerzos;

    public Trabajador(String solapin, String carnetID, String nombre,String correo, String areaT, String cargoOcup) {
        super(solapin,carnetID, nombre, correo);
        this.areaT = areaT;
        this.cargoOcup = cargoOcup;
        this.cAlmuerzos=0;
    }

    public String getAreaT() {
        return areaT;
    }

    public void setAreaT(String areaT) {
        this.areaT = areaT;
    }

    public String getCargoOcup() {
        return cargoOcup;
    }

    public void setCargoOcup(String cargoOcup) {
        this.cargoOcup = cargoOcup;
    }

    public int getcAlmuerzos() {
        return cAlmuerzos;
    }

    public void setcAlmuerzos(int cAlmuerzos) {
        this.cAlmuerzos = cAlmuerzos;
    }
   

    
    
}
