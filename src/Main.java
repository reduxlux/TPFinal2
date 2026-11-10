
import clases.*;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("SONUS");
        Menu menu = new Menu(hotel);
        Administrador administrador = new Administrador(hotel, "A", "123", "Carlos");
        Recepcionista recepcionista = new Recepcionista(hotel, "R", "321", "Jenny");
        Pasajero pasajero = new Pasajero("Pablo", "123", "321", "1231", hotel);
        hotel.agregarAdministrador(administrador);
        hotel.agregarRecepcionista(recepcionista);
        hotel.registrarPasajeroHotel(pasajero);
        // Falta conectar el hotel con el menú
        // y registrar los usuarios iniciales.

        menu.menuPrincipal();
    }
}
