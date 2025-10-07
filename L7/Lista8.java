public class Lista8<T> {
    private Caja<T> PRI;
    private Caja<T> ULT;
    private boolean res;
    
    public Lista8() {
        this.PRI = null;
        this.ULT = null;
        this.res = false;
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
    
    public boolean estaVacia() {
        return PRI == null;
    }
    
    public int obtenerTamaño() {
        int contador = 0;
        Caja<T> actual = PRI;
        
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        
        return contador;
    }
    
    public Caja<T> buscarCajaElemento(T el) {
        Caja<T> actual = PRI;
        
        while (actual != null) {
            if (actual.getElemento().equals(el)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        
        return null;
    }
    
    public boolean insertarConContador(T el) {
        Caja<T> cajaExistente = buscarCajaElemento(el);
        
        if (cajaExistente != null) {
            cajaExistente.incrementarContador();
            return true;
        } else {
            return insertarFinal(el);
        }
    }
    
    public void mostrarListaConContadores() {
        if (PRI == null) {
            System.out.print("[]");
            return;
        }
        
        System.out.print("[");
        Caja<T> actual = PRI;
        boolean primero = true;
        
        while (actual != null) {
            if (!primero) {
                System.out.print(", ");
            }
            System.out.print(actual.getElemento() + " (" + actual.getContador() + ")");
            primero = false;
            actual = actual.getSiguiente();
        }
        System.out.print("]");
    }
    
    public int contarElementosConContadores() {
        int total = 0;
        Caja<T> actual = PRI;
        
        while (actual != null) {
            total += actual.getContador();
            actual = actual.getSiguiente();
        }
        
        return total;
    }
}
