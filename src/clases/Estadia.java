package clases;

import java.time.LocalDate;
import java.util.Objects;
/// INFORMACION DE HABITACION OCUPADA
public class Estadia {
    private static int contador = 1;
    private int id;
    private Pasajero pasajero;
    private Habitacion habitacion;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    private int idReserva;

    public Estadia(Pasajero pasajero, Habitacion habitacion,
                   LocalDate fechaCheckIn, LocalDate fechaCheckOut) {
        id = contador++;
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
    }

    public Estadia(Pasajero pasajero, Habitacion habitacion,
                   LocalDate fechaCheckIn, LocalDate fechaCheckOut, int idReserva) {
        id = contador++;
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
        this.idReserva = idReserva;
    }

    public Estadia(int id) {
        this.id = id;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public int getId() {
        return id;
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

    public LocalDate getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(LocalDate fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public LocalDate getFechaCheckOut() {
        return fechaCheckOut;
    }

    public void setFechaCheckOut(LocalDate fechaCheckOut) {
        this.fechaCheckOut = fechaCheckOut;
    }

    public boolean estaActiva() {
        return fechaCheckOut == null;
    }

    public void realizarCheckOut(LocalDate fecha) {
        this.fechaCheckOut = fecha;
    }

    ///equal y hashcode

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Estadia estadia = (Estadia) o;
        return Objects.equals(pasajero, estadia.pasajero) && Objects.equals(habitacion, estadia.habitacion) && Objects.equals(fechaCheckIn, estadia.fechaCheckIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pasajero, habitacion, fechaCheckIn);
    }

    ///toString
    @Override
    public String toString() {
        return "========== ESTADIA ==========\n" +
                "ID: " + id + "\n" +
                "Pasajero: " + pasajero + "\n" +
                "Habitación: " + (habitacion != null ? habitacion.getNumero() : "No tiene") + "\n" +
                "Fecha de Check-In: " + fechaCheckIn + "\n" +
                "Fecha de Check-Out: " + (fechaCheckOut != null ? fechaCheckOut : "Pendiente") + "\n" +
                "===============================";
    }
}