class NOD {
    private NOD hijo;
    private NOD hermano;
    private boolean fin;
    private char letra;
    
    public NOD(char letra) {
        this.letra = letra;
        this.hijo = null;
        this.hermano = null;
        this.fin = false;
    }
    
    public NOD getHijo() {
        return hijo;
    }
    
    public NOD getHermano() {
        return hermano;
    }
    
    public boolean isFin() {
        return fin;
    }
    
    public char getLetra() {
        return letra;
    }
    
    public void setHijo(NOD hijo) {
        this.hijo = hijo;
    }
    
    public void setHermano(NOD hermano) {
        this.hermano = hermano;
    }
    
    public void setFin(boolean fin) {
        this.fin = fin;
    }
    
    public void setLetra(char letra) {
        this.letra = letra;
    }
}
