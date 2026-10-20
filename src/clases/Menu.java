package clases;

import java.util.Scanner;

public class Menu {
    int opcion;
    Hotel hotel;
    Administrador a;
    Recepcionista r;
    Scanner sc = new Scanner(System.in);

    public Menu(int opcion) {
        this.opcion = opcion;
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
                    } else if (u instanceof Administrador) {
                        a.menuAdmin();
                    } else if (u instanceof Recepcionista) {
                        r.menuRecepcion();
                    }
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
            }
        } while (opcion != 0);
    }
}
