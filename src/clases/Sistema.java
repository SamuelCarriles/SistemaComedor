package clases;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public final class Sistema implements Serializable {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static Usuario operador=null;
    private static UsuarioRoot root = new UsuarioRoot("rooter","manager","rooter");
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private Sistema() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }

    public static void iniciar() throws IOException, ClassNotFoundException {
        File archivo = new File("database.bin");
        if (archivo.exists()) {
            cargarDatos();
        }
        scheduler.scheduleAtFixedRate(() -> {
            CompletableFuture.runAsync(() -> {
                try {
                    exportarDatos();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }, 0, 1, TimeUnit.MINUTES);

        //Para detener el autoguardado se pone
        //scheduler.shutdown();
    }

    public static void detener() throws IOException {scheduler.shutdown();
    exportarDatos();}

    public static boolean acceder(String nombre, String pass){
        if(root.getNombre().equals(nombre)){
            if(root.getPassword().equals(pass)){
                JOptionPane.showMessageDialog(
                        null,
                        "¡Bienvenido al sistema "+root.getNombre()+"!",
                        "Bienvenida al Sistema",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Contraseña inválida. Por favor, vuelva a intentarlo...",
                        "Contraseña Inválida",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        for(Usuario u : usuarios){
            if((u.getNombre().equals(nombre))&&(u.getPassword().equals(pass))){
                operador = u;
                JOptionPane.showMessageDialog(
                        null,
                        "¡Bienvenido al sistema "+operador.getNombre()+"!",
                        "Bienvenida al Sistema",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else if((u.getNombre().equals(nombre))&&(!u.getPassword().equals(pass))){
                JOptionPane.showMessageDialog(
                        null,
                        "Contraseña inválida. Por favor, vuelva a intentarlo...",
                        "Contraseña Inválida",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        JOptionPane.showMessageDialog(
                null,
                "El usuario "+nombre+" no existe.",
                "Usuario No válido",
                JOptionPane.ERROR_MESSAGE);
    return false;
    }

    private static boolean isAdmin(Usuario user){
        return (user.getRol().equals("administrador"));
    }

    private static boolean isEmpleado(Usuario user){
        return (user.getRol().equals("empleado"));
    }

    public static boolean addUsuario(Usuario operador, Usuario usuario) {
        for(Usuario u : usuarios){
            if(usuario.getSolapin().equals(u.getSolapin())||usuario.getCarnetID().equals(u.getCarnetID())||usuario.getNombre().equals(u.getNombre())) return false;
        }
        if ((usuarios.size() < 1000)) {
            usuarios.add(usuario);
            return true;
        } else if(usuarios.size()==1000) JOptionPane.showMessageDialog(
                null,
                "No se pueden añadir más usuarios. El sistema solo soporta mil usuarios.",
                "Operación Fallida",
                JOptionPane.WARNING_MESSAGE
        );
        return false;
    }

    public static boolean deleteUsuario(Usuario operador, String solapin) {
            for (Usuario usuario : usuarios) {
                if (usuario.getSolapin().equals(solapin)) {
                    usuarios.remove(usuario);
                    return true;
                }

            }
        return false;
    }

    public static boolean marcarUsuario(Usuario operador,String solapin) {
        if(!operador.getRol().equals("cliente")){
            for (Usuario usuario : usuarios) {
                if (usuario.getSolapin().equals(solapin)) {
                    usuario.marcado = true;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean desmarcarUsuarios(Usuario operador) {
        if(!operador.getRol().equals("cliente")){
            usuarios.forEach(usuario -> {
                usuario.marcado = false;
            });
            for (Usuario u : usuarios) {
                if (u.marcado == true)
                    return false;
            }
            return true;
        }
        return false;
    }

    public static void exportarDatos() throws IOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.bin"));
            oos.writeObject(usuarios);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al guardar el archivo: " + e.getMessage(),
                    "Error", // Título del diálogo
                    JOptionPane.ERROR_MESSAGE // Tipo de mensaje (ERROR_MESSAGE, WARNING_MESSAGE, etc.)
            );
        }
    }

    public static void cargarDatos() throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.bin"));
            usuarios = (List<Usuario>) ois.readObject();
        } catch (IOException e) {
            // Diálogo de error con icono de alerta roja
            JOptionPane.showMessageDialog(
                    null, // Componente padre (null = centro de la pantalla)
                    "Error al leer el archivo: " + e.getMessage(),
                    "Error", // Título del diálogo
                    JOptionPane.ERROR_MESSAGE // Tipo de mensaje (ERROR_MESSAGE, WARNING_MESSAGE, etc.)
            );
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Clase no encontrada: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void listaFaltantes(Usuario operador) throws IOException {
        if(isAdmin(operador)){
            String archivo = "lista de Faltantes.csv";
            try (FileWriter editor = new FileWriter(archivo)) {
                String linea;
                editor.append("Nombre,Carnet de Identidad\n");
                for (Usuario u : usuarios) {
                    if (!u.marcado) {
                        linea = u.getNombre() + "," + u.getCarnetID() + "\n";
                        editor.append(linea);

                    }
                }
            }
        }
    }

    public static List<Usuario> getUsuarios(){
        return usuarios;
    }

    public static boolean cambiarPassword(Usuario usuario, String pass){
        for(Usuario u:usuarios){
            if(u.equals(usuario)){
                u.setPassword(pass);
                return true;
            }
        }
        return false;
    }

    public static Usuario getOperador(){
        if(operador==null) return root;
        else return operador;
    }
}
