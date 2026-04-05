/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package views;

import controllers.EmpleadoController;
import models.Empleado;
import models.EmpleadoPermanente;
import models.EmpleadoTemporal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Marcelo Suarez
 */

public class VentanaEmpleados extends JDialog {
 
    private final EmpleadoController ctrl;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnAgregar, btnBuscar, btnActualizar, btnEliminar, btnAsignar, btnVolver;
 
    public VentanaEmpleados(JFrame parent, EmpleadoController ctrl) {
        super(parent, "Gestión de Empleados", true);
        this.ctrl = ctrl;
        initUI();
        cargarTabla();
    }
 
    private void initUI() {
        setSize(820, 480);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel();
        header.setBackground(new Color(33, 150, 243));
        header.setPreferredSize(new Dimension(820, 50));
        JLabel lbl = new JLabel("Inventario de Empleados");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.add(lbl);
 
        String[] columnas = {"ID", "Nombre", "Tipo", "Nacimiento", "Contratación", "Desempeño %"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(24);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        JScrollPane scroll = new JScrollPane(tabla);

        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 8, 8));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotones.setBackground(Color.WHITE);
 
        btnAgregar = VentanaPrincipal.crearBoton("Agregar");
        btnBuscar = VentanaPrincipal.crearBoton("Buscar");
        btnActualizar = VentanaPrincipal.crearBoton("Actualizar");
        btnEliminar = VentanaPrincipal.crearBoton("Eliminar");
        btnEliminar.setBackground(new Color(239, 83, 80));
        btnAsignar = VentanaPrincipal.crearBoton("Asignar Depto.");
        btnVolver = VentanaPrincipal.crearBoton("Volver");
        btnVolver.setBackground(new Color(120, 120, 120));
 
        panelBotones.add(btnAgregar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnAsignar);
        panelBotones.add(btnVolver);

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.EAST);

        btnAgregar.addActionListener(e -> dialogoAgregar());
        btnBuscar.addActionListener(e -> dialogoBuscar());
        btnActualizar.addActionListener(e -> dialogoActualizar());
        btnEliminar.addActionListener(e -> accionEliminar());
        btnAsignar.addActionListener(e -> dialogoAsignar());
        btnVolver.addActionListener(e -> dispose());
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Empleado> lista = ctrl.getTodos();
        for (Empleado e : lista) {
            modelo.addRow(new Object[]{
                e.getIdEmpleado(), e.getNombre(), e.getTipo(), sdf.format(e.getFechaNacimiento()), sdf.format(e.getFechaContratacion()), String.format("%.1f%%", e.calcularDesempeno())});
        }
    }
 
    private void dialogoAgregar() {
        JTextField tfId = new JTextField(8);
        JTextField tfNombre = new JTextField(20);
        JTextField tfNac = new JTextField("dd/MM/yyyy", 10);
        JTextField tfCont = new JTextField("dd/MM/yyyy", 10);
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Permanente", "Temporal"});
        JTextField tfExtra = new JTextField(10);
        JTextField tfFinContrato = new JTextField("dd/MM/yyyy (solo Temporal)", 14);
        JCheckBox  chkBonif = new JCheckBox("Bonificación permanencia");
 
        JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
        panel.add(new JLabel("ID (número):")); panel.add(tfId);
        panel.add(new JLabel("Nombre completo:")); panel.add(tfNombre);
        panel.add(new JLabel("Fecha Nacimiento:")); panel.add(tfNac);
        panel.add(new JLabel("Fecha Contratación:")); panel.add(tfCont);
        panel.add(new JLabel("Tipo:")); panel.add(cbTipo);
        panel.add(new JLabel("Salario mensual / Tarifa hora:")); panel.add(tfExtra);
        panel.add(new JLabel("Fin contrato (Temporal):")); panel.add(tfFinContrato);
        panel.add(new JLabel("")); panel.add(chkBonif);
 
        int result = JOptionPane.showConfirmDialog(this, panel, "Nuevo empleado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;
 
        try {
            int id = Integer.parseInt(tfId.getText().trim());
            String nombre  = tfNombre.getText().trim();
            Date nac = sdf.parse(tfNac.getText().trim());
            Date cont = sdf.parse(tfCont.getText().trim());
            double extra = Double.parseDouble(tfExtra.getText().trim());
            String tipo = (String) cbTipo.getSelectedItem();
 
            String msg;
            if ("Permanente".equals(tipo)) {
                msg = ctrl.registrarPermanente(id, nombre, nac, cont, extra, chkBonif.isSelected());
            } else {
                Date fin = sdf.parse(tfFinContrato.getText().trim());
                msg = ctrl.registrarTemporal(id, nombre, nac, cont, extra, fin);
            }
            JOptionPane.showMessageDialog(this, msg);
            cargarTabla();
 
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID o valor numérico inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dialogoBuscar() {
        String input = JOptionPane.showInputDialog(this,
                "Buscar por nombre (parcial):", "Buscar empleado", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.isBlank()) return;
 
        Empleado e = ctrl.buscarPorNombre(input);
        if (e == null) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún empleado con ese nombre.");
        } else {

            for (int i = 0; i < modelo.getRowCount(); i++) {
                if ((int) modelo.getValueAt(i, 0) == e.getIdEmpleado()) {
                    tabla.setRowSelectionInterval(i, i);
                    tabla.scrollRectToVisible(tabla.getCellRect(i, 0, true));
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "Encontrado: " + e.getNombre() + " | Tipo: " + e.getTipo() + " | Desempeño: " + String.format("%.1f%%", e.calcularDesempeno()));
        }
    }

    private void dialogoActualizar() {
        String idStr = JOptionPane.showInputDialog(this, "ID del empleado a actualizar:", "Actualizar datos", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null) return;
 
        try {
            int id = Integer.parseInt(idStr.trim());
            Empleado e = ctrl.buscarPorId(id);
            if (e == null) { JOptionPane.showMessageDialog(this, "ID no encontrado."); 
                return;
            }
 
            JTextField tfNombre = new JTextField(e.getNombre(), 20);
            JTextField tfCont = new JTextField(sdf.format(e.getFechaContratacion()), 10);
 
            JPanel panel = new JPanel(new GridLayout(0, 2, 6, 6));
            panel.add(new JLabel("Nuevo nombre:")); panel.add(tfNombre);
            panel.add(new JLabel("Nueva fecha contratación:")); panel.add(tfCont);
 
            int result = JOptionPane.showConfirmDialog(this, panel, "Actualizar empleado " + id, JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) return;
 
            Date nuevaCont = sdf.parse(tfCont.getText().trim());
            String msg = ctrl.actualizarDatos(id, tfNombre.getText().trim(), nuevaCont);
            JOptionPane.showMessageDialog(this, msg);
            cargarTabla();
 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void accionEliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un empleado en la tabla primero.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);
        int ok = JOptionPane.showConfirmDialog(this,
                "¿Eliminar empleado ID " + id + "?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            String msg = ctrl.eliminarEmpleado(id);
            JOptionPane.showMessageDialog(this, msg);
            cargarTabla();
        }
    }
 
    private void dialogoAsignar() {
        String idEmpStr   = JOptionPane.showInputDialog(this, "ID del empleado:");
        if (idEmpStr == null) return;
        String idDeptoStr = JOptionPane.showInputDialog(this, "ID del departamento destino:");
        if (idDeptoStr == null) return;
 
        try {
            int idEmp   = Integer.parseInt(idEmpStr.trim());
            int idDepto = Integer.parseInt(idDeptoStr.trim());
            String msg  = ctrl.asignarDepartamento(idEmp, idDepto);
            JOptionPane.showMessageDialog(this, msg);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "IDs inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
 