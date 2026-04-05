/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.Departamento;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Marcelo Suarez
 */

public class DepartamentoController {
 
    private final LinkedList<Departamento> repositorio;
 
    public DepartamentoController(LinkedList<Departamento> repositorio) {
        this.repositorio = repositorio;
    }
 
    public String crear(int id, String nombre, String ubicacion) {
        if (buscarPorId(id) != null)
            return "Error: ya existe un departamento con ID " + id + ".";
        repositorio.add(new Departamento(id, nombre, ubicacion));
        return "Departamento \"" + nombre + "\" creado correctamente.";
    }
 
    public String modificar(int id, String nuevoNombre, String nuevaUbicacion) {
        Departamento d = buscarPorId(id);
        if (d == null) return "Error: departamento ID " + id + " no encontrado.";
        if (nuevoNombre    != null && !nuevoNombre.isBlank())    d.setNombre(nuevoNombre);
        if (nuevaUbicacion != null && !nuevaUbicacion.isBlank()) d.setUbicacion(nuevaUbicacion);
        return "Departamento " + id + " actualizado.";
    }
 
    public String eliminar(int id) {
        Departamento d = buscarPorId(id);
        if (d == null) return "Error: departamento ID " + id + " no encontrado.";
        if (!d.getEmpleados().isEmpty())
            return "Error: el departamento aún tiene empleados asignados. Reasígnalos primero.";
        repositorio.remove(d);
        return "Departamento " + id + " eliminado.";
    }
 
    public Departamento buscarPorId(int id) {
        for (Departamento d : repositorio) {
            if (d.getIdDepartamento() == id) return d;
        }
        return null;
    }
 
    public List<Departamento> getTodos() {
        return new LinkedList<>(repositorio);
    }
}