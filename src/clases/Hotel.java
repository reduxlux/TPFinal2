package clases;
import Excepciones.FechaInvalidaException;
import Enum.EEstadoReserva;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
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
    public boolean registrarPasajeroHotel(Pasajero p) {
        if (p.getDni() == null || pasajeros.contiene(p.getDni())) {
            return false;
        }

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
                    reservas.eliminar(r);
                    IO.println("Reserva eliminada correctamente");
                    return;
                }
                contador++;
            }
        }

        IO.println("No se encontró la reserva");
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

        administradores.agregar(nuevoAdministrador.getId(), nuevoAdministrador);
        return true;
    }

    public boolean agregarRecepcionista(Recepcionista nuevoRecepcionista) {
        if (nuevoRecepcionista == null || recepcionistas.contiene(nuevoRecepcionista.getId())) {
            return false;
        }
        recepcionistas.agregar(nuevoRecepcionista.getId(), nuevoRecepcionista);
        return true;
    }


    public boolean eliminarRecepcionista(Integer id) {
        if (id == null || !recepcionistas.contiene(id)) {
            return false;
        }
        recepcionistas.eliminar(id);
        return true;
    }

    /// RECEPCiONISTAS
    public Pasajero buscarPasajero(String dni){
        return pasajeros.obtener(dni);
    }

    public void crearReserva(Pasajero pasajero) throws FechaInvalidaException{
        System.out.println("Introduzca el año de la estadía: ");
        int anio = sc.nextInt();
        if (anio < LocalDateTime.now().getYear()) {
            throw new FechaInvalidaException("El año es invalido");
        }
        System.out.println("Introduzca el mes: ");
        int mes = sc.nextInt();
        if (mes < 1 || mes > 12) {
            throw new FechaInvalidaException("El mes es invalido");
        }
        System.out.println("introduzca el día: ");
        int dia = sc.nextInt();
        if (dia < 0 || dia > (LocalDate.of(anio, mes, 1)).
                lengthOfMonth()) {
            throw new FechaInvalidaException("el dia es invalido");
        }
        System.out.println("introduzca el mes en el que finaliza la estadia: ");
        int mesfin = sc.nextInt();
        if (mesfin < 1 || mesfin > 12) {
            throw new FechaInvalidaException("El mes es invalido");
        }
        System.out.println("introduzca el dia en el que finaliza su estadia: ");
        int diafin = sc.nextInt();
        int aniofin;
        if (mesfin < mes) {
            aniofin = anio + 1;
        } else {
            aniofin = anio;
        }
        if (dia < 0 || dia > (LocalDate.of(anio, mes, 1)).
                lengthOfMonth()) {
            throw new FechaInvalidaException("el dia es invalido");
        }

        LocalDate checkin = LocalDate.of(anio, mes, dia);
        LocalDate checkout = LocalDate.of(aniofin, mesfin, diafin);


        //despues hacer metodo para mostrarle las habitaciones disponibles en la fecha pedida

        int nrohabitacion = sc.nextInt();

        Habitacion habitacion = buscarHabitacion(nrohabitacion);

        Reserva reserva = new Reserva(pasajero, habitacion, checkin, checkout);

        agregarReservaHotel(reserva);

        sc.close();
    }


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

    public boolean realizarCheckInHotel(Reserva reserva) throws FechaInvalidaException {

        if (reserva == null) {
            throw new FechaInvalidaException("reseva no encontrada");
        }

        if (reserva.getEstado() != EEstadoReserva.CONFIRMADA) {
            throw new FechaInvalidaException("reserva no confirmada");
        }

        if(reserva.getPasajero() == null)
        {
            throw new FechaInvalidaException("La reserva no tiene pasajero");
        }

        Habitacion habitacion = reserva.getHabitacion();

        if (habitacion == null) {
            throw new FechaInvalidaException("La reserva no tiene habitación");
        }

        if (!habitacion.estaDisponible()) {
            throw new FechaInvalidaException("habitacion no disponible");
        }


        for (Estadia estadia : estadias) {
            if (estadia.estaActiva()
                    && estadia.getHabitacion().getNumero()
                    == habitacion.getNumero()) {
                throw new FechaInvalidaException("la habitacion ya esta ocupada");
            }
        }

        Estadia nuevaEstadia = new Estadia(
                reserva.getPasajero(),
                habitacion,
                LocalDate.now(),
                null
        );

        //guardamos la ocupacion al historial del pasajero
        if (!reserva.getPasajero().agregarEstadia(nuevaEstadia)) {
            throw new FechaInvalidaException("No se pudo registrar la estadía");
        }

        //agregamos la ocupacion al repositorio del hotel
        estadias.agregar(nuevaEstadia);

        habitacion.ocupar();

        return true;
    }

    public boolean realizarCheckOutHotel(Estadia estadia) throws FechaInvalidaException {

        if (estadia == null) {
            throw new FechaInvalidaException("La estadia no existe");
        }

        if (!estadia.estaActiva()) {
            throw new FechaInvalidaException("Esta estadia ya finalizo");
        }

        estadia.realizarCheckOut(LocalDate.now());

        estadia.getHabitacion().habilitar();

        return true;
    }

    public Estadia buscarEstadiaPasajero(String dni) {
        for(Estadia estadia : estadias) {
            if(estadia.getPasajero().getDni().equals(dni)) {
                return estadia;
            }
        }
        return null;
    }

    public Reserva buscarReserva(int id){
        for(Reserva x: reservas){
            if(x.getId() == id){
                return x;
            }
        }
        return null;
    }

    public Reserva buscarReservaPasajero(String dni) {
        for(Reserva x: reservas){
            if (x.getPasajero().getDni().equals(dni)) {
                return x;
            }
        }
        return null;
    }

    public Estadia buscarEstadiaPorId(int id)
    {
        for(Estadia estadia : estadias)
        {
            if(estadia.getId() == id)
            {
                return estadia;
            }
        }
        return null;
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
        for (Estadia estadia : estadias) {
            if (estadia.estaActiva()) {
                System.out.println("clases.Habitacion: " +
                        estadia.getHabitacion().getNumero());
                System.out.println("clases.Pasajero: " +
                        estadia.getPasajero().getNombre());
                System.out.println("Check-in: " + estadia.getFechaCheckIn());
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
        System.out.println("Habitacion disponible en esas fechas");
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