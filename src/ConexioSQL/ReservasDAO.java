package ConexioSQL;

import clases.Habitacion;
import clases.Hotel;
import clases.Pasajero;
import clases.Reserva;
import interfaces.IntDAO;
import Enum.EEstadoReserva;

import java.sql.*;

public class ReservasDAO implements IntDAO<Reserva> {

    @Override
    public void crearTabla(Statement st) throws SQLException {
        String sqlReservas = """
                CREATE TABLE IF NOT EXISTS reservas (
                    id INT PRIMARY KEY,
                    dni_pasajero VARCHAR(25) NOT NULL,
                    numero_habitacion INT NOT NULL,
                    fecha_entrada DATE NOT NULL,
                    fecha_salida DATE NOT NULL,
                    estado VARCHAR(30) NOT NULL,

                    FOREIGN KEY (dni_pasajero)
                        REFERENCES pasajeros(dni),

                    FOREIGN KEY (numero_habitacion)
                        REFERENCES habitaciones(numero)
                )
                """;

        st.executeUpdate(sqlReservas);
    }

    @Override
    public void cargar(Hotel hotel, Connection c) throws SQLException {
        String sql = "SELECT * FROM reservas ORDER BY id";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pasajero p = hotel.getPasajeros().obtener(
                        rs.getString("dni_pasajero")
                );

                Habitacion h = hotel.buscarHabitacion(
                        rs.getInt("numero_habitacion")
                );

                Reserva r = new Reserva(
                        p,
                        h,
                        rs.getDate("fecha_entrada").toLocalDate(),
                        rs.getDate("fecha_salida").toLocalDate()
                );

                r.setId(rs.getInt("id"));

                r.setEstado(
                        EEstadoReserva.valueOf(
                                rs.getString("estado"))
                );

                hotel.getReservas().agregar(r);
            }
        }
    }

    @Override
    public void guardar(Connection cn, Reserva r) throws SQLException {
        String sql = """
            INSERT INTO reservas
            (id, dni_pasajero, numero_habitacion,
             fecha_entrada, fecha_salida, estado)
            VALUES (?, ?, ?, ?, ?, ?)

            ON DUPLICATE KEY UPDATE
                dni_pasajero = VALUES(dni_pasajero),
                numero_habitacion = VALUES(numero_habitacion),
                fecha_entrada = VALUES(fecha_entrada),
                fecha_salida = VALUES(fecha_salida),
                estado = VALUES(estado)
            """;

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, r.getId());
            ps.setString(2, r.getPasajero().getDni());
            ps.setInt(3, r.getHabitacion().getNumero());

            ps.setDate(4,
                    Date.valueOf(r.getFechaEntrada()));

            ps.setDate(5,
                    Date.valueOf(r.getFechaSalida()));

            ps.setString(6, r.getEstado().name());

            ps.executeUpdate();
        }
    }

    public void eliminarReserva(int id) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id = ?";
        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
