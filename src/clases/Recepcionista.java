package clases;

import java.time.LocalDate;

public class Recepcionista extends Usuario {

    public Recepcionista(Hotel hotel, String usuario, String contrasenia, String nombre) {
        super(hotel, usuario, contrasenia, nombre);
    }

    public Recepcionista(int id, String usuario, String contrasenia, String nombre, Hotel hotel) {
        super(id, usuario, contrasenia, nombre, hotel);
    }

    public void realizarReservaRec(Reserva reserva) {
        super.getHotel().agregarReservaHotel(reserva);
    }

    public void realizarCheckInRec(Reserva reserva) {
        super.getHotel().realizarCheckInHotel(reserva);
    }

    public void realizarCheckOutRec(Ocupacion ocupacion) {
        super.getHotel().realizarCheckOutHotel(ocupacion);
    }

    public void consultarHabitaciones() {
        super.getHotel().listarHabitaciones();
    }

    public void consultarHabitacionesDisponibles() {
        super.getHotel().listarHabitacionesDisponibles();
    }

    public void consultarHabitacionesNoDisponibles() {
        super.getHotel().listarHabitacionesNoDisponibles();
    }

    public void consultarHabitacionesOcupadar() {
        super.getHotel().listarHabitacionesOcupadas();
    }

    public void consultarDisponibilidad(int numero, LocalDate fechaEntrada, LocalDate fechaSalida) {
        super.getHotel().consultarDisponibilidad(numero, fechaEntrada, fechaSalida);
    }

    public void bloquearHabitacion(int numero, String motivo) {
        super.getHotel().bloquearHabitacion(numero, motivo);
    }

    public void habilitarHabitacion(int numero) {
        super.getHotel().habilitarHabitacion(numero);
    }

    public void crearReserva(Pasajero pasajero){
        getHotel().crearReserva(pasajero);
    }
}