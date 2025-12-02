public class Heap {
    private Nodo[] vec;
    private int cantidad;
    private static final int MAX = 1000;
    
    public Heap() {
        vec = new Nodo[MAX];
        cantidad = 0;
    }
    
    public void insertar(Nodo nodo) {
        if (cantidad >= MAX) {
            System.out.println("Heap lleno.");
            return;
        }
        
        vec[cantidad] = nodo;
        heapUp(cantidad);
        cantidad++;
    }
    
    private void heapUp(int pos) {
        if (pos == 0) {
            return;
        }
        
        int padre = (pos - 1) / 2;
        
        if (vec[pos].compareTo(vec[padre]) < 0) {
            // Intercambiar
            Nodo temp = vec[pos];
            vec[pos] = vec[padre];
            vec[padre] = temp;
            
            heapUp(padre);
        }
    }
    
    public Nodo sacar() {
        if (cantidad == 0) {
            return null;
        }
        
        Nodo min = vec[0];
        cantidad--;
        
        if (cantidad > 0) {
            vec[0] = vec[cantidad];
            heapDown(0);
        }
        
        return min;
    }
    
    private void heapDown(int pos) {
        int hijoIzq = 2 * pos + 1;
        int hijoDer = 2 * pos + 2;
        int menor = pos;
        
        if (hijoIzq < cantidad && vec[hijoIzq].compareTo(vec[menor]) < 0) {
            menor = hijoIzq;
        }
        
        if (hijoDer < cantidad && vec[hijoDer].compareTo(vec[menor]) < 0) {
            menor = hijoDer;
        }
        
        if (menor != pos) {
            // Intercambiar
            Nodo temp = vec[pos];
            vec[pos] = vec[menor];
            vec[menor] = temp;
            
            heapDown(menor);
        }
    }
    
    public boolean estaVacio() {
        return cantidad == 0;
    }
    
    public int getCantidad() {
        return cantidad;
    }
}
