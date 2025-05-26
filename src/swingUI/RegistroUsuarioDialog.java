package swingUI;

import clases.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegistroUsuarioDialog extends JDialog {
    public RegistroUsuarioDialog(JFrame parent) {
        super(parent, "Registrar Usuario", true);
        setSize(450, 350);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        // Campo: Tipo de usuario
        String[] tipos = {"Selecciona...", "Estudiante", "Profesor/Trabajador", "Familiar"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);

        // Campo: Solapín
        JTextField campoSolapin = new JTextField();
        // Campo: Carnet
        JTextField campoCarnet = new JTextField();
        // Campo: Nombre
        JTextField campoNombre = new JTextField();
        // Campo: Correo
        JTextField campoCorreo = new JTextField();

        // Paneles específicos
        JPanel estudiantePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoGrupo = new JTextField(20);
        estudiantePanel.add(new JLabel("Grupo:"));
        estudiantePanel.add(campoGrupo);

        JPanel trabajadorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoArea = new JTextField(15);
        JTextField campoCargo = new JTextField(15);
        trabajadorPanel.add(new JLabel("Área:"));
        trabajadorPanel.add(campoArea);
        trabajadorPanel.add(new JLabel("Cargo:"));
        trabajadorPanel.add(campoCargo);

        JPanel familiarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField campoApartamento = new JTextField(10);
        familiarPanel.add(new JLabel("Nº Apartamento:"));
        familiarPanel.add(campoApartamento);

        // Panel dinámico
        JPanel dinamicoPanel = new JPanel(new CardLayout());
        dinamicoPanel.add(estudiantePanel, "Estudiante");
        dinamicoPanel.add(trabajadorPanel, "Profesor/Trabajador");
        dinamicoPanel.add(familiarPanel, "Familiar");

        comboTipo.addActionListener(e -> {
            String tipo = (String) comboTipo.getSelectedItem();
            CardLayout cl = (CardLayout) dinamicoPanel.getLayout();
            if (tipo != null && !tipo.equals("Selecciona...")) {
                cl.show(dinamicoPanel, tipo); // Cambia al panel correspondiente
            }
        });
        // Agregar campos comunes
        panel.add(new JLabel("Tipo de Usuario:")); panel.add(comboTipo);
        panel.add(new JLabel("Número de Solapín*:")); panel.add(campoSolapin);
        panel.add(new JLabel("Carnet de Identidad*:")); panel.add(campoCarnet);
        panel.add(new JLabel("Nombre y Apellidos:")); panel.add(campoNombre);
        panel.add(new JLabel("Correo Electrónico:")); panel.add(campoCorreo);
        panel.add(new JLabel("Datos Específicos:")); panel.add(dinamicoPanel);

        // Botones
        JButton btnAceptar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener((ActionEvent e) -> {
            String tipo = (String) comboTipo.getSelectedItem();
            String solapin = campoSolapin.getText().trim();
            String carnet = campoCarnet.getText().trim();
            String nombre = campoNombre.getText().trim();
            String correo = campoCorreo.getText().trim();

            if ("Selecciona...".equals(tipo)) {
                JOptionPane.showMessageDialog(this, "Seleccione un tipo de usuario.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (solapin.isEmpty() || carnet.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El solapín y carnet son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario nuevoUsuario = null;

            switch (tipo) {
                case "Estudiante":
                    String grupo = campoGrupo.getText().trim();
                    if (grupo.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese el grupo.", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    nuevoUsuario = new Estudiante(solapin, carnet, nombre, correo, grupo);
                    break;

                case "Profesor/Trabajador":
                    String area = campoArea.getText().trim();
                    String cargo = campoCargo.getText().trim();
                    if (area.isEmpty() || cargo.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Área y Cargo son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    nuevoUsuario = new Trabajador(solapin, carnet, nombre, correo, area, cargo);
                    break;

                case "Familiar":
                    String apartamentoStr = campoApartamento.getText().trim();
                    if (apartamentoStr.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Ingrese el número de apartamento.", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    int apartamento;
                    try {
                        apartamento = Integer.parseInt(apartamentoStr);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Número de apartamento inválido.", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    nuevoUsuario = new Familiar(solapin, carnet, nombre, correo, apartamento);
                    break;
            }

            if (nuevoUsuario != null && Sistema.addUsuario(Sistema.getOperador(), nuevoUsuario)) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el usuario.\nPuede que ya exista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(btnAceptar);
        botonesPanel.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);

        pack();
    }
}