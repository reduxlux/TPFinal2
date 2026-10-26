package clases;

import java.util.Scanner;

public class Administrador extends Usuario {

    Hotel hotel;
    Scanner teclado = new Scanner(System.in);

    public Administrador(String usuario, String contrasenia, String nombre) {
        super(usuario, contrasenia, nombre);
    }

    public boolean crearAdministrador(Administrador nuevoAdministrador) {
        return hotel.agregarAdministrador(nuevoAdministrador);
    }

    public void crearAdministrador() {
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Usuario: ");
        String usuario = teclado.nextLine();

        System.out.print("Contraseña: ");
        String contrasenia = teclado.nextLine();

        Administrador nuevo = new Administrador(
                usuario, contrasenia, nombre);

        if (hotel.agregarAdministrador(nuevo)) {
            System.out.println("Administrador creado correctamente.");
        } else {
            System.out.println("No se pudo crear el administrador.");
        }
    }

    public void realizarBackup() {
        // ...
    }

    public void asignarPermisos() {
        // ...
    }

    public void agregarHabitacion(Habitacion habitacion) {
        hotel.agregarHabitacion(habitacion);
    }

    public void eliminarHabitacion(int numeroHabitacion) {
        hotel.eliminarHabitacion(numeroHabitacion);
    }

}