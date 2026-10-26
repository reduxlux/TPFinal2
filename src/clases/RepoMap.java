package clases;

import java.util.HashMap;

public class RepoMap <K, V>{
    private HashMap<K,V> personas;

    public RepoMap() {
        personas = new HashMap<>();
    }

    public HashMap<K, V> getPersonas() {
        return personas;
    }

    public void agregar(K key, V persona){
        personas.put(key, persona);
    }

    public void eliminar(K key){
        personas.remove(key);
    }

    public V obtener(K key){
        return personas.get(key);
    }

    public boolean contiene(K key){
        return personas.containsKey(key);
    }

    public int cantidad(){
        return personas.size();
    }

    public boolean estaVacio(){
        return personas.isEmpty();
    }
}
