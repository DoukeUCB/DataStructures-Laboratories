class Heap<T extends Comparable<T>> {
    private NODO<T>[] vec;
    private int ultimo;
    private static final int CAPACIDAD = 32000;
    
    public Heap() {
        vec = (NODO<T>[]) new NODO[CAPACIDAD];
        for (int i = 0; i < CAPACIDAD; i++) {
            vec[i] = new NODO<>();
        }
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
        T tempKey = vec[i].getKey();
        vec[i].setKey(vec[j].getKey());
        vec[j].setKey(tempKey);
    }
    
    private void heapUp(int i) {
        if (i <= 1) {
            return;
        }
        
        int p = padre(i);
        
        // Si el padre es menor que el hijo actual, intercambiar
        if (vec[p].getKey().compareTo(vec[i].getKey()) < 0) {
            swap(i, p);
            heapUp(p);
        }
    }
    
    private void heapDown(int i) {
        int izq = hijoIzq(i);
        int der = hijoDer(i);
        int mayor;
        
        if (izq <= ultimo && der <= ultimo) {
            // Ambos hijos existen: encontrar el mayor
            if (vec[izq].getKey().compareTo(vec[der].getKey()) > 0) {
                mayor = izq;
            } else {
                mayor = der;
            }
            
            // Comparar el padre con el hijo mayor
            if (vec[i].getKey().compareTo(vec[mayor].getKey()) < 0) {
                swap(i, mayor);
                heapDown(mayor);
            }
        } else if (izq <= ultimo) {
            // Solo existe el hijo izquierdo
            if (vec[i].getKey().compareTo(vec[izq].getKey()) < 0) {
                swap(i, izq);
                heapDown(izq);
            }
        }
    }
    
    public void insertar(T key) {
        if (ultimo >= CAPACIDAD - 1) {
            System.out.println("Heap lleno. No se puede insertar.");
            return;
        }
        
        ultimo++;
        vec[ultimo].setKey(key);
        vec[ultimo].setPresente(true);
        heapUp(ultimo);
    }
    
    public T eliminarRaiz() {
        if (ultimo == 0) {
            System.out.println("Heap vacío.");
            return null;
        }
        
        T raiz = vec[1].getKey();
        vec[1].setKey(vec[ultimo].getKey());
        vec[ultimo].setPresente(false);
        ultimo--;
        
        if (ultimo > 0) {
            heapDown(1);
        }
        
        return raiz;
    }
    
    public void mostrarArray() {
        if (ultimo == 0) {
            System.out.println("Heap vacío.");
            return;
        }
        
        System.out.println("\nArray del Heap (índice 1 a " + ultimo + "):");
        System.out.print("[ ");
        for (int i = 1; i <= ultimo; i++) {
            System.out.print(vec[i].getKey());
            if (i < ultimo) System.out.print(", ");
        }
        System.out.println(" ]");
        
        System.out.println("\nEstructura del Heap:");
        mostrarEstructura();
    }
    
    private void mostrarEstructura() {
        if (ultimo == 0) return;
        
        int nivel = 0;
        int nodosEnNivel = 1;
        int i = 1;
        
        while (i <= ultimo && nivel < 4) {
            System.out.print("Nivel " + nivel + ": ");
            
            for (int j = 0; j < nodosEnNivel && i <= ultimo; j++, i++) {
                System.out.print(vec[i].getKey() + " ");
            }
            System.out.println();
            
            nivel++;
            nodosEnNivel *= 2;
        }
        
        if (i <= ultimo) {
            System.out.println("... (total: " + ultimo + " elementos)");
        }
    }
    
    public void heapSort() {
        if (ultimo == 0) {
            System.out.println("Heap vacío.");
            return;
        }
        
        int originalUltimo = ultimo;
        T[] copiaOriginal = (T[]) new Comparable[originalUltimo + 1];
        // Guardar una copia para restaurar el heap después
        for (int i = 1; i <= originalUltimo; i++) {
            copiaOriginal[i] = vec[i].getKey();
        }

        for (int i = originalUltimo; i > 1; i--) {
            swap(1, i); // Mover la raíz (máximo) al final
            ultimo--;   // Reducir el tamaño visible del heap
            heapDown(1); // Restaurar la propiedad del heap
        }
        
        System.out.println("Array ordenado (menor a mayor):");
        System.out.print("[ ");
        for (int i = 1; i <= originalUltimo; i++) {
            System.out.print(vec[i].getKey());
            if (i < originalUltimo) System.out.print(", ");
        }
        System.out.println(" ]");
        
        ultimo = 0;
        for (int i = 1; i <= originalUltimo; i++) {
            insertar(copiaOriginal[i]);
        }
        System.out.println("Heap restaurado.");
    }
    
    public boolean estaVacio() {
        return ultimo == 0;
    }
    
    public int tamanio() {
        return ultimo;
    }
    
    public T verRaiz() {
        if (ultimo == 0) {
            return null;
        }
        return vec[1].getKey();
    }
}
