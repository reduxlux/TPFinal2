package clases;

import java.time.LocalDate;
import java.time.Month;
import java.util.InputMismatchException;
import java.util.Scanner;
import Enum.ETipoHabitacion;
import Excepciones.ColeccionVaciaException;
import Excepciones.FechaInvalidaException;
import Excepciones.IDNoEncontradoException;
import Excepciones.PasajeroNoEncontradoException;

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
            System.out.println("3. Crear cuenta como pasajero.");
            System.out.println("0. Salir del programa.");
            try {
                opcion = Integer.parseInt(sc.nextLine());
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
                    try {
                        System.out.println("Ingresar DNI: ");
                        String dniPasajero = sc.nextLine();
                        Pasajero p = hotel.buscarPasajero(dniPasajero);
                        menuPasajero(p);
                    } catch (PasajeroNoEncontradoException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Introduzca su DNI: ");
                    String dniPasajero = sc.nextLine();
                    if(hotel.getPasajeros().contiene(dniPasajero)){
                        System.out.println("error, ya hay un pasajero con ese dni");
                    } else {
                        System.out.println("Introduzca su nombre: ");
                        String nombre = sc.nextLine();
                        System.out.println("Introduzca ciudad de origen: ");
                        String ciudad = sc.nextLine();
                        System.out.println("Introduzca su domicilio: ");
                        String domicilio = sc.nextLine();
                        Pasajero p = new Pasajero(nombre, dniPasajero, ciudad, domicilio, hotel);
                        hotel.getPasajeros().agregar(dniPasajero, p);
                        System.out.println("pasajero creado correctamente");
                        menuPasajero(p);
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
            System.out.println("5. Realizar backup del hotel a hotel_backup.");
            System.out.println("6. Restaurar backup del hotel_backup a hotel.");
            System.out.println("0. Cerrar sesion.");
            try {
                opcion = Integer.parseInt(sc.nextLine());
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
                        sc.nextLine();
                        a.eliminarRecepcionista(id);
                    }  catch (InputMismatchException e){
                        System.out.println("error, el dato introducido es invalido");
                        sc.nextLine();
                    } catch (IDNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    a.agregarHabitacion(a.crearHabitacion());
                    break;
                case 4:
                    System.out.println("Ingresar numero habitacion a eliminar: ");
                    try {
                        int nroHabitacion = sc.nextInt();
                        sc.nextLine();
                        a.eliminarHabitacion(nroHabitacion);
                    } catch (InputMismatchException e){
                        System.out.println("error, dato invalido");
                        sc.nextLine();
                    }
                    break;
                case 5:
                    a.realizarBackup();
                    break;
                case 6:
                    a.restaurarBackup();
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
                    try {
                        Pasajero pasajero = hotel.buscarPasajero(dni);
                        r.crearReserva(pasajero);
                    } catch (PasajeroNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }catch (FechaInvalidaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("introduzca id de la reserva: ");
                    try {
                        int id = sc.nextInt();
                        Reserva reserva = hotel.buscarReserva(id);
                        r.realizarCheckInRec(reserva);
                    } catch (InputMismatchException e){
                        System.out.println("error, dato invalido");
                    } catch (IDNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    } catch (FechaInvalidaException e) {
                        System.out.println(e.getMessage());
                    }finally {
                        sc.nextLine();
                    }
                    break;
                case 3:
                    System.out.println("introduzca el id de la estadia: ");
                    try {
                        int idEstadia = sc.nextInt();
                        sc.nextLine();
                        Estadia estadia = hotel.buscarEstadiaPorId(idEstadia);
                        r.realizarCheckOutRec(estadia);
                    } catch (InputMismatchException e){
                        System.out.println("error, dato invalido");
                        sc.nextLine();
                    } catch (IDNoEncontradoException e){
                        System.out.println(e.getMessage());
                    } catch (FechaInvalidaException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        r.consultarHabitaciones();
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        r.consultarHabitacionesDisponibles();
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        r.consultarHabitacionesNoDisponibles();
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        r.consultarHabitacionesOcupadar();
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
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
        do {
            System.out.println("=== MENU PASAJEROS ===");
            System.out.println("1. Agregar reserva.");
            System.out.println("2. Mostrar estadia.");
            System.out.println("3. Eliminar reserva.");
            System.out.println("4. Mostrar historial.");
            System.out.println("5. Realizar check-in.");
            System.out.println("6. Realizar check-out");
            System.out.println("0. Salir.");
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    try {
                        hotel.crearReserva(p);
                    } catch (FechaInvalidaException e) {
                        System.out.println(e.getMessage());
                    } catch (InputMismatchException e){
                        System.out.println("error, el dato es invalido");
                    }
                    break;
                case 2:
                    p.mostrarEstadiaActual();
                    break;
                case 3:
                    hotel.eliminarReserva();
                    break;
                case 4:
                    try {
                        System.out.println(p.mostrarHistorial());
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    Reserva r = hotel.buscarReservaPasajero(p.getDni());
                    if(r == null){
                        System.out.println("error, no tiene reservas realizadas");
                    } else {
                        try {
                            hotel.realizarCheckInHotel(r);
                        } catch (FechaInvalidaException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 6:
                    Estadia e = hotel.buscarEstadiaPasajero(p.getDni());
                    try {
                        hotel.realizarCheckOutHotel(e);
                    } catch (FechaInvalidaException ex) {
                        System.out.println(ex.getMessage());
                    }
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
