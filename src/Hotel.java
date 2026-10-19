import java.time.LocalDate;
import java.util.HashMap;

public class Hotel {

    private String nombre;

    private HashMap<Integer, Habitacion> habitaciones;
    private Repositorio<Pasajero> pasajeros;
    private Repositorio<Reserva> reservas;
    private Repositorio<Ocupacion> ocupaciones;
    private Repositorio<Usuario> usuarios;

    public Hotel(String nombre) {
        this.nombre = nombre;

        this.habitaciones = new HashMap<>();
        this.pasajeros = new Repositorio<>();
        this.reservas = new Repositorio<>();
        this.ocupaciones = new Repositorio<>();
        this.usuarios = new Repositorio<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<Integer, Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(HashMap<Integer, Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Repositorio<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(Repositorio<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Repositorio<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Repositorio<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Repositorio<Ocupacion> getOcupaciones() {
        return ocupaciones;
    }

    public void setOcupaciones(Repositorio<Ocupacion> ocupaciones) {
        this.ocupaciones = ocupaciones;
    }

    public Repositorio<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Repositorio<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /// PASAJEROS
    public boolean registrarPasajeroHotel(Pasajero p) {
        if (p == null || pasajeros.contiene(p)) {
            return false;
        }

        pasajeros.agregar(p);
        return true;
    }

    /// RESERVAS
    public boolean agregarReservaHotel(Reserva r) {
        if (r == null
                || reservas.contiene(r)
                || r.getHabitacion() == null
                || r.getPasajero() == null
                || r.getFechaEntrada() == null
                || r.getFechaSalida() == null)
        {
            return false;
        }

        if (!consultarDisponibilidad(
                r.getHabitacion().getNumero(),
                r.getFechaEntrada(),
                r.getFechaSalida())) {
            return false;
        }

        reservas.agregar(r);
        return true;
    }

    public boolean realizarCheckInHotel(Reserva reserva) throws CheckInException {

        if (reserva == null) {
            throw new CheckInException("reseva no encontrada");
        }
        if (reserva.getEstado() != EEstadoReserva.CONFIRMADA) {
            throw new CheckInException("reserva no confirmada");
        }

        Habitacion habitacion = reserva.getHabitacion();

        if (habitacion == null) {
            throw new CheckInException("La reserva no tiene habitación");
        }

        if (!habitacion.estaDisponible()) {
            throw new CheckInException("habitacion no disponible");
        }


        for (Ocupacion ocupacion : ocupaciones) {
            if (ocupacion.estaActiva()
                    && ocupacion.getHabitacion().getNumero()
                    == habitacion.getNumero()) {
                throw new CheckInException("la habitacion ya esta ocupada");
            }
        }
        Ocupacion nuevaOcupacion = new Ocupacion(
                reserva.getPasajero(),
                habitacion,
                LocalDate.now()
        );

        ocupaciones.agregar(nuevaOcupacion);
        System.out.println("Check-in realizado correctamente.");
        return true;
    }

    public void realizarCheckOutHotel(Ocupacion ocupacion) {

        if (ocupacion == null) {
            System.out.println("La ocupación no existe.");
            return;
        }

        if (!ocupacion.estaActiva()) {
            System.out.println("Esta ocupación ya finalizó.");
            return;
        }

        ocupacion.realizarCheckOut(LocalDate.now());

        System.out.println("Check-out realizado correctamente.");
    }

    /// HABITACIONES
    public void agregarHabitacion(Habitacion habitacion) {
        if (habitaciones.containsKey(habitacion.getNumero())) {
            System.out.println("Habitacion existente");
        } else {
            habitaciones.put(habitacion.getNumero(), habitacion);
            System.out.println("Habitacion agregada");
        }
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

    public void listarHabitacionesDisponibles() {
        System.out.println("\nHabitaciones disponibles:");
        for (Habitacion habitacion : habitaciones.values()) {
            if (habitacion.estaDisponible()) {
                habitacion.mostrarInformacion();
                System.out.println("---");
            }
        }
    }

    public void listarHabitacionesOcupadas() {
        System.out.println("\nHabitaciones ocupadas:");
        for (Ocupacion ocupacion : ocupaciones) {
            if (ocupacion.estaActiva()) {
                System.out.println("Habitacion: " +
                        ocupacion.getHabitacion().getNumero());
                System.out.println("Pasajero: " +
                        ocupacion.getPasajero().getNombre());
                System.out.println("Check-in: " + ocupacion.getFechaCheckIn());
                System.out.println("---");
            }
        }
    }

    public void listarHabitacionesNoDisponibles() {
        System.out.println("\nHabitaciones no disponibles:");
        for (Habitacion habitacion : habitaciones.values()) {
            if (!habitacion.estaDisponible()) {
                habitacion.mostrarInformacion();
                System.out.println("---");
            }
        }
    }

    public void listarHabitaciones() {
        System.out.println("\nListado habitaciones:");
        for (Habitacion habitacion : habitaciones.values()) {
            habitacion.mostrarInformacion();
            System.out.println("---");
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
        if (!habitacion.estaDisponible()) {
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
        return true;
    }
}