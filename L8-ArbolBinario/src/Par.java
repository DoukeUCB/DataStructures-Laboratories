class Par<T> {
    private boolean bandera;
    private T elem;
    
    public Par() {
        this.bandera = false;
        this.elem = null;
    }
    
    public Par(T elem) {
        this.bandera = true;
        this.elem = elem;
    }
    
    public boolean getBandera() {
        return bandera;
    }
    
    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }
    
    public T getElem() {
        return elem;
    }
    
    public void setElem(T elem) {
        this.elem = elem;
    }
    
    @Override
    public String toString() {
        return bandera ? elem.toString() : "vacío";
    }
}