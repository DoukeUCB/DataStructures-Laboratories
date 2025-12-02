import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Grafo {
    private Nodo[] vec;
    private int cantidad;
    private static final int MAX = 100;
    
    public Grafo() {
        vec = new Nodo[MAX];
        cantidad = 0;
    }
    
    public void insertarNodo(Object elemento) {
        if (cantidad >= MAX) {
            System.out.println("✗ Grafo lleno, no se puede insertar más nodos.");
            return;
        }
        
        // Verificar si ya existe
        for (int i = 0; i < cantidad; i++) {
            if (vec[i].getEl().equals(elemento)) {
                System.out.println("✗ El nodo ya existe.");
                return;
            }
        }
        
        vec[cantidad] = new Nodo(elemento);
        cantidad++;
        System.out.println("✓ Nodo insertado: " + elemento);
    }
    
    // Buscar índice de un nodo por su elemento
    private int buscarIndice(Object elemento) {
        for (int i = 0; i < cantidad; i++) {
            if (vec[i].getEl().equals(elemento)) {
                return i;
            }
        }
        return -1;
    }
    
    // Insertar arista (conexión entre dos nodos)
    public void insertarArista(Object origen, Object destino) {
        int indiceOrigen = buscarIndice(origen);
        int indiceDestino = buscarIndice(destino);
        
        if (indiceOrigen == -1) {
            System.out.println("✗ Nodo origen no existe. Creándolo...");
            insertarNodo(origen);
            indiceOrigen = cantidad - 1;
        }
        
        if (indiceDestino == -1) {
            System.out.println("✗ Nodo destino no existe. Creándolo...");
            insertarNodo(destino);
            indiceDestino = cantidad - 1;
        }
        
        // Insertar la arista (grafo no dirigido: ambas direcciones)
        vec[indiceOrigen].getLista().insertarFinal(indiceDestino);
        vec[indiceDestino].getLista().insertarFinal(indiceOrigen);
        
        System.out.println("✓ Arista insertada: " + origen + " <-> " + destino);
    }
    
    public void leerArchivo(String ruta) {
        File archivo = new File(ruta);
        
        if (!archivo.exists()) {
            System.out.println("✗ Archivo no encontrado.");
            return;
        }
        
        try (Scanner sc = new Scanner(archivo)) {
            int aristasLeidas = 0;
            
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }
                
                String[] partes = linea.split("[,\\s]+");
                
                if (partes.length >= 2) {
                    try {
                        Object nodo1 = partes[0].trim();
                        Object nodo2 = partes[1].trim();
                        
                        insertarArista(nodo1, nodo2);
                        aristasLeidas++;
                    } catch (Exception e) {
                        System.out.println("✗ Error al procesar línea: " + linea);
                    }
                }
            }
            
            System.out.println("\nArchivo leído correctamente.");
            System.out.println("  Total de aristas: " + aristasLeidas);
            System.out.println("  Total de nodos: " + cantidad);
            
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
    }
    
    // Mostrar el grafo (lista de adyacencia)
    public void mostrarGrafo() {
        if (cantidad == 0) {
            System.out.println("✗ Grafo vacío.");
            return;
        }
        
        System.out.println("\n=== GRAFO (Lista de Adyacencia) ===");
        for (int i = 0; i < cantidad; i++) {
            System.out.print(vec[i].getEl() + " -> ");
            mostrarVecinos(i);
        }
    }
    
    // Método auxiliar para mostrar vecinos
    private void mostrarVecinos(int indice) {
        Lista8<Integer> vecinos = vec[indice].getLista();
        
        if (vecinos.estaVacia()) {
            System.out.println("(sin conexiones)");
            return;
        }
        
        // Recorrer manualmente la lista
        System.out.print("[");
        Caja<Integer> actual = obtenerPrimero(vecinos);
        boolean primero = true;
        
        while (actual != null) {
            if (!primero) {
                System.out.print(", ");
            }
            System.out.print(vec[actual.getElemento()].getEl());
            primero = false;
            actual = actual.getSiguiente();
        }
        System.out.println("]");
    }
    
    // Método auxiliar para obtener el primer nodo de Lista8
    private Caja<Integer> obtenerPrimero(Lista8<Integer> lista) {
        try {
            java.lang.reflect.Field field = lista.getClass().getDeclaredField("PRI");
            field.setAccessible(true);
            return (Caja<Integer>) field.get(lista);
        } catch (Exception e) {
            return null;
        }
    }
    
    // Inicializar marca y padre antes de búsquedas
    public void inicializar() {
        for (int i = 0; i < cantidad; i++) {
            vec[i].setMarca(false);
            vec[i].setPadre(-1);
        }
        System.out.println("Grafo inicializado (marca=false, padre=-1)");
    }
    
    public void busquedaAmplitud(Object inicio) {
        int indice = buscarIndice(inicio);
        
        if (indice == -1) {
            System.out.println("Nodo no encontrado.");
            return;
        }
        
        inicializar();
        
        Lista8<Integer> cola = new Lista8<>();
        
        cola.insertarFinal(indice);
        vec[indice].setMarca(true);
        
        System.out.println("\n=== BÚSQUEDA EN AMPLITUD (BFS) ===");
        System.out.print("Orden de visita: ");
        
        while (!cola.estaVacia()) {
            // Extraer el primer elemento de la cola
            Caja<Integer> nodoActual = obtenerPrimero(cola);
            int actual = nodoActual.getElemento();
            cola.eliminarPrincipio();
            
            System.out.print(vec[actual].getEl() + " ");
            
            // Visitar vecinos
            Lista8<Integer> vecinos = vec[actual].getLista();
            Caja<Integer> vecino = obtenerPrimero(vecinos);
            
            while (vecino != null) {
                int indiceVecino = vecino.getElemento();
                
                if (!vec[indiceVecino].getMarca()) {
                    vec[indiceVecino].setMarca(true);
                    vec[indiceVecino].setPadre(actual);
                    cola.insertarFinal(indiceVecino);
                }
                
                vecino = vecino.getSiguiente();
            }
        }
        
        System.out.println();
    }
    
    public void busquedaProfundidad(Object inicio) {
        int indice = buscarIndice(inicio);
        
        if (indice == -1) {
            System.out.println("✗ Nodo no encontrado.");
            return;
        }
        
        inicializar();
        
        System.out.println("\n=== BÚSQUEDA EN PROFUNDIDAD (DFS) ===");
        System.out.print("Orden de visita: ");
        dfsRecursivo(indice);
        System.out.println();
    }
    
    private void dfsRecursivo(int indice) {
        vec[indice].setMarca(true);
        System.out.print(vec[indice].getEl() + " ");
        
        Lista8<Integer> vecinos = vec[indice].getLista();
        Caja<Integer> vecino = obtenerPrimero(vecinos);
        
        while (vecino != null) {
            int indiceVecino = vecino.getElemento();
            
            if (!vec[indiceVecino].getMarca()) {
                vec[indiceVecino].setPadre(indice);
                dfsRecursivo(indiceVecino);
            }
            
            vecino = vecino.getSiguiente();
        }
    }
    
    // Mostrar camino desde un nodo hasta la raíz (usando padre)
    public void mostrarCamino(Object destino) {
        int indice = buscarIndice(destino);
        
        if (indice == -1) {
            System.out.println("✗ Nodo no encontrado.");
            return;
        }
        
        if (vec[indice].getPadre() == -1 && !vec[indice].getMarca()) {
            System.out.println("✗ El nodo no ha sido visitado en ninguna búsqueda.");
            return;
        }
        
        System.out.print("\n=== CAMINO HASTA " + destino + " ===\n");
        mostrarCaminoRecursivo(indice);
        System.out.println();
    }
    
    private void mostrarCaminoRecursivo(int indice) {
        if (vec[indice].getPadre() != -1) {
            mostrarCaminoRecursivo(vec[indice].getPadre());
            System.out.print(" -> ");
        }
        System.out.print(vec[indice].getEl());
    }
    
    public int getCantidad() {
        return cantidad;
    }
}
