/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 
package controllers;

import models.Departamento;
import models.Empleado;
import models.EmpleadoPermanente;
import models.EmpleadoTemporal;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Marcelo Suarez
 */

public class EmpleadoController {
 
    private final LinkedList<Empleado>    repositorio;
    private final LinkedList<Departamento> deptoRepo;
 
    public EmpleadoController(LinkedList<Empleado> repositorio, LinkedList<Departamento> deptoRepo) {
        this.repositorio = repositorio;
        this.deptoRepo   = deptoRepo;
    }

    public String registrarPermanente(int id, String nombre, Date nac, Date cont, double salario, boolean bonif) {
        if (existeId(id)) return "Error: ya existe un empleado con ID " + id + ".";
        repositorio.add(new EmpleadoPermanente(id, nombre, nac, cont, salario, bonif));
        return "Empleado permanente registrado correctamente.";
    }

    public String registrarTemporal(int id, String nombre, Date nac, Date cont, double tarifa, Date finContrato) {
        if (existeId(id)) return "Error: ya existe un empleado con ID " + id + ".";
        repositorio.add(new EmpleadoTemporal(id, nombre, nac, cont, tarifa, finContrato));
        return "Empleado temporal registrado correctamente.";
    }

    public String actualizarDatos(int id, String nuevoNombre, Date nuevaCont) {
        Empleado e = buscarPorId(id);
        if (e == null) return "Error: ID " + id + " no encontrado.";
        e.setNombre(nuevoNombre);
        e.setFechaContratacion(nuevaCont);
        return "Datos del empleado " + id + " actualizados correctamente.";
    }
 
    public String asignarDepartamento(int idEmp, int idDepto) {
        Empleado emp = buscarPorId(idEmp);
        if (emp == null) return "Error: empleado ID " + idEmp + " no existe.";
 
        Departamento destino = null;
        for (Departamento d : deptoRepo) {
            if (d.getIdDepartamento() == idDepto) {
                destino = d;
                break;
            }
        }
        if (destino == null) return "Error: departamento ID " + idDepto + " no existe.";
 
        for (Departamento d : deptoRepo) {
            d.eliminarEmpleado(idEmp);  
        }

        destino.agregarEmpleado(emp);
        return "Empleado " + emp.getNombre() + " asignado a " + destino.getNombre() + ".";
    }

    public String eliminarEmpleado(int id) {
        Empleado emp = buscarPorId(id);
        if (emp == null) return "Error: ID " + id + " no encontrado.";
        for (Departamento d : deptoRepo) d.eliminarEmpleado(id);
        repositorio.remove(emp);
        return "Empleado " + id + " eliminado del sistema.";
    }

    public Empleado buscarPorId(int id) {
        for (Empleado e : repositorio) {
            if (e.getIdEmpleado() == id) return e;
        }
        return null;
    }
 
    public Empleado buscarPorNombre(String nombre) {
        String query = nombre.toLowerCase().trim();
        for (Empleado e : repositorio) {
            if (e.getNombre().toLowerCase().contains(query)) return e;
        }
        return null;
    }
 
    public List<Empleado> getTodos() {
        return new LinkedList<>(repositorio);
    }

    private boolean existeId(int id) {
        return buscarPorId(id) != null;
    }
}