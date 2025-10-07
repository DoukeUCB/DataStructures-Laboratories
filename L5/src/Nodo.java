public class Nodo {
    private String nombre;
    private Nodo siguiente;
    private Nodo anterior;
    private Hash hash;
    
    public Nodo(String nombre) {
        this.nombre = nombre;
        this.siguiente = null;
        this.anterior = null;
        this.hash = new Hash();
    }
    
    // Getters y Setters
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
    
    public Hash getHash() {
        return hash;
    }
    
    public void setHash(Hash hash) {
        this.hash = hash;
    }
    
    // Métodos para manejar ingredientes
    public void agregarIngrediente(String ingrediente) {
        hash.insertarNombre(ingrediente);
    }
    
    public boolean tieneIngrediente(String ingrediente) {
        return hash.buscarNombre(ingrediente);
    }
    
    public void mostrarIngredientes() {
        System.out.print("Plato: " + nombre + " - Ingredientes: ");
        hash.mostrarSoloOcupadas();
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
