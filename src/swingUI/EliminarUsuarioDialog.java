package swingUI;

import clases.Sistema;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EliminarUsuarioDialog extends JDialog {
    public EliminarUsuarioDialog(JFrame parent) {
        super(parent, "Eliminar Usuario", true);
        setSize(400, 150);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JTextField campoSolapin = new JTextField();

        panel.add(new JLabel("Número de Solapín*:"));
        panel.add(campoSolapin);

        JButton btnEliminar = new JButton("Eliminar");
        JButton btnCancelar = new JButton("Cancelar");

        btnEliminar.addActionListener(e -> {
            String solapin = campoSolapin.getText().trim();
            if (solapin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un número de solapín.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (Sistema.deleteUsuario(Sistema.getOperador(), solapin)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado o sin permisos.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        add(panel, BorderLayout.CENTER);
        add(btnEliminar, BorderLayout.SOUTH);
        add(btnCancelar, BorderLayout.PAGE_END);

        pack();
    }
}