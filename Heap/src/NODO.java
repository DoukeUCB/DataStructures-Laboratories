class NODO<T extends Comparable<T>> {
    private T key;
    private boolean presente;
    
    public NODO() {
        this.key = null;
        this.presente = false;
    }
    
    public NODO(T key) {
        this.key = key;
        this.presente = true;
    }
    
    public T getKey() {
        return key;
    }
    
    public void setKey(T key) {
        this.key = key;
    }
    
    public boolean isPresente() {
        return presente;
    }
    
    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}
