package clases;

public class Familiar extends Usuario{
    private String apartamento;


    public Familiar(String solapin, String carnetID, String nombre, String correo, String apartamento) {
        super(solapin, carnetID, nombre, correo);
        this.apartamento=apartamento;
    }


    public String getApartamento() {
        return apartamento;
    }


    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }
    
}
