/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Marcelo Suarez
 */

public class ReporteDesempeno {
 
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public String generarReporteIndividual(Empleado e) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n  FICHA TÉCNICA \n");
        sb.append("  Nombre      : ").append(e.getNombre().toUpperCase()).append("\n");
        sb.append("  ID          : ").append(e.getIdEmpleado()).append("\n");
        sb.append("  Tipo        : ").append(e.getTipo()).append("\n");
        sb.append("  Nacimiento  : ").append(sdf.format(e.getFechaNacimiento())).append("\n");
        sb.append("  Contratación: ").append(sdf.format(e.getFechaContratacion())).append("\n");
        sb.append("  Desempeño   : ").append(String.format("%.1f", e.calcularDesempeno())).append("%\n");
        return sb.toString();
    }
 
    public String generarReporteDepartamento(Departamento d) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n  DEPARTAMENTO: ").append(d.getNombre().toUpperCase()).append("\n");
        sb.append("  Ubicación   : ").append(d.getUbicacion()).append("\n");
 
        List<Empleado> lista = d.getEmpleados();
        if (lista.isEmpty()) {
            sb.append("  Sin personal asignado.\n");
        } else {
            for (Empleado e : lista) {
                sb.append(String.format("  [%d] %-25s %s  %.1f%%\n", e.getIdEmpleado(), e.getNombre(), e.getTipo(), e.calcularDesempeno()));
            }
        }
        return sb.toString();
    }
}
 