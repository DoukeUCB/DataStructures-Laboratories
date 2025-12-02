public class Nodo {
    private Object el;
    private boolean marca;
    private int padre;
    private Lista8<Integer> lista;

    public Nodo(Object el) {
        this.el = el;
        this.marca = false;
        this.padre = -1;
        this.lista = new Lista8<>();
    }
    
    public Object getEl() {
        return el;
    }
    
    public boolean getMarca() {
        return marca;
    }
    
    public int getPadre() {
        return padre;
    }
    
    public Lista8<Integer> getLista() {
        return lista;
    }
    
    public void setEl(Object el) {
        this.el = el;
    }
    
    public void setMarca(boolean marca) {
        this.marca = marca;
    }
    
    public void setPadre(int padre) {
        this.padre = padre;
    }
    
    public void setLista(Lista8<Integer> lista) {
        this.lista = lista;
    }
}
