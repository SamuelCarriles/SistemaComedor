package swingUI;

import clases.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class VentanaPrincipal extends JFrame {

    private JPanel crearPanelInicio() {
        JPanel panelInicio = new JPanel(new BorderLayout(10, 10));
        panelInicio.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Sistema de Control del Comedor", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        panelInicio.add(titulo, BorderLayout.NORTH);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        infoArea.setText(
                "Este sistema permite gestionar el acceso al comedor universitario de la UCI.\n\n" +
                        "FUNCIONALIDADES PRINCIPALES:\n" +
                        "- Marcar entrada de usuarios por número de solapín.\n" +
                        "- Alta, baja y modificación de usuarios (solo Administrador).\n" +
                        "- Generar reportes de usuarios ausentes (solo Jefe de Turno).\n" +
                        "- Visualizar y controlar almuerzos consumidos.\n" +
                        "- Desmarcar a todos los usuarios al finalizar un turno.\n" +
                        "- Cambiar contraseña personal desde cualquier cuenta."
        );
        JScrollPane scrollPane = new JScrollPane(infoArea);
        panelInicio.add(scrollPane, BorderLayout.CENTER);

        JPanel panelUsuario = new JPanel(new GridLayout(3, 1));
        panelUsuario.setBorder(BorderFactory.createTitledBorder("Información de Sesión"));

        JLabel lblRol = new JLabel("Rol: " + Sistema.getOperador().getRol());
        JLabel lblNombre = new JLabel("Nombre: " + Sistema.getOperador().getNombre());
        String solapin = (Sistema.getOperador().getSolapin() == null) ? "no es necesario" : Sistema.getOperador().getSolapin();
        JLabel lblSolapin = new JLabel("Número de Solapín: " + solapin);

        panelUsuario.add(lblRol);
        panelUsuario.add(lblNombre);
        panelUsuario.add(lblSolapin);
        panelInicio.add(panelUsuario, BorderLayout.SOUTH);

        return panelInicio;
    }

    public VentanaPrincipal() {
        setTitle("Sistema de Control del Comedor");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel Inicio
        JPanel panelInicio = crearPanelInicio();
        tabbedPane.addTab("Inicio", null, panelInicio);

        // Panel Gestión Usuarios (solo Admin/Manager)
        if ("administrador".equals(Sistema.getOperador().getRol()) || "manager".equals(Sistema.getOperador().getRol())) {
            JPanel panelUsuarios = new JPanel(new BorderLayout(10, 10));
            JLabel infoUsuarios = new JLabel("Seleccione una acción...", SwingConstants.CENTER);
            infoUsuarios.setFont(new Font("Arial", Font.ITALIC, 16));
            panelUsuarios.add(infoUsuarios, BorderLayout.CENTER);

            JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            JButton btnRegistrar = new JButton("Registrar Usuario");
            JButton btnEliminar = new JButton("Eliminar Usuario");
            JButton btnModificar = new JButton("Modificar Datos");

            btnRegistrar.addActionListener(e -> new RegistroUsuarioDialog(VentanaPrincipal.this).setVisible(true));
            btnEliminar.addActionListener(e -> new EliminarUsuarioDialog(VentanaPrincipal.this).setVisible(true));
            btnModificar.addActionListener(e -> new ModificarUsuarioDialog(VentanaPrincipal.this).setVisible(true));

            botonesPanel.add(btnRegistrar);
            botonesPanel.add(btnEliminar);
            botonesPanel.add(btnModificar);
            panelUsuarios.add(botonesPanel, BorderLayout.SOUTH);

            tabbedPane.addTab("Gestión Usuarios", null, panelUsuarios);
        }

        // Panel Marcar Asistencia
        JPanel panelMarcar = new JPanel();
        panelMarcar.add(new JLabel("Ingrese número de solapín para marcar asistencia"));
        tabbedPane.addTab("Marcar Asistencia", null, panelMarcar);

        // Panel Desmarcar Todos
        JPanel panelDesmarcar = new JPanel();
        panelDesmarcar.add(new JLabel("Botón para desmarcar a todos los usuarios"));
        tabbedPane.addTab("Desmarcar Todos", null, panelDesmarcar);

        // Panel Reportes
        JPanel panelReportes = new JPanel();
        panelReportes.add(new JLabel("Generar reportes diarios/mensuales"));
        tabbedPane.addTab("Reportes", null, panelReportes);

        // Panel Control Almuerzos
        JPanel panelAlmuerzos = new JPanel();
        panelAlmuerzos.add(new JLabel("Control de almuerzos consumidos por profesores/trabajadores"));
        tabbedPane.addTab("Control Almuerzos", null, panelAlmuerzos);

        // Panel Configuración
        JPanel panelConfiguracion = new JPanel();
        panelConfiguracion.setLayout(new BoxLayout(panelConfiguracion, BoxLayout.Y_AXIS));
        JButton btnCambiarPass = new JButton("Cambiar mi Contraseña");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        btnCambiarPass.addActionListener(e -> new CambiarContrasenaDialog(VentanaPrincipal.this).setVisible(true));
        btnCerrarSesion.addActionListener(e -> {
            try {
                Sistema.detener();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
            new Login().setVisible(true);
        });

        panelConfiguracion.add(Box.createVerticalGlue());
        panelConfiguracion.add(btnCambiarPass);
        panelConfiguracion.add(Box.createRigidArea(new Dimension(0, 10)));
        panelConfiguracion.add(btnCerrarSesion);
        panelConfiguracion.add(Box.createVerticalGlue());

        tabbedPane.addTab("Configuración", null, panelConfiguracion);

        add(tabbedPane);

        // Menú superior
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Cerrar Sesión");
        itemSalir.addActionListener(e -> {
            try {
                Sistema.detener();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
            new Login().setVisible(true);
        });
        menuArchivo.add(itemSalir);

        JMenu menuUsuarios = new JMenu("Usuarios");
        if ("administrador".equals(Sistema.getOperador().getRol()) || "manager".equals(Sistema.getOperador().getRol())) {
            JMenuItem itemRegistrar = new JMenuItem("Registrar Usuario");
            JMenuItem itemEliminar = new JMenuItem("Eliminar Usuario");
            JMenuItem itemModificar = new JMenuItem("Modificar Datos");

            itemRegistrar.addActionListener(e -> new RegistroUsuarioDialog(VentanaPrincipal.this).setVisible(true));
            itemEliminar.addActionListener(e -> new EliminarUsuarioDialog(VentanaPrincipal.this).setVisible(true));
            itemModificar.addActionListener(e -> new ModificarUsuarioDialog(VentanaPrincipal.this).setVisible(true));

            menuUsuarios.add(itemRegistrar);
            menuUsuarios.add(itemEliminar);
            menuUsuarios.add(itemModificar);
        }

        JMenu menuConfig = new JMenu("Configuración");
        JMenuItem itemPassword = new JMenuItem("Cambiar Contraseña");
        itemPassword.addActionListener(e -> new CambiarContrasenaDialog(VentanaPrincipal.this).setVisible(true));
        menuConfig.add(itemPassword);

        menuBar.add(menuArchivo);
        menuBar.add(menuUsuarios);
        menuBar.add(menuConfig);

        setJMenuBar(menuBar);
    }
}
