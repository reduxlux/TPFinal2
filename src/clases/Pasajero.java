package clases;

import Excepciones.ColeccionVaciaException;

import java.util.Objects;

public class Pasajero {

    private String nombre;
    private String dni;
    private String origen;
    private String domicilioOrigen;
    private Hotel hotel;

    private RepoArr<Estadia> historial;

    public Pasajero(String nombre, String dni, String origen, String domicilioOrigen, Hotel hotel) {
        this.nombre = nombre;
        this.dni = dni;
        this.origen = origen;
        this.domicilioOrigen = domicilioOrigen;
        this.historial = new RepoArr<>();
        this.hotel = hotel;
    }

    public Pasajero(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
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

    public RepoArr<Estadia> getHistorial() {
        return historial;
    }

    public void setHistorial(RepoArr<Estadia> historial) {
        this.historial = historial;
    }

    /// METODOS

    public boolean agregarEstadia(Estadia estadia) {
        if (estadia == null || historial.contiene(estadia)) {
            return false;
        }

        if (!this.equals(estadia.getPasajero())) {
            return false;
        }

        historial.agregar(estadia);
        return true;
    }

    public String mostrarHistorial()
            throws ColeccionVaciaException{

        StringBuilder mensaje = new StringBuilder();

        if (historial.estaVacio()) {
            throw new ColeccionVaciaException(Estadia.class, " ");
        }
        for (Estadia estadia : historial) {
            mensaje.append(estadia).append("\n");
        }
        return mensaje.toString();
    }

    public int contarEstadias() {
        return historial.cantidad();
    }

    public void mostrarEstadiaActual() {
        Estadia actual = hotel.obtenerEstadiaActual(dni);
        if(actual == null){
            System.out.println("no tiene ninguna estadia activa");
            return;
        }
        System.out.println("su estadia actual es: " + actual.toString());
    }



    public Estadia obtenerUltimaEstadia() {

        Estadia ultima = null;

        for (Estadia estadia : historial) {

            if (ultima == null ||
                    estadia.getFechaCheckIn().isAfter(ultima.getFechaCheckIn())) {

                ultima = estadia;
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