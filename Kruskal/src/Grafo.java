import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Grafo {
    private HashMap<String, Vertice> G;
    
    public Grafo() {
        G = new HashMap<>();
    }
    
    public void insertarArista(String origen, String destino, int peso) {
        // Insertar vértice origen si no existe
        if (!G.containsKey(origen)) {
            G.put(origen, new Vertice(origen));
        }
        
        // Insertar vértice destino si no existe
        if (!G.containsKey(destino)) {
            G.put(destino, new Vertice(destino));
        }
        
        // Agregar arista
        G.get(origen).agregarArista(destino, peso);
        G.get(destino).agregarArista(origen, peso);
        
        System.out.println("Arista insertada: " + origen + " <<<" + peso + ">>> " + destino);
    }
    
    public void leerArchivo(String ruta) {
        File archivo = new File(ruta);
        
        if (!archivo.exists()) {
            System.out.println("Archivo no encontrado: " + ruta);
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
                        String origen = partes[0];
                        String destino = partes[1];
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
            System.out.println("  Vértices: " + G.size());
            
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer archivo.");
        }
    }
    
    public void mostrarGrafo() {
        if (G.isEmpty()) {
            System.out.println("Grafo vacío.");
            return;
        }
        
        System.out.println("\n=== GRAFO (Lista de Adyacencia con Pesos) ===");
        for (Map.Entry<String, Vertice> entry : G.entrySet()) {
            String nombre = entry.getKey();
            Vertice v = entry.getValue();
            
            System.out.print(nombre + " -> ");
            
            Caja actual = v.getLista().getPri();
            if (actual == null) {
                System.out.println("(sin conexiones)");
            } else {
                System.out.print("[");
                boolean primero = true;
                while (actual != null) {
                    if (!primero) {
                        System.out.print(", ");
                    }
                    System.out.print(actual.getDestino() + ":" + actual.getPeso());
                    primero = false;
                    actual = actual.getSig();
                }
                System.out.println("]");
            }
        }
    }
    
    public int llenarColaP(Heap colaP) {
        int totalAristas = 0;
        
        for (Map.Entry<String, Vertice> entry : G.entrySet()) {
            String origen = entry.getKey();
            Vertice v = entry.getValue();
            
            Caja actual = v.getLista().getPri();
            while (actual != null) {
                String destino = actual.getDestino();
                
                if (origen.compareTo(destino) < 0) {
                    Nodo nodo = new Nodo(actual.getPeso(), origen, destino);
                    colaP.insertar(nodo);
                    totalAristas++;
                }
                
                actual = actual.getSig();
            }
        }
        
        return totalAristas;
    }
    
    private String buscarCiclo(String x) {
        Vertice v = G.get(x);
        
        if (v == null) {
            return x;
        }
        
        while (!x.equals(v.getPadre())) {
            x = v.getPadre();
            v = G.get(x);
        }
        
        return x;
    }
    
    public ResultadoKruskal kruskal() {
        if (G.isEmpty()) {
            System.out.println("Grafo vacío.");
            return null;
        }
        
        // Inicializar: cada vértice es su propio padre
        for (Vertice v : G.values()) {
            v.setPadre(v.getNombre());
        }
        
        Grafo AE = new Grafo();
        int total = 0;
        
        Heap colaP = new Heap();
        int A = llenarColaP(colaP);
        
        System.out.println("\nALGORITMO DE KRUSKAL");
        System.out.println("Total de aristas en el grafo: " + A);
        
        int cont = 0;
        int aristasAgregadas = 0;
        
        while (cont < A) {
            Nodo trio = colaP.sacar();
            
            if (trio == null) {
                break;
            }
            
            String vo = buscarCiclo(trio.getVo());
            String vd = buscarCiclo(trio.getVd());
            
            if (!vo.equals(vd)) {
                AE.insertarArista(trio.getVo(), trio.getVd(), trio.getKey());
                total += trio.getKey();
                
                // Unir conjuntos
                G.get(vo).setPadre(vd);
                
                aristasAgregadas++;
                System.out.println("Agregada: " + trio + " | Total acumulado: " + total);
            } else {
                System.out.println("Rechazada: " + trio + " (forma ciclo)");
            }
            
            cont++;
        }
        
        System.out.println("\nRESULTADO");
        System.out.println("Aristas en el Árbol de Expansión Mínima: " + aristasAgregadas);
        System.out.println("Peso total del AEM: " + total);
        
        return new ResultadoKruskal(AE, total);
    }
    
    public boolean estaVacio() {
        return G.isEmpty();
    }
    
    public int getCantidadVertices() {
        return G.size();
    }
    
    public static class ResultadoKruskal {
        private Grafo AE;
        private int total;
        
        public ResultadoKruskal(Grafo AE, int total) {
            this.AE = AE;
            this.total = total;
        }
        
        public Grafo getAE() {
            return AE;
        }
        
        public int getTotal() {
            return total;
        }
    }
}
