public class Lista2<T> {

    private static final int TAM = 10;
    private final T[] vec;
    private int PRI = -1;
    private int ULT = -1;
    private boolean res;

    @SuppressWarnings("unchecked")
    public Lista2() {
        vec = (T[]) new Object[TAM];
    }
    
    public boolean insertarPrincipio(T el) {
        // Si la lista está vacía
        if (PRI == -1) {
            PRI = 0;
            ULT = 0;
            vec[PRI] = el;
            res = true;
        } else if (estaLlena()) {
            res = false;
        } else {
            // Si PRI está en la posición 0, mover al final del arreglo
            if (PRI == 0) {
                PRI = TAM - 1;
            } else {
                PRI--;
            }
            vec[PRI] = el;
            res = true;
        }
        return res;
    }
    
    public boolean insertarFinal(T el) {
        // Si la lista está vacía
        if (PRI == -1) {
            PRI = 0;
            ULT = 0;
            vec[ULT] = el;
            return true;
        }
        
        // Si la lista está llena
        if (estaLlena()) {
            return false;
        }
        
        // Si ULT está en la última posición, mover al inicio del arreglo
        if (ULT == TAM - 1) {
            ULT = 0;
        } else {
            ULT++;
        }
        
        vec[ULT] = el;
        return true;
    }
    
    public boolean eliminarPrincipio() {
        // Si la lista está vacía
        if (PRI == -1) {
            return false;
        }
        
        vec[PRI] = null;
        
        // Si solo había un elemento
        if (PRI == ULT) {
            PRI = -1;
            ULT = -1;
            return true;
        }
        
        // Mover PRI al siguiente elemento
        if (PRI == TAM - 1) {
            PRI = 0;
        } else {
            PRI++;
        }
        
        return true;
    }
    
    public boolean eliminarFinal() {
        // Si la lista está vacía
        if (PRI == -1) {
            return false;
        }
        
        vec[ULT] = null;
        
        // Si solo había un elemento
        if (PRI == ULT) {
            PRI = -1;
            ULT = -1;
            return true;
        }
        
        // Mover ULT al elemento anterior
        if (ULT == 0) {
            ULT = TAM - 1;
        } else {
            ULT--;
        }
        
        return true;
    }
    
    public void mostrarLista() {
        if (PRI == -1) {
            System.out.println("Lista vacía");
            return;
        }
        
        System.out.print("Lista: [");
        int actual = PRI;
        boolean primero = true;
        
        do {
            if (!primero) {
                System.out.print(", ");
            }
            System.out.print(vec[actual]);
            primero = false;
            
            if (actual == ULT) {
                break;
            }
            
            actual = (actual + 1) % TAM;
        } while (actual != PRI);
        
        System.out.println("]");
    }
    
    public void recorrerVector() {
        System.out.print("Vector completo: [");
        for (int i = 0; i < TAM; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(vec[i]);
        }
        System.out.println("]");
        System.out.println("PRI: " + PRI + ", ULT: " + ULT);
    }
    
    private boolean estaLlena() {
        return (PRI == 0 && ULT == TAM - 1) || (PRI - 1 == ULT);
    }
    
    public boolean estaVacia() {
        return PRI == -1;
    }

    public Lista2<T> unir(Lista2<T> otraLista) {
        Lista2<T> resultado = new Lista2<>();
        
        // Agregar elementos de la primera lista
        if (!this.estaVacia()) {
            int actual = PRI;
            do {
                resultado.insertarFinal(vec[actual]);
                if (actual == ULT) {
                    break;
                }
                actual = (actual + 1) % TAM;
            } while (actual != PRI);
        }
        
        // Agregar elementos de la segunda lista
        if (!otraLista.estaVacia()) {
            int actual = otraLista.PRI;
            do {
                resultado.insertarFinal(otraLista.vec[actual]);
                if (actual == otraLista.ULT) {
                    break;
                }
                actual = (actual + 1) % TAM;
            } while (actual != otraLista.PRI);
        }
        
        return resultado;
    }
}
