/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views;

import controllers.DepartamentoController;
import controllers.EmpleadoController;
import controllers.ReporteController;
import models.Departamento;
import models.Empleado;
 
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
 
/**
 *
 * @author Marcelo Suarez
 */

public class VentanaPrincipal extends JFrame {

    private final LinkedList<Empleado> dbEmpleados = new LinkedList<>();
    private final LinkedList<Departamento> dbDepartamentos = new LinkedList<>();
 
    private final EmpleadoController empCtrl;
    private final DepartamentoController deptoCtrl;
    private final ReporteController repCtrl;

    private JButton btnEmpleados;
    private JButton btnDepartamentos;
    private JButton btnReportes;
    private JButton btnSalir;
 
    public VentanaPrincipal() {
        empCtrl = new EmpleadoController(dbEmpleados, dbDepartamentos);
        deptoCtrl = new DepartamentoController(dbDepartamentos);
        repCtrl = new ReporteController(dbEmpleados, dbDepartamentos);
 
        initUI();
    }
 
    private void initUI() {
        setTitle("Sistema RRHH CompuWork");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 380);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel header = new JPanel();
        header.setBackground(new Color(33, 150, 243));
        header.setPreferredSize(new Dimension(480, 70));
        JLabel titulo = new JLabel("SISTEMA DE RRHH COMPUWORK");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.add(titulo);
 
        JPanel centro = new JPanel(new GridLayout(4, 1, 10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        centro.setBackground(Color.WHITE);
 
        btnEmpleados = crearBoton("Gestión de Empleados");
        btnDepartamentos = crearBoton("Gestión de Departamentos");
        btnReportes = crearBoton("Reportes y Visualización");
        btnSalir = crearBoton("Salir");
        btnSalir.setBackground(new Color(239, 83, 80));
 
        centro.add(btnEmpleados);
        centro.add(btnDepartamentos);
        centro.add(btnReportes);
        centro.add(btnSalir);
 
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
 
        btnEmpleados.addActionListener(e -> new VentanaEmpleados(this, empCtrl).setVisible(true));
 
        btnDepartamentos.addActionListener(e -> new VentanaDepartamentos(this, deptoCtrl).setVisible(true));
 
        btnReportes.addActionListener(e -> new VentanaReportes(this, repCtrl, dbEmpleados, dbDepartamentos).setVisible(true));
 
        btnSalir.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(this, "¿Cerrar el sistema?", "Confirmar salida", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) System.exit(0);
        });
    }

    static JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(33, 150, 243));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new VentanaPrincipal().setVisible(true);
        });
    }
}