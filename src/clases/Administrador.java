package clases;

import java.util.Scanner;

public class Administrador extends Usuario {

    Scanner teclado = new Scanner(System.in);

    public Administrador(String usuario, String contrasenia, String nombre) {
        super(usuario, contrasenia, nombre);
    }

    public boolean crearAdministrador(Administrador nuevoAdministrador) {
        return super.getHotel().agregarAdministrador(nuevoAdministrador);
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

        if (super.getHotel().agregarAdministrador(nuevo)) {
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
        super.getHotel().agregarHabitacion(habitacion);
    }

    public void eliminarHabitacion(int numeroHabitacion) {
        super.getHotel().eliminarHabitacion(numeroHabitacion);
    }

}