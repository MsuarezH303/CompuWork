package compuwork;
import java.util.Date;

public abstract class Empleado {
    protected int idEmpleado;
    protected String nombreCompleto;
    protected Date fechaNacimiento;
    protected Date fechaContratacion;

    public Empleado(int id, String nombre, Date nac, Date cont) {
        this.idEmpleado = id;
        this.nombreCompleto = nombre;
        this.fechaNacimiento = nac;
        this.fechaContratacion = cont;
    }

    // ACTUALIZADO: Ahora recibe nombre y las dos fechas
    public void actualizarDatos(String nombre, Date nac, Date cont) {
        this.nombreCompleto = nombre;
        this.fechaNacimiento = nac;
        this.fechaContratacion = cont;
        System.out.println("Sistema: Datos del empleado " + idEmpleado + " actualizados correctamente.");
    }

    public int getIdEmpleado() { return idEmpleado; }
    public String getNombre() { return nombreCompleto; }
    
    public abstract double calcularDesempeño();
}