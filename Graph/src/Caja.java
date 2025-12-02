public class Caja<T> {
    private T elemento;
    private Caja<T> siguiente;
    private Caja<T> anterior;
    
    public Caja(Caja<T> anterior, T elemento, Caja<T> siguiente) {
        this.anterior = anterior;
        this.elemento = elemento;
        this.siguiente = siguiente;
    }
    
    public T getElemento() {
        return elemento;
    }
    
    public Caja<T> getSiguiente() {
        return siguiente;
    }
    
    public Caja<T> getAnterior() {
        return anterior;
    }
    
    public void setElemento(T elemento) {
        this.elemento = elemento;
    }
    
    public void setSiguiente(Caja<T> siguiente) {
        this.siguiente = siguiente;
    }
    
    public void setAnterior(Caja<T> anterior) {
        this.anterior = anterior;
    }
}
