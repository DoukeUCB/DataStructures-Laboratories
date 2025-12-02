public class Caja {
    private String destino;
    private int peso;
    private Caja sig;
    
    public Caja(String destino, int peso) {
        this.destino = destino;
        this.peso = peso;
        this.sig = null;
    }
    
    public String getDestino() {
        return destino;
    }
    
    public void setDestino(String destino) {
        this.destino = destino;
    }
    
    public int getPeso() {
        return peso;
    }
    
    public void setPeso(int peso) {
        this.peso = peso;
    }
    
    public Caja getSig() {
        return sig;
    }
    
    public void setSig(Caja sig) {
        this.sig = sig;
    }
}
