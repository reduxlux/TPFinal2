package clases;
import Enum.EEstadoReserva;

import java.time.LocalDate;
import java.util.Objects;

public class Reserva {

    private int id;
    private static int contador = 0;
    private Pasajero pasajero;
    private Habitacion habitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private EEstadoReserva estado;

    public Reserva(Pasajero pasajero, Habitacion habitacion,
                   LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.id = contador++;
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = EEstadoReserva.CONFIRMADA;
    }

    public Reserva(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public EEstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EEstadoReserva estado) {
        this.estado = estado;
    }

    public boolean cancelar() {
        if(estado != EEstadoReserva.CONFIRMADA)
        {
            return false;
        }
        this.estado = EEstadoReserva.CANCELADA;
        return true;
    }

    public boolean estaVigente() {
        return estado == EEstadoReserva.CONFIRMADA && fechaSalida.isAfter(LocalDate.now());
    }


    ///equals y hashcode

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return id == reserva.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    ///toString
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", pasajero=" + pasajero +
                ", habitacion=" + habitacion +
                ", fechaEntrada=" + fechaEntrada +
                ", fechaSalida=" + fechaSalida +
                ", estado=" + estado +
                '}';
    }
}