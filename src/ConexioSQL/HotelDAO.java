package ConexioSQL;

import clases.*;
import Enum.EEstadoHabitacion;
import Enum.EEstadoReserva;
import Enum.ETipoHabitacion;

import java.sql.*;

public class HotelDAO {

    // CREAR TODAS LAS TABLAS
    public static void crearTablas() throws SQLException {
        crearTablas("Hotel");
    }

    public static void crearTablas(String nombreBD) throws SQLException {

        try (Connection cn = ConexioBD.conectar(nombreBD);
             Statement st = cn.createStatement()) {

            // Tabla pasajeros
            String sqlPasajeros = """
                CREATE TABLE IF NOT EXISTS pasajeros (
                    dni VARCHAR(25) PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    origen VARCHAR(100),
                    domicilio_origen VARCHAR(200)
                )
                """;

            st.executeUpdate(sqlPasajeros);

            // Tabla habitaciones
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

            // Tabla reservas
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

            // Tabla ocupaciones
            String sqlOcupaciones = """
                CREATE TABLE IF NOT EXISTS ocupaciones (
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

            st.executeUpdate(sqlOcupaciones);

            // Tabla administradores
            String sqlAdministradores = """
               CREATE TABLE IF NOT EXISTS administradores (
               id INT PRIMARY KEY,
               nombre VARCHAR(100) NOT NULL,
               usuario VARCHAR(100) NOT NULL,
               contrasenia VARCHAR(100) NOT NULL
               )
               """;

            st.executeUpdate(sqlAdministradores);

            // Tabla recepcionistas
            String sqlRecepcionistas = """
               CREATE TABLE IF NOT EXISTS recepcionistas (
               id INT PRIMARY KEY,
               nombre VARCHAR(100) NOT NULL,
               usuario VARCHAR(100) NOT NULL,
               contrasenia VARCHAR(100) NOT NULL
               )
               """;

            st.executeUpdate(sqlRecepcionistas);

            System.out.println("Tablas creadas correctamente");
        }
    }

    // GUARDAR PASAJERO
    public static void guardarPasajero(Pasajero p)
            throws SQLException {

        String sql = """
            INSERT INTO pasajeros
            (dni, nombre, origen, domicilio_origen)
            VALUES (?, ?, ?, ?)

            ON DUPLICATE KEY UPDATE
                nombre = VALUES(nombre),
                origen = VALUES(origen),
                domicilio_origen = VALUES(domicilio_origen)
            """;

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getOrigen());
            ps.setString(4, p.getDomicilioOrigen());

            ps.executeUpdate();
        }
    }

    // GUARDAR HABITACION
    public static void guardarHabitacion(Habitacion h)
            throws SQLException {

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

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, h.getNumero());
            ps.setInt(2, h.getPiso());
            ps.setString(3, h.getTipo().name());
            ps.setString(4, h.getEstado().name());
            ps.setString(5, h.getMotivoNoDisponible());

            ps.executeUpdate();
        }
    }

    // GUARDAR RESERVA
    public static void guardarReserva(Reserva r)
            throws SQLException {

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

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

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

    // GUARDAR OCUPACION
    public static void guardarOcupacion(Estadia o)
            throws SQLException {

        String sql = """
            INSERT INTO ocupaciones
            (dni_pasajero, numero_habitacion,
             fecha_check_in, fecha_check_out)
            VALUES (?, ?, ?, ?)

            ON DUPLICATE KEY UPDATE
                fecha_check_out = VALUES(fecha_check_out)
            """;

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, o.getPasajero().getDni());

            ps.setInt(2, o.getHabitacion().getNumero());

            ps.setDate(3,
                    Date.valueOf(o.getFechaCheckIn()));

            if (o.getFechaCheckOut() == null) {
                ps.setNull(4, Types.DATE);
            } else {
                ps.setDate(4,
                        Date.valueOf(o.getFechaCheckOut()));
            }

            ps.executeUpdate();
        }
    }

    // GUARDAR ADMINISTRADOR
    public static void guardarAdministrador(Administrador a) throws SQLException{
        String sql = """
        INSERT INTO administradores 
        (id, nombre, usuario, contrasenia)
        VALUES (?, ?, ?, ?)
        
        ON DUPLICATE KEY UPDATE
            nombre = VALUES(nombre),
            usuario = VALUES(usuario),
            contrasenia = VALUES(contrasenia)
        """;

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, a.getId());

            ps.setString(2, a.getNombre());

            ps.setString(3, a.getUsuario());

            ps.setString(4, a.getContrasenia());

            ps.executeUpdate();
        }
    }

    // GUARDAR RECEPCIONISTA
    public static void guardarRecepcionista(Recepcionista r) throws SQLException{
        String sql = """
        INSERT INTO recepcionistas 
        (id, nombre, usuario, contrasenia)
        VALUES (?, ?, ?, ?)
        
        ON DUPLICATE KEY UPDATE
            nombre = VALUES(nombre),
            usuario = VALUES(usuario),
            contrasenia = VALUES(contrasenia)
        """;

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, r.getId());

            ps.setString(2, r.getNombre());

            ps.setString(3, r.getUsuario());

            ps.setString(4, r.getContrasenia());

            ps.executeUpdate();
        }
    }

    // CARGAR TODOS LOS DATOS
    public static void cargarDatos(Hotel hotel)
            throws SQLException {

        cargarPasajeros(hotel);
        cargarHabitaciones(hotel);
        cargarReservas(hotel);
        cargarOcupaciones(hotel);
        cargarAdministradores(hotel);
        cargarRecepcionistas(hotel);
    }

    //CARGAR PASAJEROS
    private static void cargarPasajeros(Hotel hotel)
            throws SQLException {

        String sql = "SELECT * FROM pasajeros";

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pasajero p = new Pasajero(
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("origen"),
                        rs.getString("domicilio_origen"),
                        hotel
                );

                hotel.registrarPasajeroHotel(p);
            }
        }
    }


    // CARGAR HABITACIONES
    private static void cargarHabitaciones(Hotel hotel)
            throws SQLException {

        String sql = "SELECT * FROM habitaciones";

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
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

    // CARGAR RESERVAS
    private static void cargarReservas(Hotel hotel)
            throws SQLException {

        String sql = "SELECT * FROM reservas ORDER BY id";

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
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

    // CARGAR OCUPACIONES
    private static void cargarOcupaciones(Hotel hotel)
            throws SQLException {

        String sql = """
            SELECT * FROM ocupaciones
            ORDER BY fecha_check_in
            """;

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
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

    private static void cargarAdministradores(Hotel hotel)
            throws SQLException{
        String sql = "SELECT * FROM administradores ORDER BY id";
        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()){
                int id = rs.getInt("id");

                String nombre = rs.getString("nombre");

                String usuario = rs.getString("usuario");

                String contrasenia = rs.getString("contrasenia");

                Administrador a = new Administrador(id, usuario, contrasenia, nombre, hotel);

                hotel.agregarAdministrador(a);
            }
        }
    }

    private static void cargarRecepcionistas(Hotel hotel)
            throws SQLException{
        String sql = "SELECT * FROM recepcionistas ORDER BY id";
        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()){
                int id = rs.getInt("id");

                String nombre = rs.getString("nombre");

                String usuario = rs.getString("usuario");

                String contrasenia = rs.getString("contrasenia");

                Recepcionista r = new Recepcionista(id, usuario, contrasenia, nombre, hotel);

                hotel.agregarRecepcionista(r);
            }
        }
    }

    // ELIMINACION
    public static void eliminarHabitacion(int numero) throws SQLException {
        String sql = "DELETE FROM habitaciones WHERE numero = ?";
        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, numero);
            ps.executeUpdate();
        }
    }

    public static void eliminarReserva(int id) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id = ?";
        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static void eliminarRecepcionista(int id) throws SQLException {
        String sql = "DELETE FROM recepcionistas WHERE id = ?";
        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

