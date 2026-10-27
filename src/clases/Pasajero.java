package clases;

import Excepciones.ExcepcionPropia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

public class Pasajero {

    private String nombre;
    private String dni;
    private String origen;
    private String domicilioOrigen;
    private static Hotel hotel = Menu.getHotel();

    private RepoArr<Ocupacion> historial;

    public Pasajero(String nombre, String dni, String origen, String domicilioOrigen) {
        this.nombre = nombre;
        this.dni = dni;
        this.origen = origen;
        this.domicilioOrigen = domicilioOrigen;
        this.historial = new RepoArr<>();
    }

    /// GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDomicilioOrigen() {
        return domicilioOrigen;
    }

    public void setDomicilioOrigen(String domicilioOrigen) {
        this.domicilioOrigen = domicilioOrigen;
    }

    public RepoArr<Ocupacion> getHistorial() {
        return historial;
    }

    public void setHistorial(RepoArr<Ocupacion> historial) {
        this.historial = historial;
    }

    /// METODOS

    public boolean agregarEstadia(Ocupacion ocupacion) {
        if (ocupacion == null || historial.contiene(ocupacion)) {
            return false;
        }

        if (!this.equals(ocupacion.getPasajero())) {
            return false;
        }

        historial.agregar(ocupacion);
        return true;
    }

    public String mostrarHistorial() {

        StringBuilder mensaje = new StringBuilder();

        if (historial.estaVacio()) {
            return "El pasajero no tiene estadias registradas";
        }
        for (Ocupacion ocupacion : historial) {
            mensaje.append(ocupacion).append("\n");
        }

        return mensaje.toString();
    }

    public int contarEstadias() {
        return historial.cantidad();
    }

    public void crearEstadia(){
        hotel.crearReserva(this);
    }

    public void mostrarEstadiaActual(){
         Ocupacion actual = hotel.obtenerOcupacionActual(dni);
        System.out.println("su estadia actual es: " + actual.toString());
    }

    public void eliminarEstadia(){
        hotel.eliminarEstadia();
    }


    public Ocupacion obtenerUltimaEstadia() {

        Ocupacion ultima = null;

        for (Ocupacion ocupacion : historial) {

            if (ultima == null ||
                    ocupacion.getFechaCheckIn().isAfter(ultima.getFechaCheckIn())) {

                ultima = ocupacion;
            }
        }

        return ultima;
    }

    /// equals y hashcode para agregar pasajero en hotel
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pasajero pasajero = (Pasajero) o;
        return Objects.equals(dni, pasajero.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }
}