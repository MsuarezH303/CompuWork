package compuwork;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CompuWork {
    static List<Empleado> dbGeneral = new ArrayList<>();
    static List<Departamento> dbDeptos = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static ReporteDesempeño engine = new ReporteDesempeño();
    
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        int opt = 0;
        
        while (opt != 5) {
            try {
                System.out.println("\nSISTEMA DE RRHH COMPUWORK");
                System.out.println("\n1. Empleados (Registrar/Actualizar/Promover)");
                System.out.println("2. Departamentos (Crear/Modificar/Eliminar)");
                System.out.println("3. Gestion de Asignaciones (Mover Personal)");
                System.out.println("4. Reportes y Visualizacion");
                System.out.println("5. Salir");
                System.out.print("\nSeleccione una opcion: ");

                while (!sc.hasNextInt()) {
                    System.out.println("Error datos invalidados.");
                    sc.next(); 
                }

                opt = sc.nextInt();
                sc.nextLine(); 

                switch (opt) {
                    case 1: gestionEmpleados(); break;
                    case 2: gestionDeptos(); break;
                    case 3: gestionAsignaciones(); break;
                    case 4: gestionReportes(); break;
                    case 5: System.out.println("Cerrando sesion"); break;
                    default: System.out.println("Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println("Error inesperado en el menu: " + e.getMessage());
                sc.nextLine(); 
            }
        }
    }

    static Date pedirFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return sdf.parse(sc.nextLine());
            } catch (ParseException e) {
                System.out.println("Formato invalido use dd/mm/aaaa (Ej: 20/12/1995)");
            }
        }
    }

    static void gestionEmpleados() {
        try {
            System.out.println("\nGESTION DE EMPLEADOS");
            System.out.println("1. Nuevo Registro / 2. Actualizar Datos / 3. Volver");
            int sub = sc.nextInt();
            sc.nextLine();

            if (sub == 1) {
                System.out.print("ID (Solo numeros): ");
                int id = sc.nextInt(); sc.nextLine();
                System.out.print("Nombre Completo: ");
                String n = sc.nextLine();
                
                Date fechaNac = pedirFecha("Fecha de Nacimiento (dd/mm/aaaa): ");
                Date fechaCont = pedirFecha("Fecha de Contratacion (dd/mm/aaaa): ");
                
                System.out.print("Tipo (1:Permanente / 2:Temporal): ");
                int t = sc.nextInt();

                if (t == 1) {
                    System.out.print("Salario anual: ");
                    double sal = sc.nextDouble();
                    dbGeneral.add(new EmpleadoPermanente(id, n, fechaNac, fechaCont, sal, true));
                } else {
                    System.out.print("Tarifa por hora: ");
                    double tar = sc.nextDouble();
                    dbGeneral.add(new EmpleadoTemporal(id, n, fechaNac, fechaCont, tar, new Date()));
                }
                System.out.println("Empleado guardado");

            } else if (sub == 2) {
                System.out.print("Actualizacion de datos, ID del empleado: ");
                int id = sc.nextInt(); sc.nextLine();
                boolean encontrado = false;
                for (Empleado e : dbGeneral) {
                    if (e.getIdEmpleado() == id) {
                        System.out.print("Actualizar Nombre: ");
                        String nuevoNombre = sc.nextLine();
                        Date nuevaNac = pedirFecha("Nueva Fecha Nacimiento: ");
                        Date nuevaCont = pedirFecha("Nueva Fecha Contratacion: ");
                        e.actualizarDatos(nuevoNombre, nuevaNac, nuevaCont);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) System.out.println("Error ID no registrado");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error datos invalidos");
            sc.nextLine(); 
        }
    }

    static void gestionDeptos() {
        try {
            System.out.println("\nGESTION DE DEPARTAMENTOS");
            System.out.println("1. Crear / 2. Modificar / 3. Eliminar / 4. Volver");
            System.out.print("Seleccione una opcion: ");
            int sub = sc.nextInt();
            sc.nextLine();

            if (sub == 1) {
                System.out.print("ID del departamento: "); int id = sc.nextInt(); sc.nextLine();
                System.out.print("Nombre departamento: "); String nom = sc.nextLine();
                System.out.print("Ubicacion (Direccion): "); String ubi = sc.nextLine(); 
                dbDeptos.add(new Departamento(id, nom, ubi));
                System.out.println("Departamento creado con exito.");

            } else if (sub == 2) {
                System.out.print("ID del departamento a modificar: ");
                int idBusq = sc.nextInt(); sc.nextLine();
                
                Departamento deptoEncontrado = null;
                for (Departamento d : dbDeptos) {
                    if (d.getIdDepartamento() == idBusq) {
                        deptoEncontrado = d;
                        break;
                    }
                }

                if (deptoEncontrado != null) {
                    System.out.println("Modificando: " + deptoEncontrado.getNombre());
                    System.out.print("Actualizar nombre (Enter para dejar igual): ");
                    String nuevoNom = sc.nextLine();
                    if (!nuevoNom.isEmpty()) deptoEncontrado.setNombre(nuevoNom);

                    System.out.print("Nueva ubicación/direccion (Enter para dejar igual): ");
                    String nuevaUbi = sc.nextLine();
                    if (!nuevaUbi.isEmpty()) deptoEncontrado.setUbicacion(nuevaUbi); 
                    
                    System.out.println("Datos actualizados.");
                } else {
                    System.out.println("Error ID no encontrado.");
                }

            } else if (sub == 3) {
                System.out.print("ID del departamento a eliminar: ");
                int id = sc.nextInt();
                dbDeptos.removeIf(d -> d.getIdDepartamento() == id);
                System.out.println("Proceso terminado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error entrada no valida.");
            sc.nextLine();
        }
    }

    static void gestionAsignaciones() {
        try {
            if (dbGeneral.isEmpty() || dbDeptos.isEmpty()) {
                System.out.println("Debe tener empleados y departamentos creados.");
                return;
            }
            System.out.print("ID del empleado: "); int idEmp = sc.nextInt();
            System.out.print("ID del departamento de destino: "); int idDep = sc.nextInt();

            Empleado empSeleccionado = null;
            for (Empleado e : dbGeneral) if (e.getIdEmpleado() == idEmp) empSeleccionado = e;

            if (empSeleccionado == null) {
                System.out.println("Empleado no existe.");
                return;
            }

            for (Departamento d : dbDeptos) {
                if (d.getIdDepartamento() == idDep) {
                    d.agregarEmpleado(empSeleccionado);
                } else {
                    d.eliminarEmpleado(idEmp); 
                }
            }
        } catch (Exception e) {
            System.out.println("Error en asignacion.");
            sc.nextLine();
        }
    }

    static void gestionReportes() {
        try {
            System.out.println("\nMENU DE REPORTES");
            System.out.println("1. Reporte Individual (Por Empleado)");
            System.out.println("2. Reporte por Departamento (General)");
            System.out.println("3. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            
            int sub = sc.nextInt();
            sc.nextLine(); 

            switch (sub) {
                case 1: 
                    if (dbGeneral.isEmpty()) {
                        System.out.println("No hay empleados registrados en el departamento.");
                    } else {
                        System.out.print("Ingrese el ID del empleado: ");
                        int idBuscado = sc.nextInt();
                        sc.nextLine();
                        
                        Empleado encontrado = null;
                        for (Empleado e : dbGeneral) {
                            if (e.getIdEmpleado() == idBuscado) {
                                encontrado = e;
                                break;
                            }
                        }
                        
                        if (encontrado != null) {
                            engine.generarReporteIndividual(encontrado);
                        } else {
                            System.out.println("Error no se encontro empleado con el ID " + idBuscado);
                        }
                    }
                    break;

                case 2: 
                    if (dbDeptos.isEmpty()) {
                        System.out.println("No hay departamentos creados.");
                    } else {
                        System.out.print("Ingrese el ID del departamento: ");
                        int idDepto = sc.nextInt();
                        sc.nextLine();
                        
                        Departamento deptoEncontrado = null;
                        for (Departamento d : dbDeptos) {
                            if (d.getIdDepartamento() == idDepto) {
                                deptoEncontrado = d;
                                break;
                            }
                        }
                        
                        if (deptoEncontrado != null) {
                            engine.generarReporteDepartamento(deptoEncontrado);
                        } else {
                            System.out.println("Error no se encontro el departamento con ID " + idDepto);
                        }
                    }
                    break;

                case 3:
                    System.out.println("Regresando");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error datos invalidos");
            sc.nextLine();
        }
    }
}