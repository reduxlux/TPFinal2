package ConexioSQL;

import clases.Hotel;
import clases.Pasajero;
import interfaces.IntDAO;

import java.sql.*;

public class PasajerosDAO implements IntDAO<Pasajero> {

    @Override
    public void crearTabla(Statement st) throws SQLException {
        String sqlPasajeros = """
                CREATE TABLE IF NOT EXISTS pasajeros (
                    dni VARCHAR(25) PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    origen VARCHAR(100),
                    domicilio_origen VARCHAR(200)
                )
                """;

        st.executeUpdate(sqlPasajeros);
    }

    @Override
    public void cargar(Hotel h, Connection c) throws SQLException {
        String sql = "SELECT * FROM pasajeros";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pasajero p = new Pasajero(
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("origen"),
                        rs.getString("domicilio_origen"),
                        h
                );

                h.getPasajeros().agregar(p.getDni(), p);
            }
        }
    }

    @Override
    public void guardar(Connection cn, Pasajero pasajero) throws SQLException {
        String sql = """
            INSERT INTO pasajeros
            (dni, nombre, origen, domicilio_origen)
            VALUES (?, ?, ?, ?)

            ON DUPLICATE KEY UPDATE
                nombre = VALUES(nombre),
                origen = VALUES(origen),
                domicilio_origen = VALUES(domicilio_origen)
            """;

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, pasajero.getDni());
            ps.setString(2, pasajero.getNombre());
            ps.setString(3, pasajero.getOrigen());
            ps.setString(4, pasajero.getDomicilioOrigen());

            ps.executeUpdate();
        }
    }
}
