import ConexioSQL.ConexioBD;
import ConexioSQL.HotelDAO;
import Menus.MenuPrincipal;
import clases.Hotel;

import java.sql.SQLException;
import java.util.Scanner;


void main() {

  try {
    ConexioBD.crearBaseDeDatos();
    HotelDAO.crearTablas();

    Scanner sc = new Scanner(System.in);
    Hotel hotel = new Hotel("Hotel");

    HotelDAO.cargarDatos(hotel);

    MenuPrincipal menu = new MenuPrincipal(hotel, sc);
    menu.menuPrincipal();

  } catch (SQLException e) {
    System.out.println("Error SQL: " + e.getMessage());
  }


}
