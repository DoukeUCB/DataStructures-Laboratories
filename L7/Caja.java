public class Caja<T> {
    private T elemento;
    private Caja<T> siguiente;
    private Caja<T> anterior;
    private int contador;
    
    public Caja(Caja<T> anterior, T elemento, Caja<T> siguiente) {
        this.anterior = anterior;
        this.elemento = elemento;
        this.siguiente = siguiente;
        this.contador = 1;
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
    
    public int getContador() {
        return contador;
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
    
    public void incrementarContador() {
        this.contador++;
    }
    
    public void setContador(int contador) {
        this.contador = contador;
    }
}
