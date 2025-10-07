class BST<T extends Comparable<T>> {
    private NOD<T> R;
    
    public BST() {
        this.R = null;
    }
    
    public NOD<T> insertar(NOD<T> R, T el) {
        if (R == null) {
            R = new NOD<>(el);
        } else {
            if (el.compareTo(R.getElement()) < 0) {
                R.setLeft(insertar(R.getLeft(), el));
            } else if (el.compareTo(R.getElement()) > 0) {
                R.setRight(insertar(R.getRight(), el));
            }
        }
        return R;
    }
    
    public int contarNodos(NOD<T> R) {
        if (R == null) {
            return 0;
        }
        return 1 + contarNodos(R.getLeft()) + contarNodos(R.getRight());
    }
    
    public int calcularAltura(NOD<T> R) {
        if (R == null) {
            return 0;
        }
        int alturaIzq = calcularAltura(R.getLeft());
        int alturaDer = calcularAltura(R.getRight());
        return 1 + Math.max(alturaIzq, alturaDer);
    }
    
    public void mostrarInOrder(NOD<T> R) {
        if (R != null) {
            mostrarInOrder(R.getLeft());
            System.out.print(R.getElement() + " ");
            mostrarInOrder(R.getRight());
        }
    }
 
    public void mostrarNivel(NOD<T> R, int nivelDeseado, int nivelActual) {
        if (R == null) {
            return;
        }
        if (nivelActual == nivelDeseado) {
            System.out.print(R.getElement() + " ");
        } else {
            mostrarNivel(R.getLeft(), nivelDeseado, nivelActual + 1);
            mostrarNivel(R.getRight(), nivelDeseado, nivelActual + 1);
        }
    }
    
    public void mostrarPorNiveles(NOD<T> R) {
        int altura = calcularAltura(R);
        System.out.println("Árbol por niveles:");
        for (int i = 1; i <= altura; i++) {
            System.out.print("Nivel " + i + ": ");
            mostrarNivel(R, i, 1);
            System.out.println();
        }
    }
    
    public NOD<T> buscarElemento(T el, NOD<T> R) {
        NOD<T> resp;
        if (R == null) {
            resp = null;
        } else {
            if (R.getElement().compareTo(el) == 0) {
                resp = R;
            } else {
                if (el.compareTo(R.getElement()) < 0) {
                    resp = buscarElemento(el, R.getLeft());
                } else {
                    resp = buscarElemento(el, R.getRight());
                }
            }
        }
        return resp;
    }
    
    public NOD<T> menor(NOD<T> R) {
        if (R == null) {
            return null;
        }
        if (R.getLeft() == null) {
            return R;
        }
        return menor(R.getLeft());
    }
    
    public NOD<T> mayor(NOD<T> R) {
        if (R == null) {
            return null;
        }
        if (R.getRight() == null) {
            return R;
        }
        return mayor(R.getRight());
    }
    
    public NOD<T> getRaiz() {
        return R;
    }
    
    public void setRaiz(NOD<T> R) {
        this.R = R;
    }
    
    public boolean estaVacio() {
        return R == null;
    }
    
    private NOD<T> encontrarPadre(NOD<T> raiz, NOD<T> hijo) {
        if (raiz == null || raiz == hijo) {
            return null;
        }
        
        if ((raiz.getLeft() == hijo) || (raiz.getRight() == hijo)) {
            return raiz;
        }
        
        NOD<T> padre = encontrarPadre(raiz.getLeft(), hijo);
        if (padre != null) {
            return padre;
        }
        
        return encontrarPadre(raiz.getRight(), hijo);
    }
    
    public boolean eliminar(NOD<T> raiz, T el) {
        boolean resp;
        
        if (raiz == null) {
            resp = false;
            return resp;
        }
        
        NOD<T> nodoAEliminar = buscarElemento(el, raiz);
        
        if (nodoAEliminar == null) {
            resp = false;
            return resp;
        }
        
        if (nodoAEliminar.getLeft() == null && nodoAEliminar.getRight() == null) {
            NOD<T> padre = encontrarPadre(R, nodoAEliminar);
            
            if (padre == null) {
                R = null;
            } else {
                if (padre.getLeft() == nodoAEliminar) {
                    padre.setLeft(null);
                } else {
                    padre.setRight(null);
                }
            }
            resp = true;
        }
        else {
            int nodosIzquierdo = contarNodos(nodoAEliminar.getLeft());
            int nodosDerecho = contarNodos(nodoAEliminar.getRight());
            
            NOD<T> reemplazo;
            
            if (nodosIzquierdo > nodosDerecho) {
                reemplazo = mayor(nodoAEliminar.getLeft());
            } else {
                reemplazo = menor(nodoAEliminar.getRight());
            }
            
            T elementoReemplazo = reemplazo.getElement();
            
            eliminar(R, elementoReemplazo);
            
            nodoAEliminar.setElement(elementoReemplazo);
            
            resp = true;
        }
        
        return resp;
    }
}