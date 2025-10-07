/**
 * Hash Table optimizada para clasificar palabras por longitud
 * Usa la longitud de palabra como clave directa (hash simple)
 * TAM = 50 para palabras de hasta 49 caracteres
 */
public class HashPorLongitud {
    private static final int TAM = 50; // Para palabras de hasta 49 caracteres
    private final Lista8<String>[] VEC;
    
    @SuppressWarnings("unchecked")
    public HashPorLongitud() {
        VEC = new Lista8[TAM];
        for (int i = 0; i < TAM; i++) {
            VEC[i] = new Lista8<>();
        }
    }
    
    /**
     * Función hash simple: usa la longitud directamente como índice
     * O(1) - más eficiente que hash complejo para este caso
     */
    public int funcionHash(String palabra) {
        int longitud = palabra.length();
        // Asegurar que no exceda el tamaño del arreglo
        return longitud < TAM ? longitud : TAM - 1;
    }
    
    /**
     * Inserta palabra y cuenta repeticiones por longitud
     */
    public void insertarPalabra(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            return;
        }
        
        palabra = palabra.trim().toLowerCase();
        int posicion = funcionHash(palabra);
        VEC[posicion].insertarConContador(palabra);
    }
    
    /**
     * Cuenta cuántas palabras hay de longitud X
     * O(1) acceso + O(n) recorrido de la lista en esa posición
     */
    public int contarPalabrasDeLongitud(int longitud) {
        if (longitud < 0 || longitud >= TAM) {
            return 0;
        }
        
        return VEC[longitud].contarElementosConContadores();
    }
    
    /**
     * Muestra estadísticas por longitud de palabra
     */
    public void mostrarEstadisticasPorLongitud() {
        System.out.println("\n=== ESTADÍSTICAS POR LONGITUD DE PALABRA ===");
        
        int totalPalabras = 0;
        int longitudMinima = -1;
        int longitudMaxima = -1;
        
        for (int i = 1; i < TAM; i++) { // Empezar desde 1 (palabras de 1 carácter)
            int count = VEC[i].contarElementosConContadores();
            if (count > 0) {
                System.out.printf("Longitud %2d: %4d palabras\n", i, count);
                totalPalabras += count;
                
                if (longitudMinima == -1) longitudMinima = i;
                longitudMaxima = i;
            }
        }
        
        System.out.println("\n=== RESUMEN ===");
        System.out.println("Total de palabras: " + totalPalabras);
        if (longitudMinima != -1) {
            System.out.println("Longitud mínima: " + longitudMinima);
            System.out.println("Longitud máxima: " + longitudMaxima);
        }
    }
    
    /**
     * Muestra palabras de una longitud específica
     */
    public void mostrarPalabrasDeLongitud(int longitud) {
        if (longitud < 0 || longitud >= TAM) {
            System.out.println("Longitud fuera de rango (0-" + (TAM-1) + ")");
            return;
        }
        
        System.out.printf("\n=== PALABRAS DE LONGITUD %d ===\n", longitud);
        
        if (VEC[longitud].estaVacia()) {
            System.out.println("No hay palabras de esta longitud.");
        } else {
            VEC[longitud].mostrarListaConContadores();
            System.out.println();
            System.out.printf("Total: %d palabras\n", VEC[longitud].contarElementosConContadores());
        }
    }
    
    /**
     * Muestra distribución completa ordenada por longitud
     */
    public void mostrarDistribucionOrdenada() {
        System.out.println("\n=== DISTRIBUCIÓN ORDENADA POR LONGITUD ===");
        
        boolean hayPalabras = false;
        
        for (int i = 1; i < TAM; i++) {
            if (!VEC[i].estaVacia()) {
                hayPalabras = true;
                System.out.printf("\nLongitud %d (%d palabras): ", i, VEC[i].contarElementosConContadores());
                VEC[i].mostrarListaConContadores();
            }
        }
        
        if (!hayPalabras) {
            System.out.println("No hay palabras almacenadas.");
        }
    }
    
    /**
     * Limpia todas las palabras
     */
    public void limpiar() {
        for (int i = 0; i < TAM; i++) {
            VEC[i] = new Lista8<>();
        }
    }
    
    /**
     * Retorna el total de palabras únicas
     */
    public int contarPalabrasUnicas() {
        int total = 0;
        for (int i = 0; i < TAM; i++) {
            total += VEC[i].obtenerTamaño();
        }
        return total;
    }
    
    /**
     * Retorna el total de palabras (incluyendo repeticiones)
     */
    public int contarTotalPalabras() {
        int total = 0;
        for (int i = 0; i < TAM; i++) {
            total += VEC[i].contarElementosConContadores();
        }
        return total;
    }
    
    public int getTamaño() {
        return TAM;
    }
}
