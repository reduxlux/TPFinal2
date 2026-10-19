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
}