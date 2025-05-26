package swingUI;

import clases.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ModificarUsuarioDialog extends JDialog {
    public ModificarUsuarioDialog(JFrame parent) {
        super(parent, "Modificar Usuario", true);
        setSize(450, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        JTextField campoSolapin = new JTextField();
        JTextField campoNombre = new JTextField();
        JTextField campoCorreo = new JTextField();

        // Combo para tipo
        String[] tipos = {"Estudiante", "Profesor/Trabajador", "Familiar"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);

        // Datos específicos
        JTextField campoGrupo = new JTextField(20);
        JTextField campoArea = new JTextField(15);
        JTextField campoCargo = new JTextField(15);
        JTextField campoApartamento = new JTextField(10);

        // Panel dinámico
        JPanel dinamicoPanel = new JPanel(new CardLayout());
        dinamicoPanel.add(createPanel(campoGrupo, "Grupo:"), "Estudiante");
        dinamicoPanel.add(createPanel(campoArea, "Área:"), "Profesor/Trabajador");
        dinamicoPanel.add(createPanel(campoApartamento, "Nº Apartamento:"), "Familiar");

        // Acción del combo
        comboTipo.addActionListener(e -> {
            ((CardLayout) dinamicoPanel.getLayout()).show(dinamicoPanel, (String) comboTipo.getSelectedItem());
        });

        panel.add(new JLabel("Número de Solapín*:")); panel.add(campoSolapin);
        panel.add(new JLabel("Nombre y Apellidos:")); panel.add(campoNombre);
        panel.add(new JLabel("Correo Electrónico:")); panel.add(campoCorreo);
        panel.add(new JLabel("Tipo de Usuario:")); panel.add(comboTipo);
        panel.add(new JLabel("Datos Específicos:")); panel.add(dinamicoPanel);

        JButton btnBuscar = new JButton("Buscar Usuario");
        JButton btnGuardar = new JButton("Guardar Cambios");
        JButton btnCancelar = new JButton("Cancelar");

        btnBuscar.addActionListener(e -> {
            String solapin = campoSolapin.getText().trim();
            Usuario user = Sistema.getUsuarios().stream()
                    .filter(u -> u.getSolapin().equals(solapin))
                    .findFirst().orElse(null);

            if (user == null) {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            campoNombre.setText(user.getNombre());
            campoCorreo.setText(user.getCorreo());

            if (user instanceof Estudiante) {
                comboTipo.setSelectedItem("Estudiante");
                campoGrupo.setText(((Estudiante) user).getGrupo());
            } else if (user instanceof Trabajador) {
                comboTipo.setSelectedItem("Profesor/Trabajador");
                campoArea.setText(((Trabajador) user).getArea());
                campoCargo.setText(((Trabajador) user).getCargo());
            } else if (user instanceof Familiar) {
                comboTipo.setSelectedItem("Familiar");
                campoApartamento.setText(String.valueOf(((Familiar) user).getNumApartamento()));
            }
        });

        btnGuardar.addActionListener(e -> {
            String solapin = campoSolapin.getText().trim();
            String nombre = campoNombre.getText().trim();
            String correo = campoCorreo.getText().trim();

            if (solapin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un número de solapín.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tipo = (String) comboTipo.getSelectedItem();
            boolean modificado = false;

            for (Usuario u : Sistema.getUsuarios()) {
                if (u.getSolapin().equals(solapin)) {
                    u.setNombre(nombre);
                    u.setCorreo(correo);

                    if (u instanceof Estudiante && tipo.equals("Estudiante")) {
                        ((Estudiante) u).setGrupo(campoGrupo.getText().trim());
                    } else if (u instanceof Trabajador && tipo.equals("Profesor/Trabajador")) {
                        ((Trabajador) u).setArea(campoArea.getText().trim());
                        ((Trabajador) u).setCargo(campoCargo.getText().trim());
                    } else if (u instanceof Familiar && tipo.equals("Familiar")) {
                        try {
                            ((Familiar) u).setNumApartamento(Integer.parseInt(campoApartamento.getText().trim()));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Número de apartamento inválido.", "Error", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }

                    modificado = true;
                    break;
                }
            }

            if (modificado) {
                JOptionPane.showMessageDialog(this, "Usuario modificado correctamente.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el usuario.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        add(panel, BorderLayout.CENTER);
        add(btnBuscar, BorderLayout.WEST);
        add(btnGuardar, BorderLayout.EAST);
        add(btnCancelar, BorderLayout.SOUTH);

        pack();
    }

    private JPanel createPanel(JTextField field, String label) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel(label));
        p.add(field);
        return p;
    }
}