package Menus;

import clases.*;
import Excepciones.ColeccionVaciaException;
import Excepciones.FechaInvalidaException;
import Excepciones.IDNoEncontradoException;
import Excepciones.PasajeroNoEncontradoException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuRecepcionista {

    private Hotel hotel;
    private Scanner sc;

    public MenuRecepcionista(Hotel hotel, Scanner sc) {
        this.hotel = hotel;
        this.sc = sc;
    }

    public void menuRecepcion(Recepcionista r) {
        int opcion;

        do {
            System.out.println("\n=== MENU RECEPCIONISTA === Recepcionista: " + r.getNombre());
            System.out.println("1. Realizar reserva.");
            System.out.println("2. Eliminar reserva.");
            System.out.println("3. Realizar check-in.");
            System.out.println("4. Realizar check-out.");
            System.out.println("5. Consultar habitaciones.");
            System.out.println("6. Consultar habitaciones disponibles (no reservadas).");
            System.out.println("7. Consultar habitaciones no disponibles (con motivo).");
            System.out.println("8. Consultar habitaciones ocupadas.");
            System.out.println("9. Consultar disponibilidad habitacion.");
            System.out.println("10. Bloquear habitacion.");
            System.out.println("11. Habilitar habitacion.");
            System.out.println("12. Consultar reservas.");
            System.out.println("0. Cerrar sesion.");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {

                case 1:
                    IO.println("Ingrese DNI del pasajero: ");
                    String dni = sc.nextLine();
                    Pasajero p = null;
                    try {
                        // Buscamos al pasajero
                        p = hotel.buscarPasajero(dni);
                    } catch (PasajeroNoEncontradoException e) {
                        // Si no existe, creamos el objeto
                        IO.println("Pasajero no encontrado. Vamos a registrarlo.");
                        try {
                            p = hotel.crearPasajero();
                        } catch (SQLException ex) {
                            System.out.println("se produjo un error al crear el pasajero");
                            p = null;
                        }
                        if(p != null) {
                            System.out.println("pasajero guardado correctamente");
                        }
                    }
                    // Si tenemos un pasajero, intentamos realizar la reserva
                    if (p != null) {
                        try {
                            hotel.crearReserva(p);
                        } catch (FechaInvalidaException e) {
                            IO.println(e.getMessage());
                        } catch (SQLException e) {
                            IO.println("Error al guardar la reserva: " + e.getMessage());
                        } catch (NumberFormatException e) {
                            IO.println("Error: debe ingresar un número válido");
                        } catch (IDNoEncontradoException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                case 2:
                    try {
                        System.out.println("introduzca el dni del pasajero de la reserva: ");
                        String dniPasajero = sc.nextLine();
                        hotel.eliminarReserva(dniPasajero);
                    } catch (InputMismatchException e){
                        System.out.println("error, tipo de dato incorrecto");
                    } catch (ColeccionVaciaException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        r.consultarReservas();
                        System.out.println("Introduzca ID de la reserva: ");
                        int id = Integer.parseInt(sc.nextLine());
                        Reserva reserva = hotel.buscarReserva(id);
                        r.realizarCheckInRec(reserva);
                    } catch (NumberFormatException e) {
                        System.out.println("Error, dato invalido");
                    } catch (IDNoEncontradoException | FechaInvalidaException | ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.println("introduzca el dni del pasajero de la estadia: ");
                        String dniP = sc.nextLine();
                        r.mostrarEstadiasPasajero(dniP);
                        System.out.println("Introduzca el ID de la estadia: ");
                        int idEstadia = Integer.parseInt(sc.nextLine());
                        Estadia estadia = hotel.buscarEstadiaPorId(idEstadia);
                        r.realizarCheckOutRec(estadia);
                    } catch (NumberFormatException e) {
                        System.out.println("Error, dato invalido");
                    } catch (IDNoEncontradoException | ColeccionVaciaException | FechaInvalidaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        hotel.listarHabitaciones();
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        System.out.println(hotel.listarHabitacionesDisponibles());
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        System.out.println(hotel.listarHabitacionesNoDisponibles());
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        System.out.println(hotel.listarHabitacionesOcupadas());
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 9:
                    System.out.println(
                            "Ingresar numero de habitacion a consultar: ");
                    try {
                        int nroHabitacion = Integer.parseInt(sc.nextLine());
                        System.out.println("Ingresar fecha de entrada:");
                        System.out.println("Año: ");
                        int anio1 = Integer.parseInt(sc.nextLine());
                        System.out.println("Mes: ");
                        int mes1 = Integer.parseInt(sc.nextLine());
                        System.out.println("Dia: ");
                        int dia1 = Integer.parseInt(sc.nextLine());
                        LocalDate diaEntrada = LocalDate.of(anio1, mes1, dia1);

                        System.out.println("Ingresar fecha de salida:");
                        System.out.println("Año: ");
                        int anio2 = Integer.parseInt(sc.nextLine());
                        System.out.println("Mes: ");
                        int mes2 = Integer.parseInt(sc.nextLine());
                        System.out.println("Dia: ");
                        int dia2 = Integer.parseInt(sc.nextLine());
                        LocalDate diaSalida = LocalDate.of(anio2, mes2, dia2);
                        if(r.consultarDisponibilidad(nroHabitacion, diaEntrada, diaSalida)){
                            System.out.println("La habitacion esta disponible en esa fecha");
                        } else {
                            System.out.println("la habitacion no esta disponible en esa fecha");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error, dato invalido");
                    } catch (DateTimeException e) {
                        System.out.println("Error, fecha invalida");
                    } catch (IDNoEncontradoException e){
                        System.out.println(e.getMessage());
                    } catch (FechaInvalidaException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 10:
                    System.out.println(
                            "Ingresar numero de habitacion a bloquear: ");
                    try {
                        int nroHabitacion = Integer.parseInt(sc.nextLine());
                        System.out.println("Ingresar motivo de bloqueo: ");
                        String motivo = sc.nextLine();
                        r.bloquearHabitacion(nroHabitacion, motivo);
                    } catch (NumberFormatException | InputMismatchException e){
                        System.out.println("Error: dato invalido");
                    } catch (IDNoEncontradoException e){
                        System.out.println(e.getMessage());
                    } catch (FechaInvalidaException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 11:
                    System.out.println(
                            "Ingresar numero de habitacion a habilitar: ");
                    try {
                        int nroHabitacion = Integer.parseInt(sc.nextLine());
                        r.habilitarHabitacion(nroHabitacion);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: dato invalido");
                    }
                    break;
                case 12:
                    try {
                        r.consultarReservas();
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    r.cerrarSesion();
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Error: opcion invalida");
                    break;
            }

        } while (opcion != 0);
    }
}