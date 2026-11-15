package ConexioSQL;

import clases.Habitacion;
import clases.Hotel;
import interfaces.IntDAO;
import Enum.EEstadoHabitacion;
import Enum.ETipoHabitacion;

import java.sql.*;

public class HabitacionesDAO implements IntDAO<Habitacion> {

    @Override
    public void crearTabla(Statement st) throws SQLException {
        String sqlHabitaciones = """
                CREATE TABLE IF NOT EXISTS habitaciones (
                    numero INT PRIMARY KEY,
                    piso INT NOT NULL,
                    tipo VARCHAR(30) NOT NULL,
                    estado VARCHAR(30) NOT NULL,
                    motivo_no_disponible VARCHAR(255)
                )
                """;

        st.executeUpdate(sqlHabitaciones);
    }

    @Override
    public void cargar(Hotel hotel, Connection c) throws SQLException {
        String sql = "SELECT * FROM habitaciones";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Habitacion h = new Habitacion(
                        rs.getInt("numero"),
                        rs.getInt("piso"),
                        ETipoHabitacion.valueOf(
                                rs.getString("tipo"))
                );

                h.setEstado(
                        EEstadoHabitacion.valueOf(
                                rs.getString("estado"))
                );

                h.setMotivoNoDisponible(
                        rs.getString("motivo_no_disponible")
                );

                hotel.agregarHabitacion(h);
            }
        }
    }

    @Override
    public void guardar(Connection cn, Habitacion h) throws SQLException {
        String sql = """
            INSERT INTO habitaciones
            (numero, piso, tipo, estado, motivo_no_disponible)
            VALUES (?, ?, ?, ?, ?)

            ON DUPLICATE KEY UPDATE
                piso = VALUES(piso),
                tipo = VALUES(tipo),
                estado = VALUES(estado),
                motivo_no_disponible = VALUES(motivo_no_disponible)
            """;

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, h.getNumero());
            ps.setInt(2, h.getPiso());
            ps.setString(3, h.getTipo().name());
            ps.setString(4, h.getEstado().name());
            ps.setString(5, h.getMotivoNoDisponible());

            ps.executeUpdate();
        }
    }

    public void eliminarHabitacion(int numero) throws SQLException {
        String sql = "DELETE FROM habitaciones WHERE numero = ?";
        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, numero);
            ps.executeUpdate();
        }
    }
}
