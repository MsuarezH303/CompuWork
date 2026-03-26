package compuwork;

import java.text.SimpleDateFormat;

public class ReporteDesempeño {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void generarReporteIndividual(Empleado e) {
        System.out.println("\n");
        System.out.println("FICHA TECNICA: " + e.getNombre().toUpperCase());
        System.out.println("ID: " + e.getIdEmpleado());
        System.out.println("Nacimiento: " + sdf.format(e.fechaNacimiento));
        System.out.println("Contratacion: " + sdf.format(e.fechaContratacion));
        System.out.println("Desempeno Actual: " + e.calcularDesempeño() + "%");
        System.out.println("\n");
    }

    public void generarReporteDepartamento(Departamento d) {
        System.out.println("\n");
        System.out.println("ESTADO DEL DEPARTAMENTO: " + d.getNombre().toUpperCase());
        System.out.println("UBICACION: " + d.getUbicacion()); 
        System.out.println("\n");
        
        if (d.getEmpleados().isEmpty()) {
            System.out.println("No hay personal asignado todavia.");
        } else {
            for (Empleado e : d.getEmpleados()) {
                System.out.println("- [" + e.getIdEmpleado() + "] " + e.getNombre() + 
                                   " (" + e.calcularDesempeño() + "%)");
            }
        }
        System.out.println("\n");
    }
}