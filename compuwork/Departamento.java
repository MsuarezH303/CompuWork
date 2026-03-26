package compuwork;

import java.util.ArrayList;
import java.util.List;

public class Departamento {
    private int idDepartamento;
    private String nombreCompleto;
    private String ubicacion; 
    private List<Empleado> empleados;

    public Departamento(int id, String nombre, String ubi) {
        this.idDepartamento = id;
        this.nombreCompleto = nombre;
        this.ubicacion = ubi;
        this.empleados = new ArrayList<>();
    }

    public void agregarEmpleado(Empleado e) {
        if (e != null && !empleados.contains(e)) {
            empleados.add(e);
        }
    }

    public void eliminarEmpleado(int id) {
        boolean exito = empleados.removeIf(emp -> emp.getIdEmpleado() == id);
        if (exito) {
            System.out.println("Confirmado: Empleado " + id + " removido de " + nombreCompleto);
        } else {
            System.out.println("Error el ID " + id + " no existe en este departamento.");
        }
    }

    public void listarEmpleados() {
        System.out.println("\nINTEGRANTES DE " + nombreCompleto.toUpperCase() + " ---");
        if (empleados.isEmpty()) {
            System.out.println("Sin empleados asignados.");
        } else {
            for (Empleado e : empleados) {
                System.out.println("ID: " + e.getIdEmpleado() + " | Nombre: " + e.getNombre());
            }
        }
    }

    public int getIdDepartamento() { 
        return idDepartamento; 
    }

    public String getNombre() { 
        return nombreCompleto; 
    }

    public void setNombre(String n) { 
        this.nombreCompleto = n; 
    }

    public String getUbicacion() { 
        return ubicacion; 
    }

    public void setUbicacion(String u) { 
        this.ubicacion = u; 
    }

    public List<Empleado> getEmpleados() { 
        return empleados; 
    }
}