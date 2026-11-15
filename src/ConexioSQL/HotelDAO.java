package ConexioSQL;

import clases.*;
import Enum.EEstadoHabitacion;
import Enum.EEstadoReserva;
import Enum.ETipoHabitacion;

import java.sql.*;
import java.time.LocalDate;

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

    private static void guardarPasajero(Connection cn, Pasajero p)
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

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

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

    private static void guardarReserva(Connection cn, Reserva r)
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

        try (PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, r.getId());
            ps.setString(2, r.getPasajero().getDni());
            ps.setInt(3, r.getHabitacion().getNumero());
            ps.setDate(4, Date.valueOf(r.getFechaEntrada()));
            ps.setDate(5, Date.valueOf(r.getFechaSalida()));
            ps.setString(6, r.getEstado().name());

            ps.executeUpdate();
        }
    }

    public static void guardarReservaConPasajero(
            Pasajero p, Reserva r, boolean pasajeroNuevo) throws SQLException {

        String sqlPasajero = """
        INSERT INTO pasajeros
        (dni, nombre, origen, domicilio_origen)
        VALUES (?, ?, ?, ?)
        """;

        String sqlReserva = """
        INSERT INTO reservas
        (id, dni_pasajero, numero_habitacion,
         fecha_entrada, fecha_salida, estado)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection cn = ConexioBD.conectar()) {

            // Desactivamos el guardado automático
            cn.setAutoCommit(false);

            try {

                // 1. Guardamos el pasajero solamente si es nuevo
                if (pasajeroNuevo) {
                    try (PreparedStatement ps =
                                 cn.prepareStatement(sqlPasajero)) {

                        ps.setString(1, p.getDni());
                        ps.setString(2, p.getNombre());
                        ps.setString(3, p.getOrigen());
                        ps.setString(4, p.getDomicilioOrigen());

                        ps.executeUpdate();
                    }
                }

                // 2. Guardamos la reserva
                try (PreparedStatement ps =
                             cn.prepareStatement(sqlReserva)) {

                    ps.setInt(1, r.getId());
                    ps.setString(2, p.getDni());
                    ps.setInt(3, r.getHabitacion().getNumero());
                    ps.setDate(4, Date.valueOf(r.getFechaEntrada()));
                    ps.setDate(5, Date.valueOf(r.getFechaSalida()));
                    ps.setString(6, r.getEstado().name());

                    ps.executeUpdate();
                }

                // 3. Si salió bien, confirmamos los cambios
                cn.commit();

            } catch (SQLException e) {

                // Si algo falla, deshacemos todos los cambios
                cn.rollback();

                throw e;
            }
        }
    }

    public static void guardarReservaCompleta(Pasajero p, Reserva r)
            throws SQLException {

        try (Connection cn = ConexioBD.conectar()) {

            cn.setAutoCommit(false);

            try {
                guardarPasajero(cn, p);
                guardarReserva(cn, r);

                cn.commit();

            } catch (SQLException e) {
                cn.rollback();
                throw e;
            }
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

    public static void guardarOcupacionYActualizarHabitacion(Estadia o)
            throws SQLException {

        String sqlOcupacion = """
        INSERT INTO ocupaciones
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
                // 1. Guardamos la ocupación
                try (PreparedStatement ps =
                             cn.prepareStatement(sqlOcupacion)) {

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

        String sqlOcupacion = """
        UPDATE ocupaciones
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
                // 1. Actualizamos la fecha de salida de la ocupación
                try (PreparedStatement ps = cn.prepareStatement(sqlOcupacion)) {

                    ps.setDate(1, Date.valueOf(fechaSalida));
                    ps.setString(2, estadia.getPasajero().getDni());
                    ps.setInt(3, estadia.getHabitacion().getNumero());
                    ps.setDate(4, Date.valueOf(estadia.getFechaCheckIn()));

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "No se encontró una ocupación activa para actualizar");
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
            throws SQLException {

        String sql = "SELECT * FROM administradores ORDER BY id";

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
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

    private static void cargarRecepcionistas(Hotel hotel)
            throws SQLException {

        String sql = "SELECT * FROM recepcionistas ORDER BY id";

        try (Connection cn = ConexioBD.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
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

