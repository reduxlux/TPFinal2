package Excepciones;

public class ColeccionVaciaException extends Exception {
    public ColeccionVaciaException(Class<?> clase) {
        super("no hay ningun " + clase.getSimpleName() + " guardado");
    }
}
