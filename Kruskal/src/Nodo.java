public class Nodo implements Comparable<Nodo> {
    private int key;
    private boolean presente;
    private String vo; 
    private String vd;
    
    public Nodo(int key, String vo, String vd) {
        this.key = key;
        this.vo = vo;
        this.vd = vd;
        this.presente = true;
    }
    
    public int getKey() {
        return key;
    }
    
    public void setKey(int key) {
        this.key = key;
    }
    
    public boolean isPresente() {
        return presente;
    }
    
    public void setPresente(boolean presente) {
        this.presente = presente;
    }
    
    public String getVo() {
        return vo;
    }
    
    public void setVo(String vo) {
        this.vo = vo;
    }
    
    public String getVd() {
        return vd;
    }
    
    public void setVd(String vd) {
        this.vd = vd;
    }
    
    @Override
    public int compareTo(Nodo otro) {
        return Integer.compare(this.key, otro.key);
    }
    
    @Override
    public String toString() {
        return vo + " -> " + vd + " [" + key + "]";
    }
}
