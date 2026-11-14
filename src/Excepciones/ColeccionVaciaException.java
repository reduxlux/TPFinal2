package Excepciones;

public class ColeccionVaciaException extends Exception {
    public ColeccionVaciaException(Class<?> clase, String condicion) {
        super("no hay ningun " + clase.getSimpleName() + " guardado " + condicion);
    }
}
