package Excepciones;

public class PasajeroNoEncontradoException extends Exception {
    public PasajeroNoEncontradoException(String dni) {
        super("no se encontro ningun pasajero con el dni " + dni);
    }
}
