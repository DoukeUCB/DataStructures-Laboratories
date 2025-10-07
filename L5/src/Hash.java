package src;
import L6.Lista8;

public class Hash {
    private static final int TAM = 30011;
    private final Lista8<String>[] VEC;
    
    public Hash() {
        VEC = new Lista8[TAM];
        for (int i = 0; i < TAM; i++) {
            VEC[i] = new Lista8<>();
        }
    }
    
    // ASCII
    public int funcionHash(String nom) {
        int pos = 0;
        for (int i = 0; i < nom.length(); i++) {
            pos += nom.charAt(i) * Math.pow(2, i);
        }
        pos = Math.abs(pos) % TAM;
        return pos;
    }
    
    // Multiplicacion
    public int funcionHashMultiplicacion(String nom) {
        final double A = 0.6180339887;
        int elem = 0;
        for (int i = 0; i < nom.length(); i++) {
            elem += nom.charAt(i);
        }
        elem = Math.abs(elem);
        double mult = elem * A;
        double piso = Math.floor(mult);
        double fraccion = mult - piso;
        return (int) Math.floor(TAM * fraccion);
    }
    
    public boolean insertarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        
        nombre = nombre.trim();
        int posicion = funcionHash(nombre);
        
        if (!VEC[posicion].buscarSecuencialmente(nombre)) {
            VEC[posicion].insertarFinal(nombre);
            return true;
        }
        
        return false;
    }
    
    public void insertarPalabraConContador(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            return;
        }
        
        palabra = palabra.trim().toLowerCase();
        int posicion = funcionHash(palabra);
        VEC[posicion].insertarConContador(palabra);
    }
    
    public void insertarPalabraConContadorMultiplicacion(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            return;
        }
        
        palabra = palabra.trim().toLowerCase();
        int posicion = funcionHashMultiplicacion(palabra);
        VEC[posicion].insertarConContador(palabra);
    }
    
    public void mostrarTablaConContadores() {
        System.out.println("\n=== TABLA HASH CON CONTADORES ===");
        int posicionesOcupadas = 0;
        
        for (int i = 0; i < TAM; i++) {
            if (!VEC[i].estaVacia()) {
                posicionesOcupadas++;
                System.out.print("Posicion " + i + ": ");
                VEC[i].mostrarListaConContadores();
            }
        }
        
        System.out.println("Posiciones ocupadas: " + posicionesOcupadas + "/" + TAM);
    }
    
    public void limpiarTabla() {
        for (int i = 0; i < TAM; i++) {
            VEC[i] = new Lista8<>();
        }
    }
    
    public boolean eliminarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        
        nombre = nombre.trim();
        int posicion = funcionHash(nombre);
        
        return VEC[posicion].buscarYEliminar(nombre);
    }
    
    public boolean buscarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        
        nombre = nombre.trim();
        int posicion = funcionHash(nombre);
        
        return VEC[posicion].buscarSecuencialmente(nombre);
    }
    
    public void mostrarTablaHash() {
        System.out.println("\n=== TABLA HASH DE TRABAJADORES ===");
        System.out.println("Tamaño de la tabla: " + TAM);
        
        int totalElementos = 0;
        int posicionesOcupadas = 0;
        
        for (int i = 0; i < TAM; i++) {
            if (!VEC[i].estaVacia()) {
                posicionesOcupadas++;
                int tamaño = VEC[i].obtenerTamaño();
                totalElementos += tamaño;
                
                System.out.print("Posicion " + i + " (" + tamaño + " elementos): ");
                VEC[i].mostrarLista();
            }
        }
        
        System.out.println("\nEstadisticas:");
        System.out.println("Total de elementos: " + totalElementos);
        System.out.println("Posiciones ocupadas: " + posicionesOcupadas + "/" + TAM);
        System.out.println("Factor de carga: " + String.format("%.2f", (double) totalElementos / TAM));
        System.out.println("Promedio por posicion ocupada: " + 
                          String.format("%.2f", posicionesOcupadas > 0 ? (double) totalElementos / posicionesOcupadas : 0));
    }
    
    public void mostrarSoloOcupadas() {
        System.out.println("\n=== POSICIONES OCUPADAS ===");
        boolean hayElementos = false;
        
        for (int i = 0; i < TAM; i++) {
            if (!VEC[i].estaVacia()) {
                hayElementos = true;
                System.out.print("Posicion " + i + ": ");
                VEC[i].mostrarLista();
            }
        }
        
        if (!hayElementos) {
            System.out.println("La tabla hash esta vacia.");
        }
    }
    
    public void mostrarPosicion(int posicion) {
        if (posicion >= 0 && posicion < TAM) {
            System.out.println("\nPosicion " + posicion + ":");
            if (VEC[posicion].estaVacia()) {
                System.out.println("  Vacia");
            } else {
                System.out.print("  ");
                VEC[posicion].mostrarLista();
                System.out.println("  Cantidad de elementos: " + VEC[posicion].obtenerTamaño());
            }
        } else {
            System.out.println("Posicion fuera de rango (0-" + (TAM-1) + ")");
        }
    }
    
    public int getTamaño() {
        return TAM;
    }
    
    public int contarElementos() {
        int total = 0;
        for (int i = 0; i < TAM; i++) {
            total += VEC[i].obtenerTamaño();
        }
        return total;
    }
}
