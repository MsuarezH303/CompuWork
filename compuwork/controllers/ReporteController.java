/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.Departamento;
import models.Empleado;
import models.ReporteDesempeno;

import java.util.LinkedList;

/**
 *
 * @author Marcelo Suarez
 */

public class ReporteController {
 
    private final LinkedList<Empleado> empRepo;
    private final LinkedList<Departamento> deptoRepo;
    private final ReporteDesempeno motor;
 
    public ReporteController(LinkedList<Empleado> empRepo, LinkedList<Departamento> deptoRepo) {
        this.empRepo = empRepo;
        this.deptoRepo = deptoRepo;
        this.motor = new ReporteDesempeno();
    }
 
    public String reporteIndividual(int idEmp) {
        for (Empleado e : empRepo) {
            if (e.getIdEmpleado() == idEmp) return motor.generarReporteIndividual(e);
        }
        return "Error: empleado ID " + idEmp + " no encontrado.";
    }
 
    public String reporteDepartamento(int idDepto) {
        for (Departamento d : deptoRepo) {
            if (d.getIdDepartamento() == idDepto) return motor.generarReporteDepartamento(d);
        }
        return "Error: departamento ID " + idDepto + " no encontrado.";
    }
}
 