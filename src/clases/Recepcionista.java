package clases;

import java.time.LocalDate;
import java.util.Scanner;

public class Recepcionista extends Usuario {

    private Hotel hotel;

    public Recepcionista(String usuario, String contrasenia, String nombre) {
        super(usuario, contrasenia, nombre);
    }

    public void realizarReservaRec(Reserva reserva) {
        hotel.agregarReservaHotel(reserva);
    }

    public void realizarCheckInRec(Reserva reserva) {
        hotel.realizarCheckInHotel(reserva);
    }

    public void realizarCheckOutRec(Ocupacion ocupacion) {
        hotel.realizarCheckOutHotel(ocupacion);
    }

    public void consultarHabitaciones() {
        hotel.listarHabitaciones();
    }

    public void consultarHabitacionesDisponibles() {
        hotel.listarHabitacionesDisponibles();
    }

    public void consultarHabitacionesNoDisponibles() {
        hotel.listarHabitacionesNoDisponibles();
    }

    public void consultarHabitacionesOcupadar() {
        hotel.listarHabitacionesOcupadas();
    }

    public void consultarDisponibilidad(int numero, LocalDate fechaEntrada, LocalDate fechaSalida) {
        hotel.consultarDisponibilidad(numero, fechaEntrada, fechaSalida);
    }

    public void bloquearHabitacion(int numero, String motivo) {
        hotel.bloquearHabitacion(numero, motivo);
    }

    public void habilitarHabitacion(int numero) {
        hotel.habilitarHabitacion(numero);
    }

    public void menuRecepcion() {
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
                    realizarReservaRec(null);
                    // falta crearReserva en clase Reserva
                    break;
                case 2:
                    realizarCheckInRec(null);
                    // falta reserva?
                case 3:
                    realizarCheckOutRec(null);
                    // falta reserva
                    break;
                case 4:
                    consultarHabitaciones();
                    break;
                case 5:
                    consultarHabitacionesDisponibles();
                    break;
                case 6:
                    consultarHabitacionesNoDisponibles();
                    break;
                case 7:
                    consultarHabitacionesOcupadar();
                    break;
                case 8:
                    consultarDisponibilidad(opcion, LocalDate.now(), LocalDate.now());
                    break;
                case 9:
                    int nroHabitacion1;
                    String motivo;
                    System.out.println("Ingresar numero de habitacion a bloquear: ");
                    nroHabitacion1 = Integer.parseInt(sc.nextLine());
                    System.out.println("Ingresar motivo de bloqueo: ");
                    motivo = sc.nextLine();
                    bloquearHabitacion(nroHabitacion1, motivo);
                    break;
                case 10:
                    int nroHabitacion2;
                    System.out.println("Ingresar numero de habitacion a habilitar: ");
                    nroHabitacion2 = Integer.parseInt(sc.nextLine());
                    habilitarHabitacion(nroHabitacion2);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
            }
        } while (opcion != 0);
    }

    @Override
    public void iniciarSesion(String dni, String nombre, String usuario) {

    }

    @Override
    public void cerrarSesion(String usuario) {

    }
}