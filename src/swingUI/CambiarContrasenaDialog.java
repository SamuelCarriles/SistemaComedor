package swingUI;

import clases.Sistema;
import clases.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CambiarContrasenaDialog extends JDialog {

    public CambiarContrasenaDialog(JFrame parent) {
        super(parent, "Cambiar Contraseña", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        // Campo: Solapin
        JTextField campoSolapin = new JTextField();
        panel.add(new JLabel("Número de Solapín*:"));
        panel.add(campoSolapin);

        // Campo: Contraseña actual
        JPasswordField campoPassActual = new JPasswordField();
        panel.add(new JLabel("Contraseña Actual*:"));
        panel.add(campoPassActual);

        // Campo: Nueva contraseña
        JPasswordField campoNuevaPass = new JPasswordField();
        panel.add(new JLabel("Nueva Contraseña*:"));
        panel.add(campoNuevaPass);

        JButton btnAceptar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener((ActionEvent e) -> {
            String solapin = campoSolapin.getText().trim();

            if (solapin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el número de solapín.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String passActual = new String(campoPassActual.getPassword());
            String nuevaPass = new String(campoNuevaPass.getPassword());

            if (passActual.isEmpty() || nuevaPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No puede dejar campos vacíos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean encontrado = false;

            for (Usuario u : Sistema.getUsuarios()) {
                if (u.getSolapin().equals(solapin)) {

                    // Verificar contraseña actual
                    if (!u.getPassword().equals(passActual)) {
                        JOptionPane.showMessageDialog(this, "La contraseña actual es incorrecta.", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Permitir cambio de contraseña
                    u.setPassword(nuevaPass);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                JOptionPane.showMessageDialog(this, "Contraseña cambiada correctamente.");
                dispose(); // Cerrar ventana
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        add(panel, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(btnAceptar);
        botonesPanel.add(btnCancelar);
        add(botonesPanel, BorderLayout.SOUTH);

        pack();
    }
}