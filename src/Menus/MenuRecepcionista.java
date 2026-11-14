package Menus;

import clases.*;
import Excepciones.ColeccionVaciaException;
import Excepciones.FechaInvalidaException;
import Excepciones.IDNoEncontradoException;
import Excepciones.PasajeroNoEncontradoException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.DateTimeException;
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
            System.out.println("\n=== MENU RECEPCIONISTA ===");
            System.out.println("1. Realizar reserva");
            System.out.println("2. Eliminar reserva");
            System.out.println("2. Realizar check-in");
            System.out.println("3. Realizar check-out");
            System.out.println("4. Consultar habitaciones");
            System.out.println("5. Consultar habitaciones disponibles");
            System.out.println("6. Consultar habitaciones no disponibles");
            System.out.println("7. Consultar habitaciones ocupadas");
            System.out.println("8. Consultar disponibilidad habitacion");
            System.out.println("9. Bloquear habitacion");
            System.out.println("10. Habilitar habitacion");
            System.out.println("0. Cerrar sesion");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {

                    case 1:
                        IO.println("ingrese dni del pasajero: ");
                        String dni = sc.nextLine();
                        try {
                            Pasajero p = hotel.buscarPasajero(dni);
                                hotel.crearReserva(p);
                        } catch (SQLException | FechaInvalidaException e) {
                            IO.println(e.getMessage());
                        }catch (PasajeroNoEncontradoException e)
                        {
                            IO.println(e.getMessage());
                            Pasajero p = hotel.crearPasajero();
                    //        hotel.crearReserva(p);
                        }
                        break;
                case 2:
                    hotel.eliminarReserva();
                    break;
                case 3:
                    System.out.println("Introduzca ID de la reserva: ");
                    try {
                        int id = Integer.parseInt(sc.nextLine());
                        Reserva reserva = hotel.buscarReserva(id);
                        r.realizarCheckInRec(reserva);
                    } catch (NumberFormatException e) {
                        System.out.println("Error, dato invalido");
                    } catch (IDNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    } catch (FechaInvalidaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("Introduzca el ID de la estadia: ");
                    try {
                        int idEstadia = Integer.parseInt(sc.nextLine());
                        Estadia estadia = hotel.buscarEstadiaPorId(idEstadia);
                        r.realizarCheckOutRec(estadia);
                    } catch (NumberFormatException e) {
                        System.out.println("Error, dato invalido");
                    } catch (IDNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    } catch (FechaInvalidaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        r.consultarHabitaciones();
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6:
                    try {
                        r.consultarHabitacionesDisponibles();
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 7:
                    try {
                        r.consultarHabitacionesNoDisponibles();
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 8:
                    try {
                        r.consultarHabitacionesOcupadar();
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
                        r.consultarDisponibilidad(nroHabitacion, diaEntrada, diaSalida);
                    } catch (NumberFormatException e) {
                        System.out.println("Error, dato invalido");
                    } catch (DateTimeException e) {
                        System.out.println("Error, fecha invalida");
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
                    } catch (NumberFormatException e) {
                        System.out.println("Error: dato invalido");
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