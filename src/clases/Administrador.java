package clases;

import java.sql.SQLException;
import java.util.Scanner;

import ConexioSQL.HotelBackup;
import Enum.ETipoHabitacion;
import Excepciones.IDNoEncontradoException;

public class Administrador extends Usuario {

    Scanner teclado = new Scanner(System.in);

    public Administrador(Hotel hotel, String usuario, String contrasenia, String nombre) {
        super(hotel, usuario, contrasenia, nombre);
    }

    public Administrador(int id, String usuario, String contrasenia, String nombre, Hotel hotel) {
        super(id, usuario, contrasenia, nombre, hotel);
    }

    public boolean agregarAdministrador(Administrador nuevoAdministrador) {
        return super.getHotel().agregarAdministrador(nuevoAdministrador);
    }

    public boolean agregarRecepcionista(Recepcionista nuevoRecepcionista) {
        return super.getHotel().agregarRecepcionista(nuevoRecepcionista);
    }

    public Administrador crearAdministrador() {

        System.out.print("Usuario: ");
        String usuario = teclado.nextLine();
        boolean x = true;
        for (Administrador administrador : super.getHotel().getAdministradores().getPersonas().values()) {
            if (administrador.getUsuario().equals(usuario)){
                x = false;
            }
        }
        for (Recepcionista recepcionista : super.getHotel().getRecepcionistas().getPersonas().values()) {
            if (recepcionista.getUsuario().equals(usuario)){
                x = false;
            }
        }

        if(x) {

            System.out.print("Nombre: ");
            String nombre = teclado.nextLine();

            System.out.print("Contraseña: ");
            String contrasenia = teclado.nextLine();

            Administrador admin = new Administrador(
                    getHotel(), usuario, contrasenia, nombre);

            return admin;
        } else {
            System.out.println("error, ya hay otro usuario con ese nombre de usuario");
        }
        return null;
    }

    public Recepcionista crearRecepcionista() {

        System.out.print("Usuario: ");
        String usuario = teclado.nextLine();

        boolean x = true;
        for (Administrador administrador : super.getHotel().getAdministradores().getPersonas().values()) {
            if (administrador.getUsuario().equals(usuario)){
                x = false;
            }
        }
        for (Recepcionista recepcionista : super.getHotel().getRecepcionistas().getPersonas().values()) {
            if (recepcionista.getUsuario().equals(usuario)){
                x = false;
            }
        }

        if(x) {

            System.out.print("Nombre: ");
            String nombre = teclado.nextLine();

            System.out.print("Contraseña: ");
            String contrasenia = teclado.nextLine();

            Recepcionista r = new Recepcionista(
                    getHotel(), usuario, contrasenia, nombre);

            return r;
        } else {
            System.out.println("error, ya hay otro usuario con ese nombre de usuario");
        }
        return null;
    }

    public void eliminarRecepcionista(int id) throws IDNoEncontradoException {
        getHotel().eliminarRecepcionista(id);
    }

    public void realizarBackup() {
        try {
            HotelBackup.realizarBackup();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void restaurarBackup() {
        try {
            HotelBackup.restaurarBackup();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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