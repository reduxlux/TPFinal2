package Menus;

import clases.*;
import Excepciones.PasajeroNoEncontradoException;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuPrincipal {

    private Hotel hotel;
    private Scanner sc;

    private MenuAdmin menuAdmin;
    private MenuRecepcionista menuRecepcionista;
    private MenuPasajero menuPasajero;

    public MenuPrincipal(Hotel hotel, Scanner sc) {
        this.hotel = hotel;
        this.sc = sc;
        this.menuAdmin = new MenuAdmin(hotel, sc);
        this.menuRecepcionista = new MenuRecepcionista(hotel, sc);
        this.menuPasajero = new MenuPasajero(hotel, sc);
    }

    public void menuPrincipal() {
        int opcion;
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
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    System.out.println("Ingresar usuario: ");
                    usuario = sc.nextLine();

                    System.out.println("Ingresar contrasenia: ");
                    contrasenia = sc.nextLine();

                    Usuario u = hotel.buscarUsuario(usuario, contrasenia);

                    if (u == null || contrasenia.isEmpty()
                            || usuario.isEmpty()) {

                        System.out.println("Usuario o contraseña incorrectos");

                    } else if (u.iniciarSesion(usuario, contrasenia)) {

                        if (u instanceof Administrador) {
                            menuAdmin.menuAdmin((Administrador) u);

                        } else if (u instanceof Recepcionista) {
                            menuRecepcionista.menuRecepcion((Recepcionista) u);
                        }
                    }
                    break;

                case 2:
                    try {
                        System.out.println("Ingresar DNI: ");
                        String dniPasajero = sc.nextLine();

                        Pasajero p = hotel.buscarPasajero(dniPasajero); //si este metodo lanza la excepcion
                                                                        //se captura con el catch

                        menuPasajero.menuPasajero(p);

                    } catch (PasajeroNoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        Pasajero p = hotel.crearPasajero();
                        menuPasajero.menuPasajero(p);
                    } catch (SQLException e) {
                        System.out.println("hubo un problema al intentar crear la cuenta.");
                    }
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println(
                            "Error, el dato introducido es invalido");
                    break;
            }

        } while (opcion != 0);
    }
}
