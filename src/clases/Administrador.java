package clases;

public class Administrador extends Usuario {

    Hotel hotel;

    public Administrador(Hotel hotel, String usuario, String contrasenia, String nombre) {
        super(usuario, contrasenia, nombre);
        this.hotel = hotel;
    }

    public void crearUsuario() {
        // ...
    }

    public void realizarBackup() {
        // ...
    }

    public void asignarPermisos() {
        // ...
    }

    public void agregarHabitacion(Habitacion habitacion) {
        hotel.agregarHabitacion(habitacion);
    }

    public void eliminarHabitacion(int numeroHabitacion) {
        hotel.eliminarHabitacion(numeroHabitacion);
    }

    @Override
    public void mostrarMenu() {
        // ...
    }

    @Override
    public void iniciarSesion(String nombre, String dni, String usuario) {


    }

    @Override
    public void cerrarSesion(String usuario) {

    }
}