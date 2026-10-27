package clases;
import Excepciones.ExcepcionPropia;
import Enum.EEstadoReserva;



import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public final class Hotel {

    private String nombre;

    private HashMap<Integer, Habitacion> habitaciones;
    private RepoArr<Reserva> reservas;
    private RepoArr<Ocupacion> ocupaciones;
    private RepoMap<String, Pasajero> pasajeros;
    private RepoMap<Integer, Administrador> administradores;
    private RepoMap<Integer, Recepcionista> recepcionistas;

    public Hotel(String nombre) {
        this.nombre = nombre;

        this.habitaciones = new HashMap<>();
        this.reservas = new RepoArr<>();
        this.ocupaciones = new RepoArr<>();
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

    public RepoArr<Ocupacion> getOcupaciones() {
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

    public void setReservas(RepoArr<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void setOcupaciones(RepoArr<Ocupacion> ocupaciones) {
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

    public void eliminarEstadia(){
        int x = 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("que estadia desea eliminar: 1: cancelar\n");
        for(Reserva reserva: reservas){
            if(reserva.getFechaEntrada().isAfter(LocalDate.now())) {
                System.out.println(x++ + ": " + reserva.toString());
            }
        }

        int eleccion = sc.nextInt();
        int contador = 1;
        if(eleccion > 1){
            Iterator<Reserva> it = reservas.iterator();
            while (it.hasNext()){
                if(it.next().getFechaEntrada().isAfter(LocalDate.now())){
                    contador++;
                    if(contador == x){
                        it.remove();
                        System.out.println("estadia eliminada correctamente\n");
                        break;
                    }
                }
            }
        }
    }

    public Habitacion obtenerHabitacionActual(String dni) {

        if (dni == null) {
            return null;
        }

        for (Ocupacion ocupacion : ocupaciones) {

            if (ocupacion.getPasajero().getDni().equals(dni)
                    && ocupacion.getFechaCheckOut() == null) {

                return ocupacion.getHabitacion();
            }
        }

        return null;
    }



    public Ocupacion obtenerOcupacionActual(String dni){
        if(dni == null){
            return null;
        }

        for (Ocupacion ocupacion: ocupaciones){
            if(ocupacion.getPasajero().getDni().equals(dni)
                    && ocupacion.getFechaCheckOut() == null){
                return ocupacion;
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


    public boolean eliminarRecepcionista(Integer id){
        if(id == null || !recepcionistas.contiene(id)){
            return false;
        }
        recepcionistas.eliminar(id);
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

        if(reserva.getPasajero() == null)
        {
            throw new ExcepcionPropia("La reserva no tiene pasajero");
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
                null
        );

        //guardamos la ocupacion al historial del pasajero
        if (!reserva.getPasajero().agregarEstadia(nuevaOcupacion)) {
            throw new ExcepcionPropia("No se pudo registrar la estadía");
        }

        //agregamos la ocupacion al repositorio del hotel
        ocupaciones.agregar(nuevaOcupacion);

        habitacion.ocupar();

        return true;
    }

    public boolean realizarCheckOutHotel(Ocupacion ocupacion) throws ExcepcionPropia{

        if (ocupacion == null) {
            throw new ExcepcionPropia("La ocupacion no existe");
        }

        if (!ocupacion.estaActiva()) {
            throw new ExcepcionPropia("Esta ocupacion ya finalizo");
        }

        ocupacion.realizarCheckOut(LocalDate.now());

        ocupacion.getHabitacion().habilitar();

        return true;
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