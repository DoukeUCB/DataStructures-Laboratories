public class Lista8 {
    private Caja pri;
    
    public Lista8() {
        this.pri = null;
    }
    
    public void insertar(String destino, int peso) {
        Caja nuevo = new Caja(destino, peso);
        
        if (pri == null) {
            pri = nuevo;
        } else {
            Caja aux = pri;
            while (aux.getSig() != null) {
                aux = aux.getSig();
            }
            aux.setSig(nuevo);
        }
    }
    
    public Caja getPri() {
        return pri;
    }
    
    public boolean estaVacia() {
        return pri == null;
    }
}
