import java.util.ArrayList;

public class Nodo {
    private char vertice;
    private int distancia;
    private int padre;
    private boolean marca;
    private ArrayList<Arista> lista;
    
    public Nodo(char vertice) {
        this.vertice = vertice;
        this.distancia = Integer.MAX_VALUE;
        this.padre = -1;
        this.marca = false;
        this.lista = new ArrayList<>();
    }
    
    public char getVertice() {
        return vertice;
    }
    
    public int getDistancia() {
        return distancia;
    }
    
    public int getPadre() {
        return padre;
    }
    
    public boolean getMarca() {
        return marca;
    }
    
    public ArrayList<Arista> getLista() {
        return lista;
    }
    
    public void setVertice(char vertice) {
        this.vertice = vertice;
    }
    
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
    
    public void setPadre(int padre) {
        this.padre = padre;
    }
    
    public void setMarca(boolean marca) {
        this.marca = marca;
    }
    
    public void agregarArista(int destino, int peso) {
        lista.add(new Arista(destino, peso));
    }
}
