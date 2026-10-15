import java.util.ArrayList;
import java.util.Objects;

public class Pasajero {

    private String nombre;
    private String dni;
    private String origen;
    private String domicilioOrigen;

    private ArrayList<Ocupacion> historial;

    public Pasajero(String nombre, String dni, String origen, String domicilioOrigen) {
        this.nombre = nombre;
        this.dni = dni;
        this.origen = origen;
        this.domicilioOrigen = domicilioOrigen;
        this.historial = new ArrayList<>();
    }

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

    public ArrayList<Ocupacion> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Ocupacion> historial) {
        this.historial = historial;
    }

    public void agregarEstadia(Ocupacion ocupacion) {
        historial.add(ocupacion);
    }

    public void mostrarHistorial() {
        // ...
    }

    // Getters y setters


    //metodos


    ///equals y hashcode para agregar pasajero en hotel
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