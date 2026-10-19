package clases;
import interfaces.Autenticable;

import java.util.Objects;

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

    public abstract void mostrarMenu();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(usuario, usuario1.usuario) && Objects.equals(contrasenia, usuario1.contrasenia) && Objects.equals(nombre, usuario1.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, contrasenia, nombre);
    }

    @Override
    public void iniciarSesion(String dni, String nombre, String usuario) {

    }

    @Override
    public void cerrarSesion(String usuario) {

    }
}