package Menus;

import Excepciones.IDNoEncontradoException;
import clases.*;
import Excepciones.ColeccionVaciaException;
import Excepciones.FechaInvalidaException;

import java.sql.SQLException;
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
            System.out.println("2. Mostrar estadia actual.");
            System.out.println("3. Eliminar reserva.");
            System.out.println("4. Mostrar historial.");
            System.out.println("5. Mostrar reservas.");
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
                    } catch (SQLException e){
                        System.out.println("no se pudo agregar la reserva");
                    } catch (IDNoEncontradoException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    p.mostrarEstadiaActual();
                    break;

                case 3:
                    try {
                        hotel.eliminarReserva(p.getDni());
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("error, tipo de dato incorrecto");
                    }
                    break;
                case 4:
                    try {
                        System.out.println(p.mostrarHistorial());

                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        System.out.println(hotel.mostrarReservasPorPasajero(p.getDni()));
                    } catch (ColeccionVaciaException e) {
                        System.out.println(e.getMessage());
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
