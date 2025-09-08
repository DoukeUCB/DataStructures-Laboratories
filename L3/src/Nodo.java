public class Nodo {
    private Nodo anterior;
    private String nombre;
    private Nodo siguiente;
    private Lista8<String> listaMateriales;
    
    public Nodo(Nodo anterior, String nombre, Nodo siguiente) {
        this.anterior = anterior;
        this.nombre = nombre;
        this.siguiente = siguiente;
        this.listaMateriales = new Lista8<>();
    }
    
    public Nodo getAnterior() {
        return anterior;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Nodo getSiguiente() {
        return siguiente;
    }
    
    public Lista8<String> getListaMateriales() {
        return listaMateriales;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
    public void setListaMateriales(Lista8<String> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }
}
