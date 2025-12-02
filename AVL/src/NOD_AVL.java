class NOD_AVL<T extends Comparable<T>> {
    private T el;
    private NOD_AVL<T> L;
    private NOD_AVL<T> R;
    private byte altura;
    
    public NOD_AVL(T el) {
        this.el = el;
        this.L = null;
        this.R = null;
        this.altura = 0;
    }
    
    public T getEl() {
        return el;
    }
    
    public void setEl(T el) {
        this.el = el;
    }
    
    public NOD_AVL<T> getL() {
        return L;
    }
    
    public void setL(NOD_AVL<T> L) {
        this.L = L;
    }
    
    public NOD_AVL<T> getR() {
        return R;
    }
    
    public void setR(NOD_AVL<T> R) {
        this.R = R;
    }
    
    public byte getFact() {
        return altura;
    }
    
    public void setFact(byte altura) {
        this.altura = altura;
    }
    
    public byte getAltura() {
        return altura;
    }
    
    public void setAltura(byte altura) {
        this.altura = altura;
    }
}
