/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package models;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Marcelo Suarez
 */

public class EmpleadoTemporal extends Empleado {
 
    private double tarifaHora;
    private Date   fechaFinContrato;
    private String opcionRenovacion;
 
    public EmpleadoTemporal(int id, String nombre, Date nac, Date cont,double tarifa, Date fin) {
        super(id, nombre, nac, cont);
        this.tarifaHora = tarifa;
        this.fechaFinContrato = fin;
        this.opcionRenovacion = "Pendiente";
    }

    @Override
    public double calcularDesempeno() {
        long diasRestantes = TimeUnit.MILLISECONDS.toDays(fechaFinContrato.getTime() - new Date().getTime());
        if (diasRestantes < 0) return 60.0;
        if (diasRestantes < 30) return 70.0;
        return 78.0;
    }
 
    public void renovarContrato(Date nuevaFecha) {
        this.fechaFinContrato = nuevaFecha;
        this.opcionRenovacion  = "Renovado";
    }
 
    @Override public String getTipo() { return "Temporal"; }
 
    public double getTarifaHora() { 
        return tarifaHora; 
    }
    public void   setTarifaHora(double t) { 
        this.tarifaHora = t; 
    }
    public Date   getFechaFinContrato() { 
        return fechaFinContrato;
    }
    public String getOpcionRenovacion() { 
        return opcionRenovacion; 
    }
}
 