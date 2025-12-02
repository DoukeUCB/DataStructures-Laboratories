class RBT<T extends Comparable<T>> {
    private NOD_RBT<T> R;
    
    public RBT() {
        this.R = null;
    }
    
    private boolean esRojo(NOD_RBT<T> nodo) {
        if (nodo == null) {
            return false;
        }
        return nodo.esRojo();
    }
    
    private NOD_RBT<T> rotarIzquierda(NOD_RBT<T> nodo) {
        NOD_RBT<T> temp = nodo.getRight();
        nodo.setRight(temp.getLeft());
        temp.setLeft(nodo);
        
        temp.setColor(nodo.getColor());
        nodo.setColor(NOD_RBT.ROJO);
        
        return temp;
    }
    
    private NOD_RBT<T> rotarDerecha(NOD_RBT<T> nodo) {
        NOD_RBT<T> temp = nodo.getLeft();
        nodo.setLeft(temp.getRight());
        temp.setRight(nodo);
        
        temp.setColor(nodo.getColor());
        nodo.setColor(NOD_RBT.ROJO);
        
        return temp;
    }
    
    private void cambiarColores(NOD_RBT<T> nodo) {
        nodo.setColor(NOD_RBT.ROJO);
        nodo.getLeft().setColor(NOD_RBT.NEGRO);
        nodo.getRight().setColor(NOD_RBT.NEGRO);
    }
    
    public void insertar(T el) {
        R = insertar(R, el);
        R.setColor(NOD_RBT.NEGRO);
    }
    
    private NOD_RBT<T> insertar(NOD_RBT<T> nodo, T el) {
        if (nodo == null) {
            return new NOD_RBT<>(el, NOD_RBT.ROJO);
        }
        
        int comparacion = el.compareTo(nodo.getElement());
        if (comparacion < 0) {
            nodo.setLeft(insertar(nodo.getLeft(), el));
        } else if (comparacion > 0) {
            nodo.setRight(insertar(nodo.getRight(), el));
        } else {
            nodo.incrementarContador();
            return nodo;
        }
        
        if (esRojo(nodo.getRight()) && !esRojo(nodo.getLeft())) {
            nodo = rotarIzquierda(nodo);
        }
        
        if (esRojo(nodo.getLeft()) && esRojo(nodo.getLeft().getLeft())) {
            nodo = rotarDerecha(nodo);
        }
        
        if (esRojo(nodo.getLeft()) && esRojo(nodo.getRight())) {
            cambiarColores(nodo);
        }
        
        return nodo;
    }
    
    public int contarNodos(NOD_RBT<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getLeft()) + contarNodos(nodo.getRight());
    }
    
    public int calcularAltura(NOD_RBT<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        int alturaIzq = calcularAltura(nodo.getLeft());
        int alturaDer = calcularAltura(nodo.getRight());
        return 1 + Math.max(alturaIzq, alturaDer);
    }
    
    public void mostrarInOrder(NOD_RBT<T> nodo) {
        if (nodo != null) {
            mostrarInOrder(nodo.getLeft());
            System.out.print(nodo.getElement() + "(" + nodo.getColorString() + ":" + nodo.getContador() + ") ");
            mostrarInOrder(nodo.getRight());
        }
    }
    
    public NOD_RBT<T> buscarElemento(T el, NOD_RBT<T> nodo) {
        if (nodo == null) {
            return null;
        }
        
        int comparacion = el.compareTo(nodo.getElement());
        if (comparacion == 0) {
            return nodo;
        } else if (comparacion < 0) {
            return buscarElemento(el, nodo.getLeft());
        } else {
            return buscarElemento(el, nodo.getRight());
        }
    }
    
    public NOD_RBT<T> getRaiz() {
        return R;
    }
    
    public boolean estaVacio() {
        return R == null;
    }
    
    public void eliminarTodo() {
        R = null;
    }
}

