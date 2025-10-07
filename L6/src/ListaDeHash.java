public class ListaDeHash {
    private Nodo PRI;
    private Nodo ULT;
    
    public ListaDeHash() {
        this.PRI = null;
        this.ULT = null;
    }
    
    public boolean estaVacia() {
        return PRI == null;
    }
    
    public void insertarPlato(String nombrePlato) {
        Nodo nuevo = new Nodo(nombrePlato);
        
        if (estaVacia()) {
            PRI = nuevo;
            ULT = nuevo;
        } else {
            ULT.setSiguiente(nuevo);
            nuevo.setAnterior(ULT);
            ULT = nuevo;
        }
    }
    
    public Nodo buscarPlato(String nombrePlato) {
        Nodo actual = PRI;
        
        while (actual != null) {
            if (actual.getNombre().equalsIgnoreCase(nombrePlato)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        
        return null;
    }
    
    public boolean agregarIngredienteAPlato(String nombrePlato, String ingrediente) {
        Nodo plato = buscarPlato(nombrePlato);
        
        if (plato != null) {
            plato.agregarIngrediente(ingrediente);
            return true;
        }
        
        return false;
    }
    
    public void mostrarTodosLosPlatos() {
        if (estaVacia()) {
            System.out.println("No hay platos en el restaurante.");
            return;
        }
        
        System.out.println("\n=== TODOS LOS PLATOS DEL RESTAURANTE ===");
        Nodo actual = PRI;
        int contador = 1;
        
        while (actual != null) {
            System.out.print(contador + ". ");
            actual.mostrarIngredientes();
            System.out.println();
            actual = actual.getSiguiente();
            contador++;
        }
    }
    
    public void mostrarPlatosSinIngrediente(String ingrediente) {
        if (estaVacia()) {
            System.out.println("No hay platos en el restaurante.");
            return;
        }
        
        System.out.println("\n=== PLATOS QUE NO TIENEN: " + ingrediente.toUpperCase() + " ===");
        Nodo actual = PRI;
        int contador = 0;
        
        while (actual != null) {
            if (!actual.tieneIngrediente(ingrediente)) {
                contador++;
                System.out.print(contador + ". ");
                actual.mostrarIngredientes();
                System.out.println();
            }
            actual = actual.getSiguiente();
        }
        
        if (contador == 0) {
            System.out.println("Todos los platos contienen el ingrediente: " + ingrediente);
        }
    }
    
    public int contarPlatos() {
        int contador = 0;
        Nodo actual = PRI;
        
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        
        return contador;
    }
    
    public void mostrarEstadisticas() {
        System.out.println("\n=== ESTADISTICAS DEL RESTAURANTE ===");
        System.out.println("Total de platos: " + contarPlatos());
        
        if (!estaVacia()) {
            Nodo actual = PRI;
            int totalIngredientes = 0;
            int minIngredientes = Integer.MAX_VALUE;
            int maxIngredientes = 0;
            
            while (actual != null) {
                int ingredientesPlato = actual.getHash().contarIngredientes();
                totalIngredientes += ingredientesPlato;
                
                if (ingredientesPlato < minIngredientes) {
                    minIngredientes = ingredientesPlato;
                }
                
                if (ingredientesPlato > maxIngredientes) {
                    maxIngredientes = ingredientesPlato;
                }
                
                actual = actual.getSiguiente();
            }
            
            double promedio = (double) totalIngredientes / contarPlatos();
            System.out.println("Total de ingredientes: " + totalIngredientes);
            System.out.println("Promedio de ingredientes por plato: " + String.format("%.2f", promedio));
            System.out.println("Mínimo de ingredientes en un plato: " + minIngredientes);
            System.out.println("Máximo de ingredientes en un plato: " + maxIngredientes);
        }
    }
    
    public void mostrarDistribucionesTodosLosPlatos() {
        if (estaVacia()) {
            System.out.println("No hay platos en el restaurante.");
            return;
        }
        
        Nodo actual = PRI;
        int contador = 1;
        
        while (actual != null) {
            System.out.println("\n" + contador + ". PLATO: " + actual.getNombre().toUpperCase());
            actual.getHash().mostrarDistribucionHash();
            actual = actual.getSiguiente();
            contador++;
        }
    }
}
