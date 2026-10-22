package clases;
import interfaces.Autenticable;
import Enum.EEstadoAdministrador;
import Enum.EEstadoRecepcionista;

import java.util.Objects;

public abstract class Usuario implements Autenticable {

    private int id;
    private int contador = 0;
    private String usuario;
    private String contrasenia;
    private String nombre;

    Hotel hotel;

    public Usuario(String usuario, String contrasenia, String nombre) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
    }

    //getters


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



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(usuario, usuario1.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(usuario);
    }
}