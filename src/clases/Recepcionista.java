package clases;

import java.time.LocalDate;

public class Recepcionista extends Usuario {


    public Recepcionista(String usuario, String contrasenia, String nombre) {
        super(usuario, contrasenia, nombre);
    }

    public void realizarReservaRec(Reserva reserva) {
        hotel.agregarReservaHotel(reserva);
    }

    public void realizarCheckInRec(Reserva reserva) {
        hotel.realizarCheckInHotel(reserva);
    }

    public void realizarCheckOutRec(Ocupacion ocupacion) {
        hotel.realizarCheckOutHotel(ocupacion);
    }

    public void consultarHabitaciones() {
        hotel.listarHabitaciones();
    }

    public void consultarHabitacionesDisponibles() {
        hotel.listarHabitacionesDisponibles();
    }

    public void consultarHabitacionesNoDisponibles() {
        hotel.listarHabitacionesNoDisponibles();
    }

    public void consultarHabitacionesOcupadar() {
        hotel.listarHabitacionesOcupadas();
    }

    public void consultarDisponibilidad(int numero, LocalDate fechaEntrada, LocalDate fechaSalida) {
        hotel.consultarDisponibilidad(numero, fechaEntrada, fechaSalida);
    }

    public void bloquearHabitacion(int numero, String motivo) {
        hotel.bloquearHabitacion(numero, motivo);
    }

    public void habilitarHabitacion(int numero) {
        hotel.habilitarHabitacion(numero);
    }
}