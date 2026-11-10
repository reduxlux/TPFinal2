package clases;

import java.time.LocalDate;
import java.time.Month;
import java.util.InputMismatchException;
import java.util.Scanner;
import Enum.ETipoHabitacion;

public class Menu {
    private int opcion;
    Scanner sc = new Scanner(System.in);
    private Hotel hotel;

    public Menu (Hotel hotel) {
        this.hotel = hotel;
    }

    /// MENU LOGIN
    public void menuPrincipal() {
        String usuario;
        String contrasenia;
        do {
            System.out.println("=== MENU PRINCIPAL ===");
            System.out.println("1. Ingresar usuario.");
            System.out.println("2. Ingresar pasajero.");
            System.out.println("0. Salir del programa.");
            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e){
                opcion = -1;
            } finally {
                sc.nextLine();
            }

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
                case 2:
                    // AGREGAR COMO LOGUEAR AL PASAJERO
                    System.out.println("Ingresar nombre: ");
                    String nombrePasajero = sc.nextLine();
                    System.out.println("Ingresar DNI: ");
                    String dniPasajero = sc.nextLine();
                    if (hotel.getPasajeros().contiene(dniPasajero)
                    && !nombrePasajero.isEmpty()) {
                        Pasajero p;
                        //menuPasajero(p);
                    }
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("error, el dato introducido es invalido");
            }
        } while (opcion != 0);
    }

    /// MENU ADMIN
    public void menuAdmin(Administrador a) {
        int opcion;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("=== MENU ADMIN ===");
            System.out.println("1. Crear usuario.");
            System.out.println("2. Eliminar usuario.");
            System.out.println("3. Crear y agregar habitacion.");
            System.out.println("4. Eliminar habitacion.");
            System.out.println("0. Cerrar sesion.");
            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e){
                opcion = -1;
            } finally {
                sc.nextLine();
            }

            switch (opcion) {
                case 1:
                    int opcion2;
                    System.out.println("Ingrese el tipo de usuario: ");
                    System.out.println("1. Administrador.");
                    System.out.println("2. Recepcionista.");
                    System.out.println("0. Volver.");
                    try {
                        opcion2 = sc.nextInt();
                    } catch (InputMismatchException e){
                        opcion2 = -1;
                    } finally {
                        sc.nextLine();
                    }
                    switch (opcion2) {
                        case 1:
                            a.agregarAdministrador(a.crearAdministrador());
                            break;
                        case 2:
                            a.agregarRecepcionista(a.crearRecepcionista());
                            break;
                        case 0:
                            System.out.println("Volviendo...");
                            break;
                        default:
                            System.out.println("error, el dato es invalido");
                    }
                    break;
                case 2:
                    System.out.println("Introduzca el id del recepcionista: ");
                    try {
                        int id = sc.nextInt();
                        if (a.eliminarRecepcionista(id)) {
                            System.out.println("recepcionista eliminado correctamente\n");
                        } else {
                            System.out.println("no se encontro el recepcionista\n");
                        }
                    }  catch (InputMismatchException e){
                        System.out.println("error, el dato introducido es invalido");
                    } finally {
                        sc.nextLine();
                    }
                    break;
                case 3:
                    a.agregarHabitacion(a.crearHabitacion());
                    break;
                case 4:
                    System.out.println("Ingresar numero habitacion a eliminar: ");
                    try {
                        int nroHabitacion = sc.nextInt();
                        a.eliminarHabitacion(nroHabitacion);
                    } catch (InputMismatchException e){
                        System.out.println("error, dato invalido");
                    } finally {
                        sc.nextLine();
                    }
                    break;
                case 0:
                    a.cerrarSesion();
                    // falta implementar
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("error, el dato es invalido");
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
            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e){
                opcion = -1;
            } finally {
                sc.nextLine();
            }

            switch (opcion) {
                case 1:
                    System.out.println("introduzca dni del pasajero que desea reservar: ");
                    String dni = sc.nextLine();
                    Pasajero pasajero = hotel.buscarPasajero(dni);
                    r.crearReserva(pasajero);
                    break;
                case 2:
                    System.out.println("introduzca id de la reserva: ");
                    try {
                        int id = sc.nextInt();
                        Reserva reserva = hotel.buscarReserva(id);
                        r.realizarCheckInRec(reserva);
                    } catch (InputMismatchException e){
                        System.out.println("error, dato invalido");
                    } finally {
                        sc.nextLine();
                    }
                    break;
                case 3:
                    System.out.println("introduzca el id de la ocupacion: ");
                    try {
                        int idOcupacion = sc.nextInt();
                        Ocupacion ocupacion = hotel.buscarOcupacion(idOcupacion);
                        r.realizarCheckOutRec(ocupacion);
                    } catch (InputMismatchException e){
                        System.out.println("error, dato invalido");
                    } finally {
                        sc.nextLine();
                    }
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
                    System.out.println("Ingresar numero habitacion a consultar: ");
                    try {
                        int nroHabitacion = sc.nextInt();
                        System.out.println("Ingresar fecha de entrada: ");
                        int anio1 = sc.nextInt();
                        System.out.println(" / ");
                        int mes1 = sc.nextInt();
                        System.out.println(" / ");
                        int dia1 = sc.nextInt();


                        LocalDate diaEntrada = LocalDate.of(anio1, mes1, dia1);

                        System.out.println("Ingresar fecha de salida: ");
                        int anio2 = sc.nextInt();
                        System.out.println(" / ");
                        int mes2 = sc.nextInt();
                        System.out.println(" / ");
                        int dia2 = sc.nextInt();

                        LocalDate diaSalida = LocalDate.of(anio2, mes2, dia2);

                        r.consultarDisponibilidad(nroHabitacion, diaEntrada, diaSalida);
                    } catch (InputMismatchException e){
                        System.out.println("error, dato invalido");
                    }
                    break;
                case 9:
                    int nroHabitacion1;
                    String motivo;
                    System.out.println("Ingresar numero de habitacion a bloquear: ");
                    try {
                        nroHabitacion1 = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Ingresar motivo de bloqueo: ");
                        motivo = sc.nextLine();
                        r.bloquearHabitacion(nroHabitacion1, motivo);
                    } catch (InputMismatchException e){
                        System.out.println("error, dato invalido");
                    } finally {
                        sc.nextLine();
                    }
                    break;
                case 10:
                    int nroHabitacion2;
                    System.out.println("Ingresar numero de habitacion a habilitar: ");
                    try {
                        nroHabitacion2 = sc.nextInt();
                        r.habilitarHabitacion(nroHabitacion2);
                    } catch (InputMismatchException e){
                        System.out.println("error, dato invalido");
                    } finally {
                        sc.nextLine();
                    }
                    break;
                case 0:
                    r.cerrarSesion();
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("error, el dato es invalido");
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
            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e){
                opcion = -1;
            }

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
                    System.out.println(p.mostrarHistorial());
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
} //hola
