package clases;
import Excepciones.ExcepcionPropia;
import Enum.EEstadoReserva;



import java.time.LocalDate;
import java.util.HashMap;

public final class Hotel {

    private String nombre;

    private HashMap<Integer, Habitacion> habitaciones;
    private Repositorio<Reserva> reservas;
    private Repositorio<Ocupacion> ocupaciones;
    private RepoMap<String, Pasajero> pasajeros;
    private RepoMap<Integer, Administrador> administradores;
    private RepoMap<Integer, Recepcionista> recepcionistas;

    public Hotel(String nombre) {
        this.nombre = nombre;

        this.habitaciones = new HashMap<>();
        this.reservas = new Repositorio<>();
        this.ocupaciones = new Repositorio<>();
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

    public Repositorio<Reserva> getReservas() {
        return reservas;
    }

    public Repositorio<Ocupacion> getOcupaciones() {
        return ocupaciones;
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

    public void setReservas(Repositorio<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void setOcupaciones(Repositorio<Ocupacion> ocupaciones) {
        this.ocupaciones = ocupaciones;
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
    public boolean registrarPasajeroHotel(Pasajero p) {
        if (p.getDni() == null || pasajeros.contiene(p.getDni())) {
            return false;
        }

        pasajeros.agregar(p.getDni(), p);
        return true;
    }

    public Habitacion obtenerHabitacionActual(Pasajero pasajero) {

        if (pasajero == null) {
            return null;
        }

        for (Ocupacion ocupacion : ocupaciones) {

            if (ocupacion.getPasajero().equals(pasajero)
                    && ocupacion.getFechaCheckOut() == null) {

                return ocupacion.getHabitacion();
            }
        }

        return null;
    }

    public void agregarOcupacion(Ocupacion ocupacion){
        ocupaciones.agregar(ocupacion);
    }

    ///ADMINISTRADORES

    public boolean agregarAdministrador(Administrador nuevoAdministrador) {

        if (nuevoAdministrador == null || administradores.contiene(nuevoAdministrador.getId())) {
            return false;
        }

        administradores.agregar(nuevoAdministrador.getId(), nuevoAdministrador);
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

    public boolean realizarCheckInHotel(Reserva reserva) throws ExcepcionPropia {

        if (reserva == null) {
            throw new ExcepcionPropia("reseva no encontrada");
        }
        if (reserva.getEstado() != EEstadoReserva.CONFIRMADA) {
            throw new ExcepcionPropia("reserva no confirmada");
        }

        Habitacion habitacion = reserva.getHabitacion();

        if (habitacion == null) {
            throw new ExcepcionPropia("La reserva no tiene habitación");
        }

        if (!habitacion.estaDisponible()) {
            throw new ExcepcionPropia("habitacion no disponible");
        }


        for (Ocupacion ocupacion : ocupaciones) {
            if (ocupacion.estaActiva()
                    && ocupacion.getHabitacion().getNumero()
                    == habitacion.getNumero()) {
                throw new ExcepcionPropia("la habitacion ya esta ocupada");
            }
        }
        Ocupacion nuevaOcupacion = new Ocupacion(
                reserva.getPasajero(),
                habitacion,
                LocalDate.now(),
                LocalDate.now()
        );

        ocupaciones.agregar(nuevaOcupacion);
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
            System.out.println("clases.Habitacion existente");
        } else {
            habitaciones.put(habitacion.getNumero(), habitacion);
            System.out.println("clases.Habitacion agregada");
        }
    }

    public boolean eliminarHabitacion(int numeroHabitacion) {
        if (habitaciones.containsKey(numeroHabitacion)) {
            habitaciones.remove(numeroHabitacion);
            System.out.println("clases.Habitacion eliminada.");
            return true;
        } else {
            System.out.println("clases.Habitacion no existente");
            return false;
        }
    }

    public Habitacion buscarHabitacion(int numero) {
        if (habitaciones.containsKey(numero)) {
            return habitaciones.get(numero);
        } else {
            System.out.println("clases.Habitacion no encontrada");
            return null;
        }
    }

    public void bloquearHabitacion(int numero, String motivo) {
        Habitacion habitacion = buscarHabitacion(numero);
        if (habitacion == null) {
            System.out.println("clases.Habitacion no encontrada");
        } else if (motivo == null || motivo.isBlank()) {
            System.out.println("Debe haber un motivo.");
        } else {
            habitacion.bloquear(motivo);
            System.out.println("clases.Habitacion bloqueada");
        }
    }

    public void habilitarHabitacion(int numero) {
        Habitacion habitacion = buscarHabitacion(numero);
        if (habitacion == null) {
            System.out.println("clases.Habitacion no encontrada");
        } else {
            habitacion.habilitar();
            System.out.println("clases.Habitacion habilitada");
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
                System.out.println("clases.Habitacion: " +
                        ocupacion.getHabitacion().getNumero());
                System.out.println("clases.Pasajero: " +
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
            System.out.println("clases.Habitacion no encontrada");
            return false;
        }
        if (fechaEntrada == null || fechaSalida == null || !fechaEntrada.isBefore(fechaSalida)) {
            System.out.println("Fecha Invalida");
            return false;
        }
        if (!habitacion.estaDisponible()) {
            System.out.println("clases.Habitacion no disponible");
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