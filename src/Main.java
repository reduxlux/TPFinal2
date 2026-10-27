
import clases.Administrador;
import clases.Hotel;
import clases.Menu;
import clases.Recepcionista;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Hotel Sonus");
        Menu menu = new Menu(hotel);
        Administrador administrador = new Administrador(hotel, "A", "123", "Carlos");
        Recepcionista recepcionista = new Recepcionista(hotel, "R", "321", "Jenny");
        hotel.agregarAdministrador(administrador);
        hotel.agregarRecepcionista(recepcionista);
        // Falta conectar el hotel con el menú
        // y registrar los usuarios iniciales.

        menu.menuPrincipal();
    }
}
