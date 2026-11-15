package ConexioSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexioBD {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String BD = "Hotel";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "Sd47089079*";

    public static void crearBaseDeDatos() {
        String sql = "create database if not exists " + BD;

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             Statement st = conexion.createStatement()) {

            st.executeUpdate(sql);
            IO.println("Base de datos creada correctamente");
        } catch (SQLException e) {
            IO.println(e.getMessage());
        }
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL + BD, USUARIO, PASSWORD);
    }

    // BASE DE DATOS DE RESPALDO (BACKUP)
    public static void crearBaseDeDatos(String BDBACKUP) throws SQLException {

        String sql = "CREATE DATABASE IF NOT EXISTS " + BDBACKUP;

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             Statement st = conexion.createStatement()) {
            st.executeUpdate(sql);
            System.out.println("Base de datos creada correctamente");
        } catch (SQLException e) {
            IO.println(e.getMessage());
        }
    }

    public static Connection conectar(String BDBACKUP) throws SQLException {

        return DriverManager.getConnection(URL + BDBACKUP, USUARIO, PASSWORD);
    }
}
