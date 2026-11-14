package clases;
import interfaces.Autenticable;

import java.util.Objects;

public abstract class Usuario implements Autenticable {

    private final int id;
    private static int contador = 1;
    private String usuario;
    private String contrasenia;
    private String nombre;
    private boolean sesionIniciada;
    private Hotel hotel;

    public Usuario(Hotel hotel, String usuario, String contrasenia, String nombre) {
        this.hotel = hotel;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.id = contador++;
        this.sesionIniciada = false;
    }

    public Usuario(int id, String usuario, String contrasenia, String nombre, Hotel hotel) {
        this.id = id;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.sesionIniciada = false;
        this.hotel = hotel;

        contador = Math.max(contador, id + 1);
    }

    //getters


    public Hotel getHotel() {
        return hotel;
    }

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

    public boolean isSesionIniciada() {
        return sesionIniciada;
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
        Usuario usuario = (Usuario) o;
        return getId() == usuario.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean iniciarSesion(String usuario, String contrasenia) {
        if (this.usuario.equalsIgnoreCase(usuario)
        && this.contrasenia.equals(contrasenia)) {
            sesionIniciada = true;
            System.out.println("Sesion Iniciada.");
            return true;
        }
        System.out.println("Usuario o constrasenia incorrectos.");
        return false;
    }

    @Override
    public void cerrarSesion() {
        if (sesionIniciada) {
            sesionIniciada = false;
            System.out.println("Sesion cerrada.");
        } else {
            System.out.println("No hay ninguna sesion iniciada.");
        }
    }
}