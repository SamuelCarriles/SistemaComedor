package swingUI;

import clases.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    public Login() {
        // Configuración del JFrame
        setTitle("Autenticarse");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        // Crear un panel con BorderLayout
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("Autenticarse", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel para los campos de texto
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel labelNombre = new JLabel("Nombre y Apellidos:");
        JTextField campoNombre = new JTextField();

        JLabel labelContrasena = new JLabel("Contraseña:");
        JPasswordField campoContrasena = new JPasswordField();

        formPanel.add(labelNombre);
        formPanel.add(campoNombre);
        formPanel.add(labelContrasena);
        formPanel.add(campoContrasena);

        panel.add(formPanel, BorderLayout.CENTER);

        // Botón Confirmar
        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                String contrasena = new String(campoContrasena.getPassword());
                if(Sistema.acceder(nombre,contrasena)){
                    dispose();
                    new VentanaPrincipal().setVisible(true);
                }
            }
        });

        panel.add(botonConfirmar, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

}
