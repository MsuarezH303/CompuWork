package compuwork;
import java.util.Date;

public class EmpleadoTemporal extends Empleado {
    private double tarifaHora;
    private Date fechaFinalizacionContrato;
    private String opcionRenovacion;

    public EmpleadoTemporal(int id, String nombre, Date nac, Date cont, double tarifa, Date fin) {
        super(id, nombre, nac, cont); 
        this.tarifaHora = tarifa;
        this.fechaFinalizacionContrato = fin;
        this.opcionRenovacion = "Pendiente";
    }

    @Override
    public double calcularDesempeño() {
        return 75.0; 
    }

    public void renovarContrato(Date nuevaFecha) {
        this.fechaFinalizacionContrato = nuevaFecha;
    }
}