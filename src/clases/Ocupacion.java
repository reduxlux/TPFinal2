package clases;

import java.time.LocalDate;
import java.util.Objects;
/// INFORMACION DE HABITACION OCUPADA
public class Ocupacion {
    private static int contador = 0;
    private int id;
    private Pasajero pasajero;
    private Habitacion habitacion;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;

    public Ocupacion(Pasajero pasajero, Habitacion habitacion,
                     LocalDate fechaCheckIn, LocalDate fechaCheckOut) {
        id = contador++;
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = fechaCheckOut;
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
        Ocupacion ocupacion = (Ocupacion) o;
        return Objects.equals(pasajero, ocupacion.pasajero) && Objects.equals(habitacion, ocupacion.habitacion) && Objects.equals(fechaCheckIn, ocupacion.fechaCheckIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pasajero, habitacion, fechaCheckIn);
    }
}