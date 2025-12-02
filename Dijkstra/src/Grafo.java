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
    
    // Buscar índice de un vértice por su carácter
    private int buscarIndice(char vertice) {
        for (int i = 0; i < cantidad; i++) {
            if (vec[i].getVertice() == vertice) {
                return i;
            }
        }
        return -1;
    }
    
    private int insertarNodo(char vertice) {
        int indice = buscarIndice(vertice);
        
        if (indice != -1) {
            return indice;
        }
        
        if (cantidad >= MAX) {
            System.out.println("Grafo lleno.");
            return -1;
        }
        
        vec[cantidad] = new Nodo(vertice);
        cantidad++;
        return cantidad - 1;
    }
    
    public void insertarArista(char origen, char destino, int peso) {
        int indOrigen = insertarNodo(origen);
        int indDestino = insertarNodo(destino);
        
        if (indOrigen == -1 || indDestino == -1) {
            return;
        }
        
        vec[indOrigen].agregarArista(indDestino, peso);
        vec[indDestino].agregarArista(indOrigen, peso);
        
        System.out.println("Arista insertada: " + origen + " <-" + peso + "-> " + destino);
    }
    
    public void leerArchivo(String ruta) {
        File archivo = new File(ruta);
        
        if (!archivo.exists()) {
            System.out.println("Archivo no encontrado.");
            return;
        }
        
        try (Scanner sc = new Scanner(archivo)) {
            int aristasLeidas = 0;
            
            while (sc.hasNextLine()) {
                String linea = sc.nextLine().trim();
                
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }
                
                String[] partes = linea.split("\\s+");
                
                if (partes.length >= 3) {
                    try {
                        char origen = partes[0].charAt(0);
                        char destino = partes[1].charAt(0);
                        int peso = Integer.parseInt(partes[2]);
                        
                        insertarArista(origen, destino, peso);
                        aristasLeidas++;
                    } catch (Exception e) {
                        System.out.println("Error en línea: " + linea);
                    }
                }
            }
            
            System.out.println("\nArchivo leído correctamente.");
            System.out.println("  Aristas: " + aristasLeidas);
            System.out.println("  Nodos: " + cantidad);
            
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer archivo.");
        }
    }
    
    public void mostrarGrafo() {
        if (cantidad == 0) {
            System.out.println("Grafo vacío.");
            return;
        }
        
        System.out.println("\nGRAFO (Lista de Adyacencia con Pesos)");
        for (int i = 0; i < cantidad; i++) {
            System.out.print(vec[i].getVertice() + " -> ");
            
            if (vec[i].getLista().isEmpty()) {
                System.out.println("(sin conexiones)");
            } else {
                System.out.print("[");
                for (int j = 0; j < vec[i].getLista().size(); j++) {
                    Arista arista = vec[i].getLista().get(j);
                    char destino = vec[arista.getDestino()].getVertice();
                    System.out.print(destino + ":" + arista.getPeso());
                    
                    if (j < vec[i].getLista().size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println("]");
            }
        }
    }
    
    public void inicializar() {
        for (int i = 0; i < cantidad; i++) {
            vec[i].setDistancia(Integer.MAX_VALUE);
            vec[i].setPadre(-1);
            vec[i].setMarca(false);
        }
        System.out.println("Grafo inicializado (distancia=INF, padre=-1, marca=false)");
    }
    
    public void dijkstra(char origen) {
        int indOrigen = buscarIndice(origen);
        
        if (indOrigen == -1) {
            System.out.println("Nodo origen no encontrado.");
            return;
        }
        
        inicializar();
        
        // Cola de prioridad (Heap MIN)
        HeapMin<Duo> colaP = new HeapMin<>();
        
        // Distancia del origen a sí mismo es 0
        vec[indOrigen].setDistancia(0);
        colaP.insertar(new Duo(0, indOrigen));
        
        System.out.println("\nDIJKSTRA desde '" + origen + "'");
        
        while (!colaP.estaVacio()) {
            Duo duo = colaP.extraerMin();
            int vi = duo.getVertice();
            
            // Si ya fue marcado, continuar
            if (vec[vi].getMarca()) {
                continue;
            }
            
            // Marcar como visitado
            vec[vi].setMarca(true);
            
            System.out.println("Visitando: " + vec[vi].getVertice() + 
                             " (distancia=" + vec[vi].getDistancia() + ")");
            
            // Recorrer vecinos
            for (Arista arista : vec[vi].getLista()) {
                int ady = arista.getDestino();
                int peso = arista.getPeso();
                
                // Si no ha sido marcado y peso > 0
                if (!vec[ady].getMarca() && peso > 0) {
                    int nuevaDistancia = vec[vi].getDistancia() + peso;
                    
                    // Relajación: si encontramos un camino más corto
                    if (nuevaDistancia < vec[ady].getDistancia()) {
                        vec[ady].setDistancia(nuevaDistancia);
                        vec[ady].setPadre(vi);
                        colaP.insertar(new Duo(nuevaDistancia, ady));
                    }
                }
            }
        }
        
        System.out.println("\nDISTANCIAS MÍNIMAS desde '" + origen + "'");
        for (int i = 0; i < cantidad; i++) {
            char vert = vec[i].getVertice();
            int dist = vec[i].getDistancia();
            
            if (dist == Integer.MAX_VALUE) {
                System.out.println(vert + ": INF (inalcanzable)");
            } else {
                System.out.println(vert + ": " + dist);
            }
        }
    }
    
    // Mostrar camino desde origen hasta un destino
    public void mostrarCamino(char origen, char destino) {
        int indOrigen = buscarIndice(origen);
        int indDestino = buscarIndice(destino);
        
        if (indOrigen == -1 || indDestino == -1) {
            System.out.println("Nodo no encontrado.");
            return;
        }
        
        if (vec[indDestino].getDistancia() == Integer.MAX_VALUE) {
            System.out.println("No existe camino de " + origen + " a " + destino);
            return;
        }
        
        System.out.print("\nCAMINO de '" + origen + "' a '" + destino + "'\n");
        System.out.print("Camino: ");
        mostrarCaminoRecursivo(indDestino);
        System.out.println("\nDistancia total: " + vec[indDestino].getDistancia());
    }
    
    private void mostrarCaminoRecursivo(int indice) {
        if (vec[indice].getPadre() != -1) {
            mostrarCaminoRecursivo(vec[indice].getPadre());
            System.out.print(" -> ");
        }
        System.out.print(vec[indice].getVertice());
    }
    
    public void mostrarTodosLosCaminos(char origen) {
        int indOrigen = buscarIndice(origen);
        
        if (indOrigen == -1) {
            System.out.println("Nodo origen no encontrado.");
            return;
        }
        
        dijkstra(origen);
        
        System.out.println("\nTODOS LOS CAMINOS desde '" + origen + "'");
        
        for (int i = 0; i < cantidad; i++) {
            char destino = vec[i].getVertice();
            
            if (destino == origen) {
                continue;
            }
            
            if (vec[i].getDistancia() == Integer.MAX_VALUE) {
                System.out.println(origen + " -> " + destino + " = INF (inalcanzable)");
            } else {
                System.out.print(origen + " -> " + destino + " = " + vec[i].getDistancia());
                System.out.print(" | Camino: ");
                mostrarCaminoRecursivo(i);
                System.out.println();
            }
        }
    }
    
    public int getCantidad() {
        return cantidad;
    }
}
