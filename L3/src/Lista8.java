public class Lista8<T> {
    private Caja<T> PRI;
    private Caja<T> ULT;
    private boolean res;
    
    public Lista8() {
        this.PRI = null;
        this.ULT = null;
        this.res = false;
    }
    
    public boolean insertarPrincipio(T el) {
        if (PRI == null) {
            PRI = new Caja<>(null, el, null);
            ULT = PRI;
        } else {
            Caja<T> nuevaCaja = new Caja<>(null, el, PRI);
            PRI.setAnterior(nuevaCaja);
            PRI = nuevaCaja;
        }
        res = true;
        return res;
    }
    
    public boolean insertarFinal(T el) {
        if (PRI == null) {
            PRI = new Caja<>(null, el, null);
            ULT = PRI;
        } else {
            ULT = new Caja<>(ULT, el, null);
            ULT.getAnterior().setSiguiente(ULT);
        }
        res = true;
        return res;
    }
    
    public boolean eliminarPrincipio() {
        if (PRI == null) {
            res = false;
        } else {
            if (PRI == ULT) {
                PRI = null;
                ULT = null;
            } else {
                PRI = PRI.getSiguiente();
                PRI.setAnterior(null);
            }
            res = true;
        }
        return res;
    }
    
    public boolean eliminarFinal() {
        if (ULT == null) {
            res = false;
        } else if (PRI == ULT) {
            PRI = null;
            ULT = null;
            res = true;
        } else {
            ULT = ULT.getAnterior();
            ULT.setSiguiente(null);
            res = true;
        }
        return res;
    }
    
    public void mostrarLista() {
        if (PRI == null) {
            System.out.println("Lista vacía");
            return;
        }
        
        System.out.print("Lista: [");
        Caja<T> actual = PRI;
        boolean primero = true;
        
        while (actual != null) {
            if (!primero) {
                System.out.print(", ");
            }
            System.out.print(actual.getElemento());
            primero = false;
            actual = actual.getSiguiente();
        }
        System.out.println("]");
    }
    
    public boolean buscarSecuencialmente(T el) {
        Caja<T> actual = PRI;
        
        while (actual != null) {
            if (actual.getElemento().equals(el)) {
                res = true;
                return res;
            }
            actual = actual.getSiguiente();
        }
        
        res = false;
        return res;
    }
    
    public boolean buscarRecursivo(T el) {
        return buscarRecursivo(el, PRI);
    }
    
    private boolean buscarRecursivo(T el, Caja<T> AUX) {
        if (AUX == null) {
            res = false;
        } else {
            if (el.equals(AUX.getElemento())) {
                res = true;
            } else {
                res = buscarRecursivo(el, AUX.getSiguiente());
            }
        }
        return res;
    }
    
    // Método auxiliar para buscar y retornar la caja encontrada
    private Caja<T> buscarCaja(T el) {
        Caja<T> actual = PRI;
        
        while (actual != null) {
            if (actual.getElemento().equals(el)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        
        return null;
    }
    
    // Buscar y eliminar elemento
    public boolean buscarYEliminar(T el) {
        // Primero verificamos si la lista está vacía
        if (PRI == null) {
            res = false;
            return res;
        }
        
        // Usamos búsqueda secuencial para encontrar el elemento
        Caja<T> AUX = buscarCaja(el);
        
        // Si no se encontró el elemento
        if (AUX == null) {
            res = false;
            return res;
        }
        
        // Caso 1: El elemento está al principio
        if (AUX == PRI) {
            return eliminarPrincipio();
        }
        
        // Caso 2: El elemento está al final
        if (AUX == ULT) {
            return eliminarFinal();
        }
        
        // Caso 3: El elemento está en el medio
        // Reasignamos los enlaces anterior y siguiente
        Caja<T> anterior = AUX.getAnterior();
        Caja<T> siguiente = AUX.getSiguiente();
        
        // El nodo anterior ahora apunta al siguiente
        anterior.setSiguiente(siguiente);
        // El nodo siguiente ahora apunta al anterior
        siguiente.setAnterior(anterior);
        
        // Limpiamos las referencias del nodo eliminado (opcional, buena práctica)
        AUX.setAnterior(null);
        AUX.setSiguiente(null);
        
        res = true;
        return res;
    }
    
    // Método auxiliar para verificar si la lista está vacía
    public boolean estaVacia() {
        return PRI == null;
    }
    
    // Método auxiliar para obtener el tamaño
    public int obtenerTamaño() {
        int contador = 0;
        Caja<T> actual = PRI;
        
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        
        return contador;
    }
}
