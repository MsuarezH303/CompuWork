/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import controllers.DepartamentoController;
import models.Departamento;
import models.Empleado;
 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
 
/**
 *
 * @author Marcelo Suarez
 */

public class VentanaDepartamentos extends JDialog {
 
    private final DepartamentoController ctrl;
 
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnCrear, btnModificar, btnEliminar, btnVerEmpleados, btnVolver;
 
    public VentanaDepartamentos(JFrame parent, DepartamentoController ctrl) {
        super(parent, "Gestión de Departamentos", true);
        this.ctrl = ctrl;
        initUI();
        cargarTabla();
    }
 
    private void initUI() {
        setSize(700, 430);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
 
        JPanel header = new JPanel();
        header.setBackground(new Color(33, 150, 243));
        header.setPreferredSize(new Dimension(700, 50));
        JLabel lbl = new JLabel("Gestión de Departamentos");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.add(lbl);

        String[] cols = {"ID", "Nombre", "Ubicación", "Nº Empleados"};
        modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 8, 8));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotones.setBackground(Color.WHITE);
 
        btnCrear = VentanaPrincipal.crearBoton("Crear");
        btnModificar = VentanaPrincipal.crearBoton("Modificar");
        btnEliminar = VentanaPrincipal.crearBoton("Eliminar");
        btnEliminar.setBackground(new Color(239, 83, 80));
        btnVerEmpleados = VentanaPrincipal.crearBoton("Ver empleados");
        btnVolver = VentanaPrincipal.crearBoton("Volver");
        btnVolver.setBackground(new Color(120, 120, 120));
 
        panelBotones.add(btnCrear);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerEmpleados);
        panelBotones.add(btnVolver);
 
        add(header, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.EAST);

        btnCrear.addActionListener(e -> dialogoCrear());
        btnModificar.addActionListener(e -> dialogoModificar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnVerEmpleados.addActionListener(e -> verEmpleados());
        btnVolver.addActionListener(e -> dispose());
    }
 
    private void cargarTabla() {
        modelo.setRowCount(0);
        for (Departamento d : ctrl.getTodos()) {
            modelo.addRow(new Object[]{
                d.getIdDepartamento(), d.getNombre(), d.getUbicacion(),d.getEmpleados().size()});
        }
    }
 
    private void dialogoCrear() {
        JTextField tfId  = new JTextField(6);
        JTextField tfNom = new JTextField(18);
        JTextField tfUbi = new JTextField(18);
 
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
        p.add(new JLabel("ID:")); p.add(tfId);
        p.add(new JLabel("Nombre:")); p.add(tfNom);
        p.add(new JLabel("Ubicación:")); p.add(tfUbi);
 
        int result = JOptionPane.showConfirmDialog(this, p, "Nuevo departamento", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;
        try {
            int id  = Integer.parseInt(tfId.getText().trim());
            String nom = tfNom.getText().trim();
            String ubi = tfUbi.getText().trim();
            String msg = ctrl.crear(id, nom, ubi);
            JOptionPane.showMessageDialog(this, msg);
            cargarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void dialogoModificar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { 
            JOptionPane.showMessageDialog(this, "Selecciona un departamento."); 
        return; 
        }
        int id = (int) modelo.getValueAt(fila, 0);
        Departamento d = ctrl.buscarPorId(id);
 
        JTextField tfNom = new JTextField(d.getNombre(), 18);
        JTextField tfUbi = new JTextField(d.getUbicacion(), 18);
 
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
        p.add(new JLabel("Nuevo nombre:")); p.add(tfNom);
        p.add(new JLabel("Nueva ubicación:")); p.add(tfUbi);
 
        int result = JOptionPane.showConfirmDialog(this, p, "Modificar departamento " + id, JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return;
 
        String msg = ctrl.modificar(id, tfNom.getText().trim(), tfUbi.getText().trim());
        JOptionPane.showMessageDialog(this, msg);
        cargarTabla();
    }
 
    private void accionEliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona un departamento."); return; }
        int id = (int) modelo.getValueAt(fila, 0);
        int ok = JOptionPane.showConfirmDialog(this, "¿Eliminar departamento ID " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            String msg = ctrl.eliminar(id);
            JOptionPane.showMessageDialog(this, msg);
            cargarTabla();
        }
    }
 
    private void verEmpleados() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { 
            JOptionPane.showMessageDialog(this, "Selecciona un departamento."); 
            return;
        }
        
        int id = (int) modelo.getValueAt(fila, 0);
        Departamento d = ctrl.buscarPorId(id);
        List<Empleado> lista = d.getEmpleados();
 
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sin empleados en este departamento.");
            return;
        }
        
        StringBuilder sb = new StringBuilder("Empleados de \"" + d.getNombre() + "\":\n\n");
        for (Empleado e : lista) {
            sb.append("  • ["). append(e.getIdEmpleado()). append("] "). append(e.getNombre()). append(" — "). append(e.getTipo()). append(" | "). append(String.format("%.1f%%", e.calcularDesempeno())).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Empleados del departamento", JOptionPane.INFORMATION_MESSAGE);
    }
}
