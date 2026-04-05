/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Marcelo Suarez
 */

public class Departamento {
 
    private int idDepartamento;
    private String nombre;
    private String ubicacion;
    private LinkedList<Empleado> empleados; 
 
    public Departamento(int id, String nombre, String ubicacion) {
        this.idDepartamento = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.empleados = new LinkedList<>();
    }
 
    public boolean agregarEmpleado(Empleado e) {
        if (e == null) 
            return false;
        for (Empleado emp : empleados) {
            if (emp.getIdEmpleado() == e.getIdEmpleado()) 
                return false;
        }
        empleados.add(e);
        return true;
    }

    public boolean eliminarEmpleado(int idEmp) {
        return empleados.removeIf(e -> e.getIdEmpleado() == idEmp);
    }
 
    public int getIdDepartamento() { 
        return idDepartamento; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String n) { 
        this.nombre = n; 
    }
    public String getUbicacion() { 
        return ubicacion; 
    }
    public void setUbicacion(String u) { 
        this.ubicacion = u; 
    }
 
    public List<Empleado> getEmpleados()  { 
        return new LinkedList<>(empleados); 
    }
 
    @Override
    public String toString() { 
        return "[" + idDepartamento + "] " + nombre; 
    }
}
 