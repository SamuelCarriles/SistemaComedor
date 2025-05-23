package Class;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Usuario> usuarios;

    public Sistema() {
        this.usuarios = new ArrayList<>();

    }

    public boolean addUsuario(Usuario usuario) {
        if (usuarios.size() < 1000) {
            usuarios.add(usuario);
            return true;
        }
        return false;
    }

    public boolean deleteUsuario(String solapin) {
        for (Usuario usuario : usuarios) {
            if (usuario.getSolapin().equals(solapin)) {
                usuarios.remove(usuario);
                return true;
            }

        }
        return false;
    }

    public boolean marcarUsuario(String solapin){
        for(Usuario usuario: usuarios) {
            if(usuario.getSolapin().equals(solapin)) {
                usuario.marcado = true;
                return true;
            }
        }
        return false;
    }

    public boolean desmarcarUsuarios(){
        usuarios.forEach(usuario -> {
            usuario.marcado=false;
        });
        for(Usuario u:usuarios){
            if(u.marcado==true) return false;
        }
        return true;
    }
    

}
