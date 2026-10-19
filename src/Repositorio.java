import java.util.ArrayList;
import java.util.Iterator;

public class Repositorio<T> implements Iterable<T> {

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

    public boolean contiene(T elemento) {
        return elementos.contains(elemento);
    }

    public boolean estaVacio() {
        return elementos.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return elementos.iterator();
    }

}