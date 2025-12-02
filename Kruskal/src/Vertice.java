public class Vertice {
    private String nombre;
    private String padre;
    private Lista8 lista;
    
    public Vertice(String nombre) {
        this.nombre = nombre;
        this.padre = nombre;
        this.lista = new Lista8();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getPadre() {
        return padre;
    }
    
    public void setPadre(String padre) {
        this.padre = padre;
    }
    
    public Lista8 getLista() {
        return lista;
    }
    
    public void agregarArista(String destino, int peso) {
        lista.insertar(destino, peso);
    }
}
