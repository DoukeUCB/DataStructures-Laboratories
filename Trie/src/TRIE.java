class TRIE {
    private NOD R;
    
    public TRIE() {
        this.R = null;
    }
    
    public void insertar(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            System.out.println("Palabra vacía no válida.");
            return;
        }
        R = insertar(palabra.toLowerCase(), R, 0);
    }
    
    private NOD insertar(String pal, NOD nodo, int i) {
        // Caso 1: Si el nodo es NULL
        if (nodo == null) {
            if (i < pal.length()) {
                nodo = new NOD(pal.charAt(i));
                
                // Si es la última letra, marcar fin
                if (i == pal.length() - 1) {
                    nodo.setFin(true);
                } else {
                    // Continuar con el siguiente carácter en el hijo
                    nodo.setHijo(insertar(pal, nodo.getHijo(), i + 1));
                }
            }
            return nodo;
        }
        
        // Caso 2: Si la letra actual coincide con el nodo
        if (pal.charAt(i) == nodo.getLetra()) {
            // Si es la última letra, marcar fin
            if (i == pal.length() - 1) {
                nodo.setFin(true);
            } else {
                // Continuar con el siguiente carácter en el hijo
                nodo.setHijo(insertar(pal, nodo.getHijo(), i + 1));
            }
            return nodo;
        }
        
        // Caso 3: La letra no coincide, buscar en hermanos
        nodo.setHermano(insertar(pal, nodo.getHermano(), i));
        return nodo;
    }
    
    public boolean buscar(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return false;
        }
        return buscar(palabra.toLowerCase(), R, 0);
    }
    
    private boolean buscar(String pal, NOD nodo, int i) {
        if (nodo == null) {
            return false;
        }
        
        if (pal.charAt(i) == nodo.getLetra()) {
            // Si es la última letra, verificar si es fin de palabra
            if (i == pal.length() - 1) {
                return nodo.isFin();
            }
            // Continuar buscando en los hijos
            return buscar(pal, nodo.getHijo(), i + 1);
        }
        
        // Buscar en hermanos
        return buscar(pal, nodo.getHermano(), i);
    }
    
    // Mostrar palabras que empiezan con un prefijo
    public void mostrarPalabrasCon(String prefijo) {
        if (prefijo == null || prefijo.isEmpty()) {
            System.out.println("Prefijo vacío.");
            return;
        }
        
        String prefijoLower = prefijo.toLowerCase();
        NOD nodo = buscarPrefijo(prefijoLower, R, 0);
        
        if (nodo == null) {
            System.out.println("No se encontraron palabras con el prefijo: " + prefijo);
            return;
        }
        
        System.out.println("Palabras que empiezan con '" + prefijo + "':");
        
        // Si el prefijo mismo es una palabra
        if (nodo.isFin()) {
            System.out.println("  - " + prefijoLower);
        }
        
        // Mostrar todas las palabras que continúan desde este nodo
        mostrarPalabras(nodo.getHijo(), prefijoLower);
    }
    
    // Buscar el nodo donde termina el prefijo
    private NOD buscarPrefijo(String prefijo, NOD nodo, int i) {
        if (nodo == null) {
            return null;
        }
        
        if (prefijo.charAt(i) == nodo.getLetra()) {
            if (i == prefijo.length() - 1) {
                return nodo;
            }
            // Continuar en los hijos
            return buscarPrefijo(prefijo, nodo.getHijo(), i + 1);
        }
        
        // Buscar en hermanos
        return buscarPrefijo(prefijo, nodo.getHermano(), i);
    }
    
    private void mostrarPalabras(NOD nodo, String prefijo) {
        if (nodo == null) {
            return;
        }
        
        String nuevaPalabra = prefijo + nodo.getLetra();
        
        if (nodo.isFin()) {
            System.out.println("  - " + nuevaPalabra);
        }
        
        // Explorar hijos (profundidad)
        mostrarPalabras(nodo.getHijo(), nuevaPalabra);
        
        // Explorar hermanos (mismo nivel)
        mostrarPalabras(nodo.getHermano(), prefijo);
    }
    
    public boolean estaVacio() {
        return R == null;
    }
    
    public void mostrarTodas() {
        if (estaVacio()) {
            System.out.println("El Trie está vacío.");
            return;
        }
        
        System.out.println("Todas las palabras en el Trie:");
        mostrarPalabras(R, "");
    }
    
    public int buscarPalabras(String palabra) {
        return buscarPalabras(palabra, 0, R, 0);
    }
    
    private int buscarPalabras(String palabra, int pos, NOD nodo, int inicio) {
        // Caso base: palabra vacía o nula
        if (palabra == null || palabra.isEmpty()) {
            return 0;
        }
        
        // Si hemos procesado todas las posiciones de inicio posibles
        if (inicio >= palabra.length()) {
            return 0;
        }
        
        // Si el nodo es null o la posición actual está fuera de rango
        if (nodo == null || pos >= palabra.length()) {
            // Probar desde la siguiente posición de inicio
            return buscarPalabras(palabra, inicio + 1, R, inicio + 1);
        }
        
        int contador = 0;
        char letraBuscada = Character.toLowerCase(palabra.charAt(pos));
        
        // Si la letra coincide
        if (letraBuscada == nodo.getLetra()) {
            // Si este nodo marca fin de palabra
            if (nodo.isFin()) {
                int longitud = (pos - inicio) + 1;
                
                // Solo contar si NO es la palabra completa
                if (inicio != 0 || longitud < palabra.length()) {
                    contador++;
                }
            }
            
            // Continuar en los hijos (siguiente letra)
            contador += buscarPalabras(palabra, pos + 1, nodo.getHijo(), inicio);
        }
        
        // Buscar en hermanos (misma posición, diferente letra)
        contador += buscarPalabras(palabra, pos, nodo.getHermano(), inicio);
        
        return contador;
    }
}
