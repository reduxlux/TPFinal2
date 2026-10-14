public abstract class Usuario implements Autenticable {

    private String usuario;
    private String contrasenia;
    private String nombre;

    public Usuario(String usuario, String contrasenia, String nombre) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void iniciarSesion() {
        // ...
    }

    @Override
    public void cerrarSesion() {
        // ...
    }

    public abstract void mostrarMenu();
}