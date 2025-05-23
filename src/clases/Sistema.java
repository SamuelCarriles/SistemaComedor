package clases;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Sistema implements Serializable{
    private static List<Usuario> usuarios = new ArrayList<>();

    private Sistema() {throw new AssertionError("Esta clase no debe ser instanciada");}

    public static void init() throws IOException, ClassNotFoundException {cargarDatos();}

    public static boolean addUsuario(Usuario usuario) {
        if (usuarios.size() < 1000) {
            usuarios.add(usuario);
            return true;
        }
        return false;
    }

    public static boolean deleteUsuario(String solapin) {
        for (Usuario usuario : usuarios) {
            if (usuario.getSolapin().equals(solapin)) {
                usuarios.remove(usuario);
                return true;
            }

        }
        return false;
    }

    public static boolean marcarUsuario(String solapin){
        for(Usuario usuario: usuarios) {
            if(usuario.getSolapin().equals(solapin)) {
                usuario.marcado = true;
                return true;
            }
        }
        return false;
    }

    public static boolean desmarcarUsuarios(){
        usuarios.forEach(usuario -> {
            usuario.marcado=false;
        });
        for(Usuario u:usuarios){
            if(u.marcado==true) return false;
        }
        return true;
    }

    public static void exportarDatos() throws IOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.bin"));
            oos.writeObject(usuarios);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar el archivo: " + e.getMessage(),
                    "Error",                        // Título del diálogo
                    JOptionPane.ERROR_MESSAGE       // Tipo de mensaje (ERROR_MESSAGE, WARNING_MESSAGE, etc.)
            );
        }
    }

    public static void cargarDatos() throws IOException,ClassNotFoundException{
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.bin"));
            usuarios=(List<Usuario>) ois.readObject();
        } catch (IOException e) {
            // Diálogo de error con icono de alerta roja
            JOptionPane.showMessageDialog(
                    null,                           // Componente padre (null = centro de la pantalla)
                    "Error al leer el archivo: " + e.getMessage(),
                    "Error",                        // Título del diálogo
                    JOptionPane.ERROR_MESSAGE       // Tipo de mensaje (ERROR_MESSAGE, WARNING_MESSAGE, etc.)
            );
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Clase no encontrada: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void mostrarInfo() {
        usuarios.forEach(u-> System.out.println(u.nombre));
    }
}
