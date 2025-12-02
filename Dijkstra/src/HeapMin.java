public class HeapMin<T extends Comparable<T>> {
    private T[] vec;
    private int ultimo;
    private static final int CAPACIDAD = 1000;
    
    @SuppressWarnings("unchecked")
    public HeapMin() {
        vec = (T[]) new Comparable[CAPACIDAD];
        ultimo = 0;
    }
    
    private int padre(int i) {
        return i / 2;
    }
    
    private int hijoIzq(int i) {
        return 2 * i;
    }
    
    private int hijoDer(int i) {
        return 2 * i + 1;
    }
    
    private void swap(int i, int j) {
        T temp = vec[i];
        vec[i] = vec[j];
        vec[j] = temp;
    }
    
    // Heap Up para MIN HEAP (padre debe ser MENOR que hijo)
    private void heapUp(int i) {
        if (i <= 1) {
            return;
        }
        
        int p = padre(i);
        
        // Si el padre es MAYOR que el hijo, intercambiar (MIN HEAP)
        if (vec[p].compareTo(vec[i]) > 0) {
            swap(i, p);
            heapUp(p);
        }
    }
    
    // Heap Down para MIN HEAP (padre debe ser MENOR que hijos)
    private void heapDown(int i) {
        int izq = hijoIzq(i);
        int der = hijoDer(i);
        int menor;
        
        if (izq <= ultimo && der <= ultimo) {
            // Ambos hijos existen: encontrar el MENOR
            if (vec[izq].compareTo(vec[der]) < 0) {
                menor = izq;
            } else {
                menor = der;
            }
            
            // Si el padre es MAYOR que el hijo menor, intercambiar
            if (vec[i].compareTo(vec[menor]) > 0) {
                swap(i, menor);
                heapDown(menor);
            }
        } else if (izq <= ultimo) {
            // Solo existe el hijo izquierdo
            if (vec[i].compareTo(vec[izq]) > 0) {
                swap(i, izq);
                heapDown(izq);
            }
        }
    }
    
    public void insertar(T elemento) {
        if (ultimo >= CAPACIDAD - 1) {
            System.out.println("Heap lleno.");
            return;
        }
        
        ultimo++;
        vec[ultimo] = elemento;
        heapUp(ultimo);
    }
    
    public T extraerMin() {
        if (ultimo == 0) {
            return null;
        }
        
        T min = vec[1];
        vec[1] = vec[ultimo];
        ultimo--;
        
        if (ultimo > 0) {
            heapDown(1);
        }
        
        return min;
    }
    
    public boolean estaVacio() {
        return ultimo == 0;
    }
    
    public int tamanio() {
        return ultimo;
    }
    
    public T verMin() {
        if (ultimo == 0) {
            return null;
        }
        return vec[1];
    }
}
