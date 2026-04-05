/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views;

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

public class VentanaReportes extends JDialog {
 
    private final ReporteController ctrl;
    private final LinkedList<Empleado> empRepo;
    private final LinkedList<Departamento> deptoRepo;
 
    private JTextArea areaReporte;
    private JButton btnIndividual, btnDepartamento, btnVolver;
 
    public VentanaReportes(JFrame parent, ReporteController ctrl,
        LinkedList<Empleado> empRepo,
        LinkedList<Departamento> deptoRepo) {
        super(parent, "Reportes y Visualización", true);
        this.ctrl = ctrl;
        this.empRepo = empRepo;
        this.deptoRepo = deptoRepo;
        initUI();
    }
 
    private void initUI() {
        setSize(680, 480);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel();
        header.setBackground(new Color(33, 150, 243));
        header.setPreferredSize(new Dimension(680, 50));
        JLabel lbl = new JLabel("Reportes de Desempeño");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.add(lbl);
 
        areaReporte = new JTextArea();
        areaReporte.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        areaReporte.setEditable(false);
        areaReporte.setMargin(new Insets(10, 10, 10, 10));
        areaReporte.setText("Seleccione un tipo de reporte para comenzar.");
        JScrollPane scroll = new JScrollPane(areaReporte);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 8, 8));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotones.setBackground(Color.WHITE);
 
        btnIndividual = VentanaPrincipal.crearBoton("Reporte individual");
        btnDepartamento = VentanaPrincipal.crearBoton("Reporte departamento");
        btnVolver = VentanaPrincipal.crearBoton("Volver");
        btnVolver.setBackground(new Color(120, 120, 120));
 
        panelBotones.add(btnIndividual);
        panelBotones.add(btnDepartamento);
        panelBotones.add(btnVolver);
 
        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.EAST);

        btnIndividual.addActionListener(e -> reporteIndividual());
        btnDepartamento.addActionListener(e -> reporteDepartamento());
        btnVolver.addActionListener(e -> dispose());
    }
 
    private void reporteIndividual() {
        if (empRepo.isEmpty()) {
            areaReporte.setText("No hay empleados registrados.");
            return;
        }

        String[] opciones = empRepo.stream().map(e -> e.getIdEmpleado() + " — " + e.getNombre()).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona el empleado:", "Reporte individual", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == null) return;
 
        int id = Integer.parseInt(seleccion.split(" — ")[0].trim());
        areaReporte.setText(ctrl.reporteIndividual(id));
    }
 
    private void reporteDepartamento() {
        if (deptoRepo.isEmpty()) {
            areaReporte.setText("No hay departamentos creados.");
            return;
        }
        String[] opciones = deptoRepo.stream().map(d -> d.getIdDepartamento() + " — " + d.getNombre()).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(this, "Selecciona el departamento:", "Reporte departamento", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (seleccion == null) return;
 
        int id = Integer.parseInt(seleccion.split(" — ")[0].trim());
        areaReporte.setText(ctrl.reporteDepartamento(id));
    }
}
 