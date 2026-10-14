import java.util.ArrayList;

public class Repositorio<T> {

    private ArrayList<T> elementos;

    public Repositorio() {
        elementos = new ArrayList<>();
    }

    // Hay que ponerle un getter/setter?

    public void agregar(T elemento) {
        elementos.add(elemento);
    }

    public void eliminar(T elemento) {
        elementos.remove(elemento);
    }

    public T obtener(int posicion) {
        return elementos.get(posicion);
    }

    public int cantidad() {
        return elementos.size();
    }
}