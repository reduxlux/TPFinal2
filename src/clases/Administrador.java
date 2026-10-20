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

    @Override
    public void mostrarMenu() {
        // ...
    }

    @Override
    public void iniciarSesion(String nombre, String dni, String usuario) {


    }

    @Override
    public void cerrarSesion(String usuario) {

    }
}