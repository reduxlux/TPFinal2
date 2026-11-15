package clases;

import Excepciones.ColeccionVaciaException;
import Excepciones.FechaInvalidaException;

import java.sql.SQLException;
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

    public void realizarCheckInRec(Reserva reserva) throws FechaInvalidaException{
        super.getHotel().realizarCheckInHotel(reserva);
    }

    public void realizarCheckOutRec(Estadia estadia) throws FechaInvalidaException{
        super.getHotel().realizarCheckOutHotel(estadia);
    }

    public void consultarHabitacionesNoDisponibles() throws ColeccionVaciaException{
        super.getHotel().listarHabitacionesNoDisponibles();
    }

    public void consultarHabitacionesOcupadar() throws ColeccionVaciaException{
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

    public void crearReserva(Pasajero pasajero) throws FechaInvalidaException, SQLException {
        getHotel().crearReserva(pasajero);
    }

    public void consultarReservas() throws ColeccionVaciaException {
        super.getHotel().listarReservas();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}