package clases;

public class Familiar extends Usuario{
    private int apartamento;


    public Familiar(String solapin, String carnetID, String nombre, String correo, int apartamento) {
        super(solapin, carnetID, nombre, correo);
        this.apartamento=apartamento;
    }


    public int getNumApartamento() {
        return apartamento;
    }


    public void setNumApartamento(int apartamento) {
        this.apartamento = apartamento;
    }
    
}
