class NOD<T extends Comparable<T>> {
    private T el;
    private NOD<T> left;
    private NOD<T> right;
    
    public NOD(T el) {
        this.el = el;
        this.left = null;
        this.right = null;
    }
    
    public T getElement() {
        return el;
    }
    
    public void setElement(T el) {
        this.el = el;
    }
    
    public NOD<T> getLeft() {
        return left;
    }
    
    public void setLeft(NOD<T> left) {
        this.left = left;
    }
    
    public NOD<T> getRight() {
        return right;
    }
    
    public void setRight(NOD<T> right) {
        this.right = right;
    }
}
