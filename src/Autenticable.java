public interface Autenticable {

    void iniciarSesion(String dni, String nombre, String usuario);

    void cerrarSesion(String usuario);

}