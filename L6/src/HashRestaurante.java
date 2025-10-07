public class HashRestaurante {
    private static final int TAM = 101;
    private final Lista8<String>[] VEC;
    
    public HashRestaurante() {
        VEC = new Lista8[TAM];
        for (int i = 0; i < TAM; i++) {
            VEC[i] = new Lista8<>();
        }
    }
    
    public int funcionHash(String ingrediente) {
        int pos = 0;
        for (int i = 0; i < ingrediente.length(); i++) {
            int ascii = ingrediente.charAt(i);
            pos += ascii * Math.pow(2, i);
        }
        return Math.abs(pos) % TAM;
    }
    
    public boolean insertarIngrediente(String ingrediente) {
        if (ingrediente == null || ingrediente.trim().isEmpty()) {
            return false;
        }
        
        ingrediente = ingrediente.trim().toLowerCase();
        int posicion = funcionHash(ingrediente);
        
        if (!VEC[posicion].buscarSecuencialmente(ingrediente)) {
            VEC[posicion].insertarFinal(ingrediente);
            return true;
        }
        
        return false;
    }
    
    public boolean buscarIngrediente(String ingrediente) {
        if (ingrediente == null || ingrediente.trim().isEmpty()) {
            return false;
        }
        
        ingrediente = ingrediente.trim().toLowerCase();
        int posicion = funcionHash(ingrediente);
        
        return VEC[posicion].buscarSecuencialmente(ingrediente);
    }
    
    public void mostrarIngredientes() {
        boolean hayElementos = false;
        
        for (int i = 0; i < TAM; i++) {
            if (!VEC[i].estaVacia()) {
                if (!hayElementos) {
                    System.out.print("[ ");
                    hayElementos = true;
                }
                VEC[i].mostrarLista();
                System.out.print(" ");
            }
        }
        
        if (hayElementos) {
            System.out.print("]");
        } else {
            System.out.print("Sin ingredientes");
        }
    }
    
    public int contarIngredientes() {
        int total = 0;
        for (int i = 0; i < TAM; i++) {
            total += VEC[i].obtenerTamaño();
        }
        return total;
    }
    
    public int getTamaño() {
        return TAM;
    }
    
    public void mostrarDistribucionHash() {
        System.out.println("\n=== DISTRIBUCIÓN DE LA TABLA HASH ===");
        System.out.println("Tamaño de la tabla: " + TAM);
        
        int posicionesOcupadas = 0;
        int totalColisiones = 0;
        int maxColisiones = 0;
        
        for (int i = 0; i < TAM; i++) {
            int tamaño = VEC[i].obtenerTamaño();
            if (tamaño > 0) {
                posicionesOcupadas++;
                if (tamaño > 1) {
                    totalColisiones += (tamaño - 1);
                    if (tamaño > maxColisiones) {
                        maxColisiones = tamaño;
                    }
                }
                System.out.print("Posición " + i + " (" + tamaño + " elementos): ");
                VEC[i].mostrarLista();
            }
        }
        
        System.out.println("\n=== ESTADÍSTICAS ===");
        System.out.println("Posiciones ocupadas: " + posicionesOcupadas + "/" + TAM);
        System.out.println("Total de ingredientes: " + contarIngredientes());
        System.out.println("Total de colisiones: " + totalColisiones);
        System.out.println("Máximo elementos en una posición: " + maxColisiones);
        System.out.println("Factor de carga: " + String.format("%.2f", (double) contarIngredientes() / TAM));
    }
}
