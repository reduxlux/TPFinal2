package ConexioSQL;

import clases.Hotel;
import clases.Recepcionista;
import interfaces.IntDAO;

import java.sql.*;

public class RecepcionistasDAO implements IntDAO<Recepcionista> {

    @Override
    public void crearTabla(Statement st) throws SQLException {
        String sqlRecepcionistas = """
               CREATE TABLE IF NOT EXISTS recepcionistas (
               id INT PRIMARY KEY,
               nombre VARCHAR(100) NOT NULL,
               usuario VARCHAR(100) NOT NULL,
               contrasenia VARCHAR(100) NOT NULL
               )
               """;

        st.executeUpdate(sqlRecepcionistas);
    }

    @Override
    public void cargar(Hotel hotel, Connection c) throws SQLException {
        String sql = "SELECT * FROM recepcionistas ORDER BY id";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String usuario = rs.getString("usuario");
                String contrasenia = rs.getString("contrasenia");

                Recepcionista r = new Recepcionista(
                        id, usuario, contrasenia, nombre, hotel);

                hotel.getRecepcionistas().agregar(r.getId(), r);
            }
        }
    }

    @Override
    public void guardar(Connection cn, Recepcionista r) throws SQLException {
        String sql = """
        INSERT INTO recepcionistas 
        (id, nombre, usuario, contrasenia)
        VALUES (?, ?, ?, ?)
        
        ON DUPLICATE KEY UPDATE
            nombre = VALUES(nombre),
            usuario = VALUES(usuario),
            contrasenia = VALUES(contrasenia)
        """;

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, r.getId());

            ps.setString(2, r.getNombre());

            ps.setString(3, r.getUsuario());

            ps.setString(4, r.getContrasenia());

            ps.executeUpdate();
        }
    }

    public void eliminarRecepcionista(int id) throws SQLException {
        String sql = "DELETE FROM recepcionistas WHERE id = ?";
        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
