package interfaces;

import clases.Hotel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface IntDAO <T>{
    public void crearTabla(Statement st) throws SQLException;
    public  void cargar(Hotel h, Connection c) throws SQLException;
    public void guardar(Connection con, T t) throws SQLException;
}
