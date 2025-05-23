package clases;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public final class Sistema implements Serializable {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static Usuario operador=null;
    private static UsuarioRoot root = new UsuarioRoot("rooter","manager","rooter");

    private Sistema() {
        throw new AssertionError("Esta clase no debe ser instanciada");
    }

    public static void init() throws IOException, ClassNotFoundException {
        File archivo = new File("database.bin");
        if (archivo.exists()) {
            cargarDatos();
        }
    }
    public static void acceder(String nombre, String pass){
        for(Usuario u : usuarios){
            if((u.getNombre().equals(nombre))&&(u.getPassword().equals(pass))){
                operador = u;
                JOptionPane.showMessageDialog(
                        null,
                        "¡Bienvenido al sistema "+operador.getNombre()+"!",
                        "Bienvenida al Sistema",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            } else if((u.getNombre().equals(nombre))&&(!u.getPassword().equals(pass))){
                JOptionPane.showMessageDialog(
                        null,
                        "Contraseña inválida. Por favor, vuelva a intentarlo...",
                        "Contraseña Inválida",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(
                null,
                "El usuario "+nombre+" no existe.",
                "Usuario No válido",
                JOptionPane.ERROR_MESSAGE);
    }

    private static boolean isAdmin(Usuario user){
        return (user.getRol().equals("administrador"));
    }

    private static boolean isEmpleado(Usuario user){
        return (user.getRol().equals("empleado"));
    }

    public static boolean addUsuario(Usuario operador, Usuario usuario) {
        if ((usuarios.size() < 1000)&&(!operador.getRol().equals("cliente"))) {
            usuarios.add(usuario);
            return true;
        }
        return false;
    }

    public static boolean deleteUsuario(Usuario operador, String solapin) {
        if(!operador.getRol().equals("cliente")){
            for (Usuario usuario : usuarios) {
                if (usuario.getSolapin().equals(solapin)) {
                    usuarios.remove(usuario);
                    return true;
                }

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

    //metodo temporal
    public static void mostrarInfo() {
        usuarios.forEach(u -> System.out.println(u.nombre));
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
}
