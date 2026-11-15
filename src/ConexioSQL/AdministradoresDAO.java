package ConexioSQL;

import clases.Administrador;
import clases.Hotel;
import interfaces.IntDAO;

import java.sql.*;

public class AdministradoresDAO implements IntDAO<Administrador> {
    @Override
    public void crearTabla(Statement st) throws SQLException {
        String sqlAdministradores = """
               CREATE TABLE IF NOT EXISTS administradores (
               id INT PRIMARY KEY,
               nombre VARCHAR(100) NOT NULL,
               usuario VARCHAR(100) PRIMARY KEY,
               contrasenia VARCHAR(100) NOT NULL
               )
               """;

        st.executeUpdate(sqlAdministradores);
    }

    @Override
    public void cargar(Hotel hotel, Connection c) throws SQLException {
        String sql = "SELECT * FROM administradores ORDER BY id";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String usuario = rs.getString("usuario");
                String contrasenia = rs.getString("contrasenia");

                Administrador a = new Administrador(
                        id, usuario, contrasenia, nombre, hotel);

                hotel.getAdministradores().agregar(a.getId(), a);
            }
        }
    }

    @Override
    public void guardar(Connection cn, Administrador a) throws SQLException {
        String sql = """
        INSERT INTO administradores 
        (id, nombre, usuario, contrasenia)
        VALUES (?, ?, ?, ?)
        
        ON DUPLICATE KEY UPDATE
            nombre = VALUES(nombre),
            usuario = VALUES(usuario),
            contrasenia = VALUES(contrasenia)
        """;

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, a.getId());

            ps.setString(2, a.getNombre());

            ps.setString(3, a.getUsuario());

            ps.setString(4, a.getContrasenia());

            ps.executeUpdate();
        }
    }
}
