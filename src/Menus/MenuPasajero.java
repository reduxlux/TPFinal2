package Menus;

import clases.*;
import Excepciones.ColeccionVaciaException;
import Excepciones.FechaInvalidaException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPasajero {

    private Hotel hotel;
    private Scanner sc;

    public MenuPasajero(Hotel hotel, Scanner sc) {
        this.hotel = hotel;
        this.sc = sc;
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
            System.out.println("6. Realizar check-out.");
            System.out.println("0. Salir.");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {

                case 1:
                    try {
                        hotel.crearReserva(p);

                    } catch (FechaInvalidaException e) {
                        System.out.println(e.getMessage());

                    } catch (InputMismatchException e) {
                        System.out.println("Error, el dato es invalido");
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
                    Reserva r =
                            hotel.buscarReservaPasajero(p.getDni());

                    if (r == null) {
                        System.out.println(
                                "Error, no tiene reservas realizadas");

                    } else {
                        try {
                            hotel.realizarCheckInHotel(r);

                        } catch (FechaInvalidaException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case 6:
                    Estadia e =
                            hotel.buscarEstadiaPasajero(p.getDni());

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
                    System.out.println(
                            "Opcion invalida. Intente nuevamente.");
                    break;
            }

        } while (opcion != 0);
    }
}
