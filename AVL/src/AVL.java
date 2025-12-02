class AVL<T extends Comparable<T>> {
    private NOD_AVL<T> raiz;
    
    public AVL() {
        this.raiz = null;
    }
    
    private int altura(NOD_AVL<T> nodo) {
        return (nodo == null) ? -1 : nodo.getAltura();
    }
    
    private int factorBalance(NOD_AVL<T> nodo) {
        return (nodo == null) ? 0 : altura(nodo.getR()) - altura(nodo.getL());
    }
    
    private void actualizarAltura(NOD_AVL<T> nodo) {
        if (nodo != null) {
            int altL = altura(nodo.getL());
            int altR = altura(nodo.getR());
            nodo.setAltura((byte)(1 + Math.max(altL, altR)));
        }
    }
    
    private NOD_AVL<T> rotarIzq(NOD_AVL<T> nodo) {
        NOD_AVL<T> temp = nodo.getR();
        nodo.setR(temp.getL());
        temp.setL(nodo);
        actualizarAltura(nodo);
        actualizarAltura(temp);
        return temp;
    }
    
    private NOD_AVL<T> rotarDer(NOD_AVL<T> nodo) {
        NOD_AVL<T> temp = nodo.getL();
        nodo.setL(temp.getR());
        temp.setR(nodo);
        actualizarAltura(nodo);
        actualizarAltura(temp);
        return temp;
    }
    
    private NOD_AVL<T> rotarIzqDer(NOD_AVL<T> nodo) {
        nodo.setL(rotarIzq(nodo.getL()));
        return rotarDer(nodo);
    }
    
    private NOD_AVL<T> rotarDerIzq(NOD_AVL<T> nodo) {
        nodo.setR(rotarDer(nodo.getR()));
        return rotarIzq(nodo);
    }
    
    private NOD_AVL<T> balancear(NOD_AVL<T> nodo) {
        actualizarAltura(nodo);
        int fb = factorBalance(nodo);
        
        if (fb == -2) {
            if (factorBalance(nodo.getL()) <= 0) {
                nodo = rotarDer(nodo);
            } else {
                nodo = rotarIzqDer(nodo);
            }
        } else if (fb == 2) {
            if (factorBalance(nodo.getR()) >= 0) {
                nodo = rotarIzq(nodo);
            } else {
                nodo = rotarDerIzq(nodo);
            }
        }
        return nodo;
    }
    
    public void insertar(T el) {
        raiz = insertar(raiz, el);
    }
    
    private NOD_AVL<T> insertar(NOD_AVL<T> nodo, T el) {
        if (nodo == null) {
            return new NOD_AVL<>(el);
        }
        
        int cmp = el.compareTo(nodo.getEl());
        if (cmp < 0) {
            nodo.setL(insertar(nodo.getL(), el));
        } else if (cmp > 0) {
            nodo.setR(insertar(nodo.getR(), el));
        } else {
            return nodo; // Duplicado, no insertar
        }
        
        return balancear(nodo);
    }
    
    public NOD_AVL<T> buscarMenor(NOD_AVL<T> nodo) {
        if (nodo == null) return null;
        if (nodo.getL() == null) return nodo;
        return buscarMenor(nodo.getL());
    }
    
    public NOD_AVL<T> buscarMayor(NOD_AVL<T> nodo) {
        if (nodo == null) return null;
        if (nodo.getR() == null) return nodo;
        return buscarMayor(nodo.getR());
    }
    
    private NOD_AVL<T> eliminarMin(NOD_AVL<T> nodo) {
        if (nodo.getL() == null) {
            return nodo.getR();
        }
        nodo.setL(eliminarMin(nodo.getL()));
        return balancear(nodo);
    }
    
    public boolean eliminar(T el) {
        int nodosAntes = contarNodos(raiz);
        raiz = eliminar(raiz, el);
        return contarNodos(raiz) < nodosAntes;
    }
    
    private NOD_AVL<T> eliminar(NOD_AVL<T> nodo, T el) {
        if (nodo == null) {
            return null;
        }
        
        int cmp = el.compareTo(nodo.getEl());
        if (cmp < 0) {
            nodo.setL(eliminar(nodo.getL(), el));
        } else if (cmp > 0) {
            nodo.setR(eliminar(nodo.getR(), el));
        } else {
            if (nodo.getL() == null) {
                return nodo.getR();
            }
            
            if (nodo.getR() == null) {
                return nodo.getL();
            }
            
            NOD_AVL<T> temp = nodo;
            nodo = buscarMenor(temp.getR()); // Encontrar sucesor
            nodo.setR(eliminarMin(temp.getR())); // Eliminar el sucesor de su posición original
            nodo.setL(temp.getL()); // Mantener el subárbol izquierdo
        }
        
        return balancear(nodo);
    }
    
    public NOD_AVL<T> buscar(T el, NOD_AVL<T> nodo) {
        if (nodo == null) {
            return null;
        }
        
        int cmp = el.compareTo(nodo.getEl());
        if (cmp == 0) {
            return nodo;
        } else if (cmp < 0) {
            return buscar(el, nodo.getL());
        } else {
            return buscar(el, nodo.getR());
        }
    }
    
    public int contarNodos(NOD_AVL<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getL()) + contarNodos(nodo.getR());
    }
    
    public int calcularAltura(NOD_AVL<T> nodo) {
        return altura(nodo);
    }
    
    public int obtenerFactorBalance(NOD_AVL<T> nodo) {
        return factorBalance(nodo);
    }
    
    public void mostrarInOrder(NOD_AVL<T> nodo) {
        if (nodo != null) {
            mostrarInOrder(nodo.getL());
            System.out.print(nodo.getEl() + "[" + factorBalance(nodo) + "] ");
            mostrarInOrder(nodo.getR());
        }
    }
    
    public void eliminarTodo() {
        raiz = null;
        System.out.println("Árbol eliminado");
    }
    
    public NOD_AVL<T> getRaiz() {
        return raiz;
    }
    
    public boolean estaVacio() {
        return raiz == null;
    }
}
