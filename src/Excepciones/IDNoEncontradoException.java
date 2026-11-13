package Excepciones;

public class IDNoEncontradoException extends Exception {
    public IDNoEncontradoException(Class<?> clase, int id) {
        super("no se encontro ningun " + clase.getSimpleName() + " con el id " + id);
    }
}
