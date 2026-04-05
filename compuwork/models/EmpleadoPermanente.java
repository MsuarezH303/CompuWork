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

public class EmpleadoPermanente extends Empleado {
 
    private double  salarioMensual;
    private boolean bonificacionPermanencia;
    private String  paqueteBeneficios;
 
    public EmpleadoPermanente(int id, String nombre, Date nac, Date cont, double salario, boolean bonif) {
        super(id, nombre, nac, cont);
        this.salarioMensual = salario;
        this.bonificacionPermanencia = bonif;
        this.paqueteBeneficios = "Bono empleado permanente";
    }
 
    @Override
    public double calcularDesempeno() {
        long diasTrabajados = TimeUnit.MILLISECONDS.toDays(new Date().getTime() - getFechaContratacion().getTime());
        double base;
        if (diasTrabajados < 365) base = 70.0;
        else if (diasTrabajados < 365 * 3) base = 80.0;
        else if (diasTrabajados < 365 * 5) base = 90.0;
        else base = 97.0;
 
        return bonificacionPermanencia ? Math.min(base + 2.5, 100.0) : base;
    }
 
    @Override public String getTipo() { return "Permanente"; }
 
    public double  getSalarioMensual() { 
        return salarioMensual; 
    }
    public void setSalarioMensual(double s) { 
        this.salarioMensual = s; 
    }
    public boolean isBonificacionPermanencia() {
        return bonificacionPermanencia;
    }
    public String  getPaqueteBeneficios() {
        return paqueteBeneficios;
    }
    
}