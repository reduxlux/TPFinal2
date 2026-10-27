package clases;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    private int opcion;
    private static Hotel hotel;
    Scanner sc = new Scanner(System.in);

    public static Hotel getHotel() {
        return hotel;
    }

    /// GETTER Y SETTER?
    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    /// MENU LOGIN
    public void menuPrincipal() {
        String usuario;
        String contrasenia;
        do {
            System.out.println("=== MENU PRINCIPAL ===");
            System.out.println("1. Ingresar usuario");
            System.out.println("0. Salir del programa.");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    System.out.println("Ingresar usuario: ");
                    usuario = sc.nextLine();
                    System.out.println("Ingresar contrasenia: ");
                    contrasenia = sc.nextLine();
                    Usuario u = hotel.buscarUsuario(usuario, contrasenia);
                    if (u == null || contrasenia.isEmpty() || usuario.isEmpty()) {
                        System.out.println("Usuario o contraseña incorrectos");
                    } else if (u.iniciarSesion(usuario, contrasenia)) {
                        if (u instanceof Administrador) {
                            menuAdmin((Administrador) u);
                        } else if (u instanceof Recepcionista) {
                            menuRecepcion((Recepcionista) u);
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
            }
        } while (opcion != 0);
    }

    /// MENU ADMIN
    public void menuAdmin(Administrador a) {
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
                    a.crearAdministrador();
                    break;
                case 2:
                    System.out.println("Introduzca el id del recepcionista: ");
                    int id = sc.nextInt();
                    if(a.eliminarRecepcionista(id)){
                        System.out.println("recepcionista eliminado correctamente\n");
                    } else {
                        System.out.println("no se encontro el recepcionista\n");
                    }
                    break;
                case 3:
                    a.agregarHabitacion(null);
                    // pasarle habitacion
                    break;
                case 4:
                    System.out.println("Ingresar numero habitacion a eliminar: ");
                    int nroHabitacion = Integer.parseInt(sc.nextLine());
                    a.eliminarHabitacion(nroHabitacion);
                    break;
                case 0:
                    a.cerrarSesion();
                    // falta implementar
                    System.out.println("Saliendo...");
                    break;
            }
        } while (opcion != 0);
    }

    /// MENU RECEPCION
    public void menuRecepcion(Recepcionista r) {
        int opcion;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n=== MENÚ RECEPCIONISTA ===");
            System.out.println("1. Realizar reserva");
            System.out.println("2. Realizar check-in");
            System.out.println("3. Realizar check-out");
            System.out.println("4. Consultar habitaciones");
            System.out.println("5. Consultar habitaciones disponibles");
            System.out.println("6. Consultar habitaciones no disponibles");
            System.out.println("7. Consultar habitaciones ocupadas");
            System.out.println("8. Consultar disponibilidad habitacion");
            System.out.println("9. Bloquear habitacion");
            System.out.println("10. Habilitar habitacion");
            System.out.println("0. Cerrar sesión");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    r.realizarReservaRec(null);
                    System.out.println("introduzca dni del pasajero que desea reservar: ");
                    String dni = sc.nextLine();
                    Pasajero pasajero = hotel.buscarPasajero(dni);
                    r.crearReserva(pasajero);
                    break;
                case 2:
                    System.out.println("introduzca id de la reserva: ");
                    int id = sc.nextInt();
                    Reserva reserva = hotel.buscarReserva(id);
                    r.realizarCheckInRec(reserva);
                    break;
                case 3:
                    System.out.println("introduzca el id de la ocupacion: ");
                    int idOcupacion = sc.nextInt();
                    Ocupacion ocupacion = hotel.buscarOcupacion(idOcupacion);
                    r.realizarCheckOutRec(ocupacion);
                    break;
                case 4:
                    r.consultarHabitaciones();
                    break;
                case 5:
                    r.consultarHabitacionesDisponibles();
                    break;
                case 6:
                    r.consultarHabitacionesNoDisponibles();
                    break;
                case 7:
                    r.consultarHabitacionesOcupadar();
                    break;
                case 8:
                    r.consultarDisponibilidad(opcion, LocalDate.now(), LocalDate.now());
                    break;
                case 9:
                    int nroHabitacion1;
                    String motivo;
                    System.out.println("Ingresar numero de habitacion a bloquear: ");
                    nroHabitacion1 = Integer.parseInt(sc.nextLine());
                    System.out.println("Ingresar motivo de bloqueo: ");
                    motivo = sc.nextLine();
                    r.bloquearHabitacion(nroHabitacion1, motivo);
                    break;
                case 10:
                    int nroHabitacion2;
                    System.out.println("Ingresar numero de habitacion a habilitar: ");
                    nroHabitacion2 = Integer.parseInt(sc.nextLine());
                    r.habilitarHabitacion(nroHabitacion2);
                    break;
                case 0:
                    r.cerrarSesion();
                    System.out.println("Saliendo...");
                    break;
            }
        } while (opcion != 0);
    }

    public void menuPasajero(Pasajero p) {
        int opcion;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("=== MENU PASAJEROS ===");
            System.out.println("1. Agregar estadia.");
            System.out.println("2. Mostrar estadia.");
            System.out.println("3. Eliminar estadia.");
            System.out.println("4. Mostrar historial.");
            System.out.println("0. Salir.");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    p.crearEstadia();
                    break;
                case 2:
                    p.mostrarEstadiaActual();
                    break;
                case 3:
                    p.eliminarEstadia();
                    break;
                case 4:
                    p.mostrarHistorial();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 0);
    }
}
