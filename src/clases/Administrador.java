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


    @Override
    public void iniciarSesion(String nombre, String dni, String usuario) {


    }

    @Override
    public void cerrarSesion(String usuario) {

    }

    public void menuAdmin() {
        int opcion;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("=== MENU ADMIN ===");
            System.out.println("1. Crear administrador.");
            System.out.println("2. Eliminar usuario.");
            System.out.println("3. Agregar habitacion.");
            System.out.println("4. Eliminar habitacion.");
            System.out.println("0. Salir del programa.");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    crearAdministrador();
                    break;
                case 2:
                    // eliminar usuario
                    break;
                case 3:
                    agregarHabitacion(null);
                    // pasarle habitacion
                    break;
                case 4:
                    System.out.println("Ingresar numero habitacion a eliminar: ");
                    int nroHabitacion = Integer.parseInt(sc.nextLine());
                    eliminarHabitacion(nroHabitacion);
                    break;
                case 0:
                    // cerrarSesion();
                    // falta implementar
                    System.out.println("Saliendo...");
                    break;
            }
        } while (opcion != 0);
    }
}