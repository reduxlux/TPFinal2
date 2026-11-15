package clases;
import Enum.EEstadoHabitacion;
import Enum.ETipoHabitacion;
import java.util.Scanner;

public class Habitacion {

    Scanner sc = new Scanner(System.in);

    private int numero;
    private int piso;
    private ETipoHabitacion tipo;
    private EEstadoHabitacion estado;
    private String motivoNoDisponible;

    public Habitacion(int numero, int piso, ETipoHabitacion tipo) {
        this.numero = numero;
        this.piso = piso;
        this.tipo = tipo;
        this.estado = EEstadoHabitacion.DISPONIBLE;
        this.motivoNoDisponible = "";
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public ETipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(ETipoHabitacion tipo) {
        this.tipo = tipo;
    }

    public EEstadoHabitacion getEstado() {
        return estado;
    }

    public void setEstado(EEstadoHabitacion estado) {
        this.estado = estado;
    }

    public String getMotivoNoDisponible() {
        return motivoNoDisponible;
    }

    public void setMotivoNoDisponible(String motivoNoDisponible) {
        this.motivoNoDisponible = motivoNoDisponible;
    }

    public boolean estaDisponible() {
        return estado == EEstadoHabitacion.DISPONIBLE;
    }

    public void ocupar() {
        this.estado = EEstadoHabitacion.NO_DISPONIBLE;
    }

    public void habilitar() {
        this.estado = EEstadoHabitacion.DISPONIBLE;
        this.motivoNoDisponible = "";
    }

    public void reservar() {
        this.estado = EEstadoHabitacion.RESERVADA;
    }

    public void bloquear(String motivo) {
        this.estado = EEstadoHabitacion.NO_DISPONIBLE;
        this.motivoNoDisponible = motivo;
    }

    public String mostrarInformacion() {
        StringBuilder sb = new StringBuilder();
        sb.append("Habitacion: " + this.numero + " ");
        sb.append("Piso: " + this.piso + " ");
        sb.append("Tipo: " + this.tipo + " ");
        sb.append("Estado: " + this.estado + " ");
        if (this.estado == EEstadoHabitacion.NO_DISPONIBLE) {
            sb.append("Motivo: " + motivoNoDisponible);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Habitacion)) return false;
        Habitacion that = (Habitacion) o;
        return getNumero() == that.getNumero();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getNumero());
    }
}