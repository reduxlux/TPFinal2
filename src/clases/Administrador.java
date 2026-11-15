package clases;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import ConexioSQL.HotelBackup;
import Enum.ETipoHabitacion;
import Excepciones.ColeccionVaciaException;
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
        try {
            System.out.print("Ingrese el número de habitación: ");
            int numero = teclado.nextInt();
            if (numero <= 0){
                System.out.println("El numero de habitacion debe ser mayor a 0.");
            } else if(super.getHotel().getHabitaciones().containsKey(numero)) {
                System.out.println("error, ya hay otra habitacion con ese numero.");
            } else {
                System.out.print("Ingrese el piso: ");
                int piso = teclado.nextInt();
                if(piso < 0) {
                    System.out.println("El hotel no tiene subsuelo, el piso debe ser igual o mayor a 0.");
                } else {

                    System.out.println("Seleccione el tipo:");
                    System.out.println("1. Simple");
                    System.out.println("2. Doble");
                    System.out.println("3. Suite");

                    int tipo = teclado.nextInt();
                    ETipoHabitacion tipoHabitacion;

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
                            tipoHabitacion = null;
                            break;
                    }
                    if (tipoHabitacion != null) {
                        Habitacion habitacion =
                                new Habitacion(numero, piso, tipoHabitacion);

                        return habitacion;
                    }
                }
            }
        } catch (InputMismatchException e){
            System.out.println("error, tipo de dato invalido");
        }
        return null;
    }

    public void eliminarHabitacion(int numeroHabitacion) throws IDNoEncontradoException{
        super.getHotel().eliminarHabitacion(numeroHabitacion);
    }

    public void listarRecepcionistas() throws ColeccionVaciaException {
        super.getHotel().listarRecepcionistas();
    }

    public void listarHabitaciones() throws ColeccionVaciaException{
        super.getHotel().listarHabitaciones();
    }
}