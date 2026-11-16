package ConexioSQL;

import clases.*;
import Enum.EEstadoHabitacion;
import Enum.EEstadoReserva;
import Enum.ETipoHabitacion;

import java.sql.*;
import java.time.LocalDate;

public class HotelDAO {
    private static AdministradoresDAO administradoresDAO = new AdministradoresDAO();
    private static RecepcionistasDAO recepcionistasDAO = new RecepcionistasDAO();
    private static PasajerosDAO pasajerosDAO = new PasajerosDAO();
    private static EstadiasDAO estadiasDAO = new EstadiasDAO();
    private static ReservasDAO reservasDAO = new ReservasDAO();
    private static HabitacionesDAO habitacionesDAO = new HabitacionesDAO();

    // CREAR TODAS LAS TABLAS

    public static void crearTablas() throws SQLException{
        String nombreBD = "Hotel";
        crearTablas(nombreBD);
    }

    public static void crearTablas(String nombreBD) throws SQLException {
        try (Connection cn = ConexioBD.conectar(nombreBD);
             Statement st = cn.createStatement()) {
            administradoresDAO.crearTabla(st);
            recepcionistasDAO.crearTabla(st);
            pasajerosDAO.crearTabla(st);
            habitacionesDAO.crearTabla(st);
            reservasDAO.crearTabla(st);
            estadiasDAO.crearTabla(st);

            System.out.println("Tablas creadas correctamente");
        }
    }

    // GUARDAR PASAJERO
    public static void guardarPasajero(Pasajero p)
            throws SQLException {
        try (Connection cn = ConexioBD.conectar()) {
            pasajerosDAO.guardar(cn, p);
        }
    }

    // GUARDAR HABITACION
    public static void guardarHabitacion(Habitacion h)
            throws SQLException {
        try (Connection cn = ConexioBD.conectar()) {
            habitacionesDAO.guardar(cn, h);
        }
    }

    // GUARDAR RESERVA
    public static void guardarReserva(Reserva r)
            throws SQLException {
        try (Connection cn = ConexioBD.conectar()) {
            reservasDAO.guardar(cn, r);
        }
    }

    public static void guardarReservaCompleta(Pasajero p, Reserva r)
            throws SQLException {

        try (Connection cn = ConexioBD.conectar()) {

            cn.setAutoCommit(false);

            try {
                pasajerosDAO.guardar(cn, p);
                reservasDAO.guardar(cn, r);

                cn.commit();

            } catch (SQLException e) {
                cn.rollback();
                throw e;
            }
        }
    }

    // GUARDAR OCUPACION
    public static void guardarEstadia(Estadia o)
            throws SQLException {
        try (Connection cn = ConexioBD.conectar()) {
            estadiasDAO.guardar(cn, o);
        }
    }

    public static void guardarEstadiaYActualizarHabitacion(Estadia o)
            throws SQLException {

        String sqlEstadia = """
        INSERT INTO estadias
        (dni_pasajero, numero_habitacion,
         fecha_check_in, fecha_check_out)
        VALUES (?, ?, ?, ?)
        """;

        String sqlHabitacion = """
        UPDATE habitaciones
        SET estado = ?, motivo_no_disponible = ?
        WHERE numero = ?
        """;

        try (Connection cn = ConexioBD.conectar()) {

            cn.setAutoCommit(false);

            try {
                // 1. Guardamos la estadia
                try (PreparedStatement ps =
                             cn.prepareStatement(sqlEstadia)) {

                    ps.setString(1, o.getPasajero().getDni());
                    ps.setInt(2, o.getHabitacion().getNumero());
                    ps.setDate(3, Date.valueOf(o.getFechaCheckIn()));

                    if (o.getFechaCheckOut() == null) {
                        ps.setNull(4, Types.DATE);
                    } else {
                        ps.setDate(4, Date.valueOf(o.getFechaCheckOut()));
                    }

                    ps.executeUpdate();
                }

                // 2. Actualizamos el estado de la habitación
                try (PreparedStatement ps =
                             cn.prepareStatement(sqlHabitacion)) {

                    ps.setString(1, EEstadoHabitacion.NO_DISPONIBLE.name());
                    ps.setString(2, "");
                    ps.setInt(3, o.getHabitacion().getNumero());

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "No se pudo actualizar el estado de la habitación");
                    }
                }

                // 3. Confirmamos ambas operaciones
                cn.commit();

            } catch (SQLException e) {
                cn.rollback();
                throw e;
            }
        }
    }

    public static void actualizarCheckOut(Estadia estadia, LocalDate fechaSalida) throws SQLException {

        String sqlEstadia = """
        UPDATE estadias
        SET fecha_check_out = ?
        WHERE dni_pasajero = ?
          AND numero_habitacion = ?
          AND fecha_check_in = ?
          AND fecha_check_out IS NULL
        """;

        String sqlHabitacion = """
        UPDATE habitaciones
        SET estado = ?, motivo_no_disponible = ?
        WHERE numero = ?
        """;

        try (Connection cn = ConexioBD.conectar()) {

            cn.setAutoCommit(false);

            try {
                // 1. Actualizamos la fecha de salida de la estadia
                try (PreparedStatement ps = cn.prepareStatement(sqlEstadia)) {

                    ps.setDate(1, Date.valueOf(fechaSalida));
                    ps.setString(2, estadia.getPasajero().getDni());
                    ps.setInt(3, estadia.getHabitacion().getNumero());
                    ps.setDate(4, Date.valueOf(estadia.getFechaCheckIn()));

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "No se encontró una estadia activa para actualizar");
                    }
                }

                // 2. Habilitamos la habitación en SQL
                try (PreparedStatement ps =
                             cn.prepareStatement(sqlHabitacion)) {

                    ps.setString(1, EEstadoHabitacion.DISPONIBLE.name());
                    ps.setString(2, "");
                    ps.setInt(3, estadia.getHabitacion().getNumero());

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "No se pudo actualizar la habitación");
                    }
                }

                // 3. Confirmamos ambas operaciones
                cn.commit();

            } catch (SQLException e) {
                cn.rollback();
                throw e;
            }
        }
    }

    // GUARDAR ADMINISTRADOR
    public static void guardarAdministrador(Administrador a) throws SQLException{
        try (Connection cn = ConexioBD.conectar()) {
            administradoresDAO.guardar(cn, a);
        }
    }

    // GUARDAR RECEPCIONISTA
    public static void guardarRecepcionista(Recepcionista r) throws SQLException{
        try (Connection cn = ConexioBD.conectar()) {
            recepcionistasDAO.guardar(cn, r);
        }
    }

    // CARGAR TODOS LOS DATOS
    public static void cargarDatos(Hotel hotel)
            throws SQLException {
        try (Connection con = ConexioBD.conectar()) {
            administradoresDAO.cargar(hotel, con);
            recepcionistasDAO.cargar(hotel, con);
            pasajerosDAO.cargar(hotel, con);
            estadiasDAO.cargar(hotel, con);
            habitacionesDAO.cargar(hotel, con);
            reservasDAO.cargar(hotel, con);
        }
    }

    // ELIMINACION
    public static void eliminarHabitacion(int numero) throws SQLException {
        habitacionesDAO.eliminarHabitacion(numero);
    }

    public static void eliminarReserva(int id) throws SQLException {
        reservasDAO.eliminarReserva(id);
    }

    public static void eliminarRecepcionista(int id) throws SQLException {
        recepcionistasDAO.eliminarRecepcionista(id);
    }
}

