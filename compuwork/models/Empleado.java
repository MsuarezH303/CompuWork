/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package models;

import java.util.Date;

/**
 *
 * @author Marcelo Suarez
 */

public abstract class Empleado {
    
    private int    idEmpleado;
    private String nombreCompleto;
    private Date   fechaNacimiento;
    private Date   fechaContratacion;
 
    public Empleado(int id, String nombre, Date nac, Date cont) {
        this.idEmpleado = id;
        this.nombreCompleto = nombre;
        this.fechaNacimiento = nac;
        this.fechaContratacion = cont;
    }

    public int getIdEmpleado() { 
        return idEmpleado; 
    }
    public String getNombre() { 
        return nombreCompleto;
    }
    public Date getFechaNacimiento() { 
        return fechaNacimiento; 
    }
    public Date getFechaContratacion() { 
        return fechaContratacion; 
    }
 
    public void setNombre(String nombre) { 
        this.nombreCompleto = nombre; 
    }
    public void setFechaContratacion(Date nuevaCont) { 
        this.fechaContratacion = nuevaCont; 
    }
 
    public abstract double calcularDesempeno();
 
    public abstract String getTipo();
 
    @Override
    public String toString() {
        return "[" + idEmpleado + "] " + nombreCompleto + " (" + getTipo() + ")";
    }
}
    

