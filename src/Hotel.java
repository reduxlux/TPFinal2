import java.util.ArrayList;
import java.util.HashMap;

public class Hotel {

    private String nombre;

    private HashMap<Integer, Habitacion> habitaciones;
    private ArrayList<Pasajero> pasajeros;
    private ArrayList<Reserva> reservas;
    private ArrayList<Ocupacion> ocupaciones;
    private ArrayList<Usuario> usuarios;

    public Hotel(String nombre) {
        this.nombre = nombre;

        habitaciones = new HashMap<>();
        pasajeros = new ArrayList<>();
        reservas = new ArrayList<>();
        ocupaciones = new ArrayList<>();
        usuarios = new ArrayList<>();
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

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(ArrayList<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    public ArrayList<Ocupacion> getOcupaciones() {
        return ocupaciones;
    }

    public void setOcupaciones(ArrayList<Ocupacion> ocupaciones) {
        this.ocupaciones = ocupaciones;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void agregarHabitacion(Habitacion habitacion) {
        // No se que ponerle
    }

    public void registrarPasajero(Pasajero pasajero) {
        // No se que ponerle
    }

    public void realizarReserva(Reserva reserva) {
        // No se que ponerle
    }

    public void realizarCheckIn(Ocupacion ocupacion) {
        // No se que ponerle
    }

    public void realizarCheckOut(Ocupacion ocupacion) {
        // No se que ponerle
    }

    public void listarHabitacionesDisponibles() {
        // No se que ponerle
    }

    public void listarHabitacionesOcupadas() {
        // No se que ponerle
    }

    public void listarHabitacionesNoDisponibles() {
        // No se que ponerle
    }

    public boolean consultarDisponibilidad(/* fechas */) {
        return false;
    }
}