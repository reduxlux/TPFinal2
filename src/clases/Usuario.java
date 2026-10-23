package clases;
import interfaces.Autenticable;
import Enum.EEstadoAdministrador;
import Enum.EEstadoRecepcionista;

import java.util.Objects;

public abstract class Usuario implements Autenticable {

    private final int id;
    private static int contador = 0;
    private String usuario;
    private String contrasenia;
    private String nombre;

    Hotel hotel;

    public Usuario(String usuario, String contrasenia, String nombre) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.id = contador++;
    }

    //getters


    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    //setters

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //metodos


    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Usuario usuario = (Usuario) object;
        return id == usuario.id;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}