package ConexioSQL;

import clases.Estadia;
import clases.Habitacion;
import clases.Hotel;
import clases.Pasajero;
import interfaces.IntDAO;

import java.sql.*;

public class EstadiasDAO implements IntDAO<Estadia> {

    @Override
    public void crearTabla(Statement st) throws SQLException {
        String sqlEstadias = """
                CREATE TABLE IF NOT EXISTS estadias (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    dni_pasajero VARCHAR(25) NOT NULL,
                    numero_habitacion INT NOT NULL,
                    fecha_check_in DATE NOT NULL,
                    fecha_check_out DATE,

                    UNIQUE (
                        dni_pasajero,
                        numero_habitacion,
                        fecha_check_in
                    ),

                    FOREIGN KEY (dni_pasajero)
                        REFERENCES pasajeros(dni),

                    FOREIGN KEY (numero_habitacion)
                        REFERENCES habitaciones(numero)
                )
                """;

        st.executeUpdate(sqlEstadias);
    }

    @Override
    public void cargar(Hotel hotel, Connection c) throws SQLException {
        String sql = """
            SELECT * FROM estadias
            ORDER BY fecha_check_in
            """;

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pasajero p = hotel.getPasajeros().obtener(
                        rs.getString("dni_pasajero")
                );

                Habitacion h = hotel.buscarHabitacion(
                        rs.getInt("numero_habitacion")
                );

                Date salida = rs.getDate("fecha_check_out");

                Estadia o = new Estadia(
                        p,
                        h,
                        rs.getDate("fecha_check_in").toLocalDate(),
                        salida == null
                                ? null
                                : salida.toLocalDate()
                );

                hotel.agregarEstadia(o);
                p.agregarEstadia(o);
            }
        }
    }

    @Override
    public void guardar(Connection cn, Estadia estadia) throws SQLException {
        String sql = """
            INSERT INTO estadias
            (dni_pasajero, numero_habitacion,
             fecha_check_in, fecha_check_out)
            VALUES (?, ?, ?, ?)

            ON DUPLICATE KEY UPDATE
                fecha_check_out = VALUES(fecha_check_out)
            """;

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, estadia.getPasajero().getDni());

            ps.setInt(2, estadia.getHabitacion().getNumero());

            ps.setDate(3,
                    Date.valueOf(estadia.getFechaCheckIn()));

            if (estadia.getFechaCheckOut() == null) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4,
                        Date.valueOf(estadia.getFechaCheckOut()));
            }

            ps.executeUpdate();
        }
    }
}
