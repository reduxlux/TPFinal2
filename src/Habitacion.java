public class Habitacion {

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
        // No se que ponerle
    }

    public void liberar() {
        // No se que ponerle
    }

    public void bloquear(String motivo) {
        // No se que ponerle
    }
}