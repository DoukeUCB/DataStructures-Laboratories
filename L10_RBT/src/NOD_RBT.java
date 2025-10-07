class NOD_RBT<T extends Comparable<T>> {
    private T el;
    private NOD_RBT<T> left;
    private NOD_RBT<T> right;
    private boolean color;
    private int contador;
    
    public static final boolean ROJO = true;
    public static final boolean NEGRO = false;
    
    public NOD_RBT(T el) {
        this.el = el;
        this.left = null;
        this.right = null;
        this.color = ROJO;
        this.contador = 1;
    }
    
    public NOD_RBT(T el, boolean color) {
        this.el = el;
        this.left = null;
        this.right = null;
        this.color = color;
        this.contador = 1;
    }
    
    public T getElement() {
        return el;
    }
    
    public void setElement(T el) {
        this.el = el;
    }
    
    public NOD_RBT<T> getLeft() {
        return left;
    }
    
    public void setLeft(NOD_RBT<T> left) {
        this.left = left;
    }
    
    public NOD_RBT<T> getRight() {
        return right;
    }
    
    public void setRight(NOD_RBT<T> right) {
        this.right = right;
    }
    
    public boolean getColor() {
        return color;
    }
    
    public void setColor(boolean color) {
        this.color = color;
    }
    
    public int getContador() {
        return contador;
    }
    
    public void incrementarContador() {
        this.contador++;
    }
    
    public boolean esRojo() {
        return color == ROJO;
    }
    
    public boolean esNegro() {
        return color == NEGRO;
    }
    
    public String getColorString() {
        return color == ROJO ? "R" : "N";
    }
}
