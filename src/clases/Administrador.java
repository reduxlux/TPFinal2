package clases;

import java.util.Scanner;
import Enum.ETipoHabitacion;

public class Administrador extends Usuario {

    Scanner teclado = new Scanner(System.in);

    public Administrador(Hotel hotel, String usuario, String contrasenia, String nombre) {
        super(hotel, usuario, contrasenia, nombre);
    }

    public boolean agregarAdministrador(Administrador nuevoAdministrador) {
        return super.getHotel().agregarAdministrador(nuevoAdministrador);
    }

    public boolean agregarRecepcionista(Recepcionista nuevoRecepcionista) {
        return super.getHotel().agregarRecepcionista(nuevoRecepcionista);
    }

    public Administrador crearAdministrador() {
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Usuario: ");
        String usuario = teclado.nextLine();

        System.out.print("Contraseña: ");
        String contrasenia = teclado.nextLine();

        Administrador admin = new Administrador(
                getHotel(), usuario, contrasenia, nombre);

        return admin;
    }

    public Recepcionista crearRecepcionista() {
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Usuario: ");
        String usuario = teclado.nextLine();

        System.out.println("Contrasenia: ");
        String contrasenia = teclado.nextLine();

        Recepcionista recepcionista = new Recepcionista(getHotel(), usuario, contrasenia, nombre);
        return recepcionista;
    }

    public boolean eliminarRecepcionista(int id){
        return getHotel().eliminarRecepcionista(id);
    }

    public void realizarBackup() {
        // ...
    }

    public void asignarPermisos() {
        // ...
    }

    public void agregarHabitacion(Habitacion habitacion) {
        super.getHotel().agregarHabitacion(habitacion);
    }

    public Habitacion crearHabitacion() {
        System.out.print("Ingrese el número de habitación: ");
        int numero = Integer.parseInt(teclado.nextLine());

        System.out.print("Ingrese el piso: ");
        int piso = Integer.parseInt(teclado.nextLine());

        if (numero <= 0 || piso < 0) {
            System.out.println("El numero de habitacion debe ser mayor a 0." +
                    "El hotel no tiene subsuelo, el piso debe ser igual o mayor a 0.");
        }

        System.out.println("Seleccione el tipo:");
        System.out.println("1. Simple");
        System.out.println("2. Doble");
        System.out.println("3. Suite");

        int tipo = Integer.parseInt(teclado.nextLine());
        ETipoHabitacion tipoHabitacion = null;

        switch (tipo) {
            case 1:
                tipoHabitacion = ETipoHabitacion.SIMPLE;
                break;
            case 2:
                tipoHabitacion = ETipoHabitacion.DOBLE;
                break;
            case 3:
                tipoHabitacion = ETipoHabitacion.SUITE;
                break;
            default:
                System.out.println("Tipo inválido.");
                break;
        }
        if (tipoHabitacion != null) {
            System.out.println("Habitacion creada con exito.");
        }
        Habitacion habitacion =
                new Habitacion(numero, piso, tipoHabitacion);

        return habitacion;
    }

    public void eliminarHabitacion(int numeroHabitacion) {
        super.getHotel().eliminarHabitacion(numeroHabitacion);
    }

}