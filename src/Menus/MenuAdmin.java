package Menus;

import clases.*;
import Excepciones.IDNoEncontradoException;
import java.util.Scanner;

public class MenuAdmin {

    private Hotel hotel;
    private Scanner sc = new Scanner(System.in);

    public MenuAdmin(Hotel hotel, Scanner sc) {
        this.hotel = hotel;
        this.sc = sc;
    }

    public void menuAdmin(Administrador a) {
        int opcion;

        do {
            System.out.println("=== MENU ADMIN ===");
            System.out.println("1. Crear usuario.");
            System.out.println("2. Eliminar usuario.");
            System.out.println("3. Crear y agregar habitacion.");
            System.out.println("4. Eliminar habitacion.");
            System.out.println("5. Realizar backup del hotel a hotel_backup.");
            System.out.println("6. Restaurar backup del hotel_backup a hotel.");
            System.out.println("7. Mostrar Administradores y ");
            System.out.println("0. Cerrar sesion.");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    int opcion2;

                    System.out.println("Ingrese el tipo de usuario: ");
                    System.out.println("1. Administrador.");
                    System.out.println("2. Recepcionista.");
                    System.out.println("0. Volver.");

                    try {
                        opcion2 = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        opcion2 = -1;
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
                            System.out.println("Error, el dato es invalido");
                            break;
                    }
                    break;

                case 2:
                    System.out.println("Introduzca el ID del recepcionista: ");

                    try {
                        int id = Integer.parseInt(sc.nextLine());

                        a.eliminarRecepcionista(id);

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Error, el dato introducido es invalido");

                    } catch (IDNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    a.agregarHabitacion(a.crearHabitacion());
                    break;

                case 4:
                    System.out.println(
                            "Ingresar numero de habitacion a eliminar: ");

                    try {
                        int nroHabitacion =
                                Integer.parseInt(sc.nextLine());

                        a.eliminarHabitacion(nroHabitacion);

                    } catch (NumberFormatException e) {
                        System.out.println("Error, dato invalido");
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
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Error, el dato es invalido");
                    break;
            }

        } while (opcion != 0);
    }
}