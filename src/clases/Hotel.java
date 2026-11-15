package clases;

import ConexioSQL.HotelDAO;
import Excepciones.*;
import Enum.EEstadoReserva;
import Enum.EEstadoHabitacion;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public final class Hotel {

    private String nombre;
    Scanner sc = new Scanner(System.in);

    private HashMap<Integer, Habitacion> habitaciones;
    private RepoArr<Reserva> reservas;
    private RepoArr<Estadia> estadias;
    private RepoMap<String, Pasajero> pasajeros;
    private RepoMap<Integer, Administrador> administradores;
    private RepoMap<Integer, Recepcionista> recepcionistas;

    public Hotel(String nombre) {
        this.nombre = nombre;

        this.habitaciones = new HashMap<>();
        this.reservas = new RepoArr<>();
        this.estadias = new RepoArr<>();
        this.administradores = new RepoMap<>();
        this.recepcionistas = new RepoMap<>();
        this.pasajeros = new RepoMap<>();
    }

    //getters

    public String getNombre() {
        return nombre;
    }

    public HashMap<Integer, Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public RepoArr<Reserva> getReservas() {
        return reservas;
    }

    public RepoArr<Estadia> getEstadias() {
        return estadias;
    }

    public RepoMap<String, Pasajero> getPasajeros() {
        return pasajeros;
    }

    public RepoMap<Integer, Administrador> getAdministradores() {
        return administradores;
    }

    public RepoMap<Integer, Recepcionista> getRecepcionistas() {
        return recepcionistas;
    }

    //setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setHabitaciones(HashMap<Integer, Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public void setReservas(RepoArr<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void setEstadias(RepoArr<Estadia> estadias) {
        this.estadias = estadias;
    }

    public void setPasajeros(RepoMap<String, Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public void setAdministradores(RepoMap<Integer, Administrador> administradores) {
        this.administradores = administradores;
    }

    public void setRecepcionistas(RepoMap<Integer, Recepcionista> recepcionistas) {
        this.recepcionistas = recepcionistas;
    }

    /// PASAJEROS

    public Pasajero crearPasajero() {
        System.out.println("Introduzca su DNI: ");
        String dni = sc.nextLine();

        if (pasajeros.contiene(dni)) {
            System.out.println(
                    "Error, ya hay un pasajero con ese DNI");

        } else {

            System.out.println("Introduzca su nombre: ");
            String nombre = sc.nextLine();

            System.out.println("Introduzca ciudad de origen: ");
            String ciudad = sc.nextLine();

            System.out.println("Introduzca su domicilio: ");
            String domicilio = sc.nextLine();

            Pasajero p = new Pasajero(
                    nombre, dni, ciudad, domicilio, this);

            return p;
        }

        return null;
    }

    public boolean registrarPasajeroHotel(Pasajero p) throws SQLException {
        if (p.getDni() == null || pasajeros.contiene(p.getDni())) {
            return false;
        }

        HotelDAO.guardarPasajero(p);
        pasajeros.agregar(p.getDni(), p);
        return true;
    }

    public void eliminarReserva() {
        IO.println("Ingrese el DNI:");
        String dni = sc.nextLine();

        IO.println(mostrarReservasPorPasajero(dni));

        IO.println("Ingrese la reserva que desea eliminar:");
        int opcion = Integer.parseInt(sc.nextLine());

        int contador = 1;
        for (Reserva r : reservas) {
            if (r.getPasajero().getDni().equalsIgnoreCase(dni)) {
                if (contador == opcion) {
                    if (eliminarReservaCompleta(r)) {
                        IO.println("Reserva eliminada correctamente");
                    } else {
                        IO.println("No se pudo eliminar la reserva");
                    }
                    return;
                }
                contador++;
            }
        }

        IO.println("No se encontró la reserva");
    }

    public boolean eliminarReservaCompleta(Reserva r) {

        if (r == null) {
            return false;
        }

        // Primero eliminamos de SQL
        if (!eliminarReservaSQL(r.getId())) {
            return false;
        }

        // Si SQL funcionó, eliminamos de memoria
        reservas.eliminar(r);

        return true;
    }

    public Habitacion obtenerHabitacionActualDelPasajero(String dni) {

        if (dni == null) {
            return null;
        }

        for (Estadia estadia : estadias) {

            if (estadia.getPasajero().getDni().equals(dni)
                    && estadia.getFechaCheckOut() == null) {

                return estadia.getHabitacion();
            }
        }

        return null;
    }


    public Estadia obtenerEstadiaActual(String dni) {
        if (dni == null) {
            return null;
        }

        for (Estadia estadia : estadias) {
            if (estadia.getPasajero().getDni().equals(dni)
                    && estadia.getFechaCheckOut() == null) {
                return estadia;
            }
        }

        return null;
    }

    public void agregarEstadia(Estadia estadia) {
        estadias.agregar(estadia);
    }

    public String mostrarReservasPorPasajero(String dni) {
        StringBuilder mensaje = new StringBuilder();
        int contador = 1;

        for (Reserva r : reservas) {
            if (r.getPasajero().getDni().equalsIgnoreCase(dni)) {
                mensaje.append(contador++)
                        .append("- ")
                        .append(r)
                        .append("\n");
            }
        }

        return mensaje.toString();
    }

    /// ADMINISTRADORES

    public boolean agregarAdministrador(Administrador nuevoAdministrador) {

        if (nuevoAdministrador == null || administradores.contiene(nuevoAdministrador.getId())) {
            return false;
        }

        if (agregarAdministradorSQL(nuevoAdministrador)) {
            administradores.agregar(nuevoAdministrador.getId(), nuevoAdministrador);

            System.out.println("Administrador guardado en HashMap con ID: " + nuevoAdministrador.getId());

            return true;
        }

        return false;
    }

    public boolean agregarRecepcionista(Recepcionista nuevoRecepcionista) {
        if (nuevoRecepcionista == null || recepcionistas.contiene(nuevoRecepcionista.getId())) {
            return false;
        }

        if (agregarRecepcionistaSQL(nuevoRecepcionista)) {
            recepcionistas.agregar(nuevoRecepcionista.getId(), nuevoRecepcionista);

            System.out.println("Recepcionista guardado en HashMap con ID: " + nuevoRecepcionista.getId());

            return true;
        }

        return false;
    }


    public void eliminarRecepcionista(Integer id) throws IDNoEncontradoException {
        if (!recepcionistas.contiene(id)) {
            throw new IDNoEncontradoException(Recepcionista.class, id);
        }

        if (eliminarRecepcionistaSQL(id)) {
            recepcionistas.eliminar(id);
            System.out.println("Recepcionista eliminado correctamente");
        } else {
            System.out.println("No se pudo eliminar el recepcionista");
        }
    }

    /// RECEPCIONISTAS
    public Pasajero buscarPasajero(String dni) throws PasajeroNoEncontradoException {
        if (!pasajeros.contiene(dni)) {
            throw new PasajeroNoEncontradoException(dni);
        }
        return pasajeros.obtener(dni);
    }

    public void crearReserva(Pasajero pasajero) throws FechaInvalidaException, SQLException {

        System.out.println("Introduzca el año de la estadía:");
        int anio = Integer.parseInt(sc.nextLine());
        if (anio < LocalDate.now().getYear()) {
            throw new FechaInvalidaException("El año es invalido");
        }

        System.out.println("Introduzca el mes:");
        int mes = Integer.parseInt(sc.nextLine());
        if (mes < 1 || mes > 12) {
            throw new FechaInvalidaException("El mes es invalido");
        }

        System.out.println("Introduzca el dia:");
        int dia = Integer.parseInt(sc.nextLine());
        if (dia < 1 || dia > LocalDate.of(anio, mes, 1).lengthOfMonth()) {
            throw new FechaInvalidaException("El dia es invalido");
        }

        System.out.println("Introduzca el mes de salida:");
        int mesfin = Integer.parseInt(sc.nextLine());
        if (mesfin < 1 || mesfin > 12) {
            throw new FechaInvalidaException("El mes de salida es invalido");
        }

        System.out.println("Introduzca el dia de salida:");
        int diafin = Integer.parseInt(sc.nextLine());

        int aniofin;

        if (mesfin < mes || (mesfin == mes && diafin < dia)) {
            aniofin = anio + 1;
        } else {
            aniofin = anio;
        }

        if (diafin < 1 ||
                diafin > LocalDate.of(aniofin, mesfin, 1).lengthOfMonth()) {
            throw new FechaInvalidaException("El dia de salida es invalido");
        }

        LocalDate checkin = LocalDate.of(anio, mes, dia);
        LocalDate checkout = LocalDate.of(aniofin, mesfin, diafin);

        if (checkin.isBefore(LocalDate.now())) {
            throw new FechaInvalidaException(
                    "La fecha de entrada no puede ser anterior a hoy");
        }

        if (!checkout.isAfter(checkin)) {
            throw new FechaInvalidaException(
                    "La fecha de salida debe ser posterior a la entrada");
        }

        IO.println(mostrarHabitacionesEnFecha(checkin, checkout));

        System.out.println("Ingrese el numero de habitacion:");
        int nrohabitacion = Integer.parseInt(sc.nextLine());

        Habitacion habitacion = buscarHabitacion(nrohabitacion);

        Reserva reserva = new Reserva(pasajero, habitacion, checkin, checkout);

        if (agregarReservaCompleta(reserva)) {
            System.out.println("Reserva agregada correctamente");
        } else {
            System.out.println("No se pudo agregar la reserva");
        }
    }

    public String mostrarHabitacionesEnFecha(LocalDate entrada, LocalDate salida) {
        StringBuilder mensaje = new StringBuilder();
        int contador = 0;

        for (Habitacion h : habitaciones.values()) {

            if (consultarDisponibilidad(h.getNumero(), entrada, salida)) {
                mensaje.append("Numero: ").append(h.getNumero())
                        .append(" | Piso: ").append(h.getPiso())
                        .append(" | Tipo: ").append(h.getTipo())
                        .append("\n");

                contador++;
            }
        }

        if (contador == 0) {
            return "No hay habitaciones disponibles para esas fechas.";
        }

        return mensaje.toString();
    }

    public void listarReservas() throws ColeccionVaciaException {

        if (reservas.cantidad() == 0) {
            throw new ColeccionVaciaException(Reserva.class, " ");
        }

        System.out.println("\n===== LISTADO DE RESERVAS =====");

        for (int i = 0; i < reservas.cantidad(); i++) {

            Reserva r = reservas.obtener(i);

            System.out.println("ID: " + r.getId());
            System.out.println("Pasajero: " + r.getPasajero().getNombre());
            System.out.println("DNI: " + r.getPasajero().getDni());
            System.out.println("Habitación: " + r.getHabitacion().getNumero());
            System.out.println("Fecha de entrada: " + r.getFechaEntrada());
            System.out.println("Fecha de salida: " + r.getFechaSalida());
            System.out.println("Estado: " + r.getEstado());

            System.out.println("------------------------------");
        }
    }

    public boolean agregarReservaHotel(Reserva r) {
        if (r == null
                || reservas.contiene(r)
                || r.getHabitacion() == null
                || r.getPasajero() == null
                || r.getFechaEntrada() == null
                || r.getFechaSalida() == null) {
            return false;
        }

        if (!consultarDisponibilidad(
                r.getHabitacion().getNumero(),
                r.getFechaEntrada(),
                r.getFechaSalida())) {
            return false;
        }

        if (!pasajeros.contiene(r.getPasajero().getDni())) {
            pasajeros.agregar(r.getPasajero().getDni(), r.getPasajero());
        }

        reservas.agregar(r);
        return true;
    }

    public boolean agregarReservaCompleta(Reserva r) {

        if (r == null) {
            return false;
        }
        if (!agregarReservaHotel(r)) {
            return false;
        }
        try {
            HotelDAO.guardarReservaCompleta(r.getPasajero(), r);
            // Si el pasajero era nuevo, lo agregamos a memoria.
            if (!pasajeros.contiene(r.getPasajero().getDni())) {
                pasajeros.agregar(r.getPasajero().getDni(), r.getPasajero());
            }
            return true;
        } catch (SQLException e) {
            // Si falla SQL, quitamos la reserva que habíamos agregado
            // previamente a la colección en memoria.
            reservas.eliminar(r);
            System.out.println("Error al guardar reserva: " + e.getMessage());
            return false;
        }
    }

    public void realizarCheckInHotel(Reserva reserva) throws FechaInvalidaException {

        if (reserva.getEstado() != EEstadoReserva.CONFIRMADA) {
            throw new FechaInvalidaException(
                    "La reserva no está confirmada");
        }
        if (reserva.getFechaEntrada().isAfter(LocalDate.now())
                || reserva.getFechaSalida().isBefore(LocalDate.now())) {

            throw new FechaInvalidaException(
                    "Aún no es la fecha para hacer el CheckIn");
        }

        Habitacion habitacion = reserva.getHabitacion();
        Estadia nuevaEstadia = new Estadia(
                reserva.getPasajero(),
                habitacion,
                LocalDate.now(),
                null,
                reserva.getId()
        );

        System.out.println("ID Reserva: " + reserva.getId());
        System.out.println("ID Estadia: " + nuevaEstadia.getId());
        System.out.println("ID Reserva guardado en Estadia: " + nuevaEstadia.getIdReserva());

        // Guardamos la estadía y actualizamos la habitación en SQL
        if (agregarEstadiaSQL(nuevaEstadia)) {
            // Si SQL funcionó, actualizamos memoria
            reserva.getPasajero().agregarEstadia(nuevaEstadia);
            habitacion.ocupar();
            System.out.println("Check-in realizado correctamente");
        } else {
            System.out.println("No se pudo realizar el Check-in");
        }
    }

    public void realizarCheckOutHotel(Estadia estadia)
            throws FechaInvalidaException {

        if (estadia == null || estadia.getFechaCheckOut() != null) {
            throw new FechaInvalidaException(
                    "La estadía no existe o ya finalizó");
        }

        LocalDate fechaSalida = LocalDate.now();

        if (fechaSalida.isBefore(estadia.getFechaCheckIn())) {
            throw new FechaInvalidaException(
                    "La fecha de salida no puede ser anterior al check-in");
        }

        if (actualizarCheckOutSQL(estadia, fechaSalida)) {

            estadia.realizarCheckOut(fechaSalida);
            estadia.getHabitacion().habilitar();

            // Eliminamos la reserva de SQL
            if (eliminarReservaSQL(estadia.getIdReserva())) {
                // Eliminamos la reserva de memoria
                try {
                    Reserva reserva = buscarReserva(estadia.getIdReserva());
                    reservas.eliminar(reserva);
                } catch (IDNoEncontradoException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("Check-out realizado correctamente");
            } else {
                System.out.println("Check-out realizado, pero no se pudo eliminar la reserva");
            }

        } else {
            System.out.println("No se pudo realizar el check-out");
        }
    }

    public String buscarEstadiaPasajero(String dni) throws ColeccionVaciaException, PasajeroNoEncontradoException {
        boolean b = false;
        StringBuilder sb = new StringBuilder();
        Pasajero p = buscarPasajero(dni);
        for (Estadia estadia : p.getHistorial()) {
            sb.append(estadia);
            b = true;
        }
        if (!b) {
            throw new ColeccionVaciaException(Estadia.class, " realizada por el pasajero con dni " + dni);
        }
        return sb.toString();
    }

    public Reserva buscarReserva(int id) throws IDNoEncontradoException {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return r;
            }
        }

        throw new IDNoEncontradoException(Reserva.class, id);
    }

    public Reserva buscarReservaPasajero(String dni) {
        for (Reserva x : reservas) {
            if (x.getPasajero().getDni().equals(dni)) {
                return x;
            }
        }
        return null;
    }

    public Estadia buscarEstadiaPorId(int id)
            throws IDNoEncontradoException {

        System.out.println("Buscando estadía con ID: " + id);
        System.out.println("Cantidad de estadías: " + estadias.cantidad());

        for (Estadia estadia : estadias) {
            System.out.println("Estadía encontrada en colección:");
            System.out.println("ID: " + estadia.getId());
            System.out.println("Pasajero: " + estadia.getPasajero().getNombre());
        }

        for (Estadia estadia : estadias) {
            if (estadia.getId() == id) {
                return estadia;
            }
        }

        throw new IDNoEncontradoException(Estadia.class, id);
    }

    /// HABITACIONES
    public void agregarHabitacion(Habitacion habitacion) {

        if (habitacion == null) {
            System.out.println("La habitación es inválida");
            return;
        }

        if (habitaciones.containsKey(habitacion.getNumero())) {
            System.out.println("Habitación existente");
            return;
        }

        if (agregarHabitacionSQL(habitacion)) {
            habitaciones.put(habitacion.getNumero(), habitacion);
            System.out.println("Habitación agregada al hotel");
        }
    }

    public boolean eliminarHabitacion(int numero) {
        if (!habitaciones.containsKey(numero)) {
            System.out.println("Habitacion no encontrada");
            return false;
        }

        if (eliminarHabitacionSQL(numero)) {
            habitaciones.remove(numero);
            System.out.println("Habitacion eliminada correctamente");
            return true;
        }

        System.out.println("No se pudo eliminar la habitacion");
        return false;
    }

    public Habitacion buscarHabitacion(int numero) {
        if (habitaciones.containsKey(numero)) {
            return habitaciones.get(numero);
        } else {
            System.out.println("Habitacion no encontrada");
            return null;
        }
    }

    public void bloquearHabitacion(int numero, String motivo) {
        Habitacion habitacion = buscarHabitacion(numero);
        if (habitacion == null) {
            System.out.println("Habitacion no encontrada");
        } else if (motivo == null || motivo.isBlank()) {
            System.out.println("Debe haber un motivo.");
        } else {
            habitacion.bloquear(motivo);
            System.out.println("Habitacion bloqueada");
        }
    }

    public void habilitarHabitacion(int numero) {
        Habitacion habitacion = buscarHabitacion(numero);
        if (habitacion == null) {
            System.out.println("Habitacion no encontrada");
        } else {
            habitacion.habilitar();
            System.out.println("Habitacion habilitada");
        }
    }

    public String listarHabitacionesDisponibles() throws ColeccionVaciaException {
        String resultado = "Habitaciones disponibles:\n";
        boolean hayHabitaciones = false;
        for (Habitacion x : habitaciones.values()) {
            if (x.estaDisponible()) {
                resultado += x.mostrarInformacion() + "\n";
                hayHabitaciones = true;
            }
        }
        if (!hayHabitaciones) {
            throw new ColeccionVaciaException(
                    Habitacion.class, " que esté disponible");
        }
        return resultado;
    }

    public String listarHabitacionesOcupadas()
            throws ColeccionVaciaException {
        StringBuilder sb = new StringBuilder();
        sb.append("habitaciones no disponibles: \n");
        boolean b = false;
        for (Estadia estadia : estadias) {
            if (estadia.estaActiva()) {
                sb.append(estadia.getHabitacion().mostrarInformacion());
                b = true;
            }
        }
        if (!b) {
            throw new ColeccionVaciaException(Habitacion.class, " que este ocupada");
        }
        return sb.toString();
    }

    public String listarHabitacionesNoDisponibles()
            throws ColeccionVaciaException {
        StringBuilder sb = new StringBuilder();
        sb.append("habitaciones no disponibles: \n");
        boolean b = false;
        for (Habitacion x : habitaciones.values()) {
            if (x.getEstado() == EEstadoHabitacion.NO_DISPONIBLE) {
                sb.append(x.mostrarInformacion());
                b = true;
            }
        }
        if (!b) {
            throw new ColeccionVaciaException(Habitacion.class, " que no este disponible");
        }
        return sb.toString();
    }

    public void listarHabitaciones() throws ColeccionVaciaException {

        if (habitaciones.isEmpty()) {
            throw new ColeccionVaciaException(Habitacion.class, " ");
        }

        System.out.println("\nListado de habitaciones:");
        for (Habitacion habitacion : habitaciones.values()) {
            System.out.println(habitacion.mostrarInformacion());
            System.out.println("--------------------");
        }
    }

    public boolean consultarDisponibilidad(int numero, LocalDate fechaEntrada, LocalDate fechaSalida) {
        Habitacion habitacion = buscarHabitacion(numero);
        if (habitacion == null) {
            System.out.println("Habitacion no encontrada");
            return false;
        }
        if (fechaEntrada == null || fechaSalida == null || !fechaEntrada.isBefore(fechaSalida)) {
            System.out.println("Fecha Invalida");
            return false;
        }
        if (habitacion.getEstado() == EEstadoHabitacion.NO_DISPONIBLE) {
            System.out.println("Habitacion no disponible");
            return false;
        }
        for (Reserva reserva : reservas) {
            if (reserva.getHabitacion().getNumero() == numero && reserva.getEstado() == EEstadoReserva.CONFIRMADA) {
                boolean seSuperponen =
                        fechaEntrada.isBefore(reserva.getFechaSalida())
                                && fechaSalida.isAfter(reserva.getFechaEntrada());
                if (seSuperponen) {
                    System.out.println("La habitacion ya está reservada en esas fechas");
                    return false;
                }
            }
        }
        System.out.println("Habitacion disponible en esas fechas");
        return true;
    }

    /// PERSISTENCIA SQL
    // HABITACION
    public boolean agregarHabitacionSQL(Habitacion habitacion) {
        try {
            HotelDAO.guardarHabitacion(habitacion);
            System.out.println("Habitación agregada correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar la habitación: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarHabitacionSQL(int numeroHabitacion) {
        try {
            HotelDAO.eliminarHabitacion(numeroHabitacion);
            System.out.println("Habitación eliminada.");
            return true;
        } catch (SQLException e) {
            System.out.println("No se pudo eliminar la habitación: " + e.getMessage());
            return false;
        }
    }

    // RESERVA
    public boolean agregarReservaSQL(Reserva r) {
        try {
            HotelDAO.guardarReserva(r);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar reserva: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarReservaSQL(int id) {
        try {
            HotelDAO.eliminarReserva(id);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar reserva: " + e.getMessage());
            return false;
        }
    }

    // ESTADIA
    public boolean agregarEstadiaSQL(Estadia estadia) {
        try {
            HotelDAO.guardarEstadiaYActualizarHabitacion(estadia);
            estadias.agregar(estadia);
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar ocupación: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCheckOutSQL(Estadia estadia, LocalDate fechaSalida) {
        try {
            HotelDAO.actualizarCheckOut(estadia, fechaSalida);
            return true;
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            return false;
        }
    }

    // PASAJERO
    public boolean agregarPasajeroSQL(Pasajero p) {
        try {
            HotelDAO.guardarPasajero(p);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar pasajero: " + e.getMessage());
            return false;
        }
    }

    // USUARIO
    public boolean agregarAdministradorSQL(Administrador a) {
        try {
            HotelDAO.guardarAdministrador(a);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean agregarRecepcionistaSQL(Recepcionista r) {
        try {
            HotelDAO.guardarRecepcionista(r);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarRecepcionistaSQL(int id) {
        try {
            HotelDAO.eliminarRecepcionista(id);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /// USUARIO
    public Usuario buscarUsuario(String usuario, String contrasenia) {
        for (Administrador a : administradores.getPersonas().values()) {
            if (a.getUsuario().equals(usuario) &&
                    a.getContrasenia().equals(contrasenia)) {
                return a;
            }
        }

        for (Recepcionista r : recepcionistas.getPersonas().values()) {
            if (r.getUsuario().equals(usuario) &&
                    r.getContrasenia().equals(contrasenia)) {
                return r;
            }
        }
        return null;
    }
}