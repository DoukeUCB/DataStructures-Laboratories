public class Nodo {
    private String nombre;
    private Nodo siguiente;
    private Nodo anterior;
    private HashRestaurante hash;
    
    public Nodo(String nombre) {
        this.nombre = nombre;
        this.siguiente = null;
        this.anterior = null;
        this.hash = new HashRestaurante();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Nodo getSiguiente() {
        return siguiente;
    }
    
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
    public Nodo getAnterior() {
        return anterior;
    }
    
    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }
    
    public HashRestaurante getHash() {
        return hash;
    }
    
    public void setHash(HashRestaurante hash) {
        this.hash = hash;
    }
    
    public void agregarIngrediente(String ingrediente) {
        hash.insertarIngrediente(ingrediente);
    }
    
    public boolean tieneIngrediente(String ingrediente) {
        return hash.buscarIngrediente(ingrediente);
    }
    
    public void mostrarIngredientes() {
        System.out.print("Plato: " + nombre + " - Ingredientes: ");
        hash.mostrarIngredientes();
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
