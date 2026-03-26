package compuwork;
import java.util.Date;

public class EmpleadoPermanente extends Empleado {
    private double salarioAnual;
    private String paqueteBeneficios;
    private boolean bonificacionPermanencia;

    public EmpleadoPermanente(int id, String nombre, Date nac, Date cont, double salario, boolean bonif) {
        super(id, nombre, nac, cont);
        this.salarioAnual = salario;
        this.bonificacionPermanencia = bonif;
        this.paqueteBeneficios = "Bono de empleado";
    }

    @Override
    public double calcularDesempeño() {
        return bonificacionPermanencia ? 95.5 : 80.0;
    }
}