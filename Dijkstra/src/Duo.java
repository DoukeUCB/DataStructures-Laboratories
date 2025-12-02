public class Duo implements Comparable<Duo> {
    private int distancia;
    private int vertice;
    
    public Duo(int distancia, int vertice) {
        this.distancia = distancia;
        this.vertice = vertice;
    }
    
    public int getDistancia() {
        return distancia;
    }
    
    public int getVertice() {
        return vertice;
    }
    
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
    
    public void setVertice(int vertice) {
        this.vertice = vertice;
    }
    
    @Override
    public int compareTo(Duo otro) {
        // Comparación para HEAP MIN (menor distancia primero)
        return Integer.compare(this.distancia, otro.distancia);
    }
    
    @Override
    public String toString() {
        return "[dist=" + distancia + ", vert=" + vertice + "]";
    }
}
