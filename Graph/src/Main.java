import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = new Grafo();
        int opcion;
        
        System.out.println("=== GRAFOS ===\n");
        
        do {
            System.out.println("\n=== MENÚ ===");
            System.out.println("1. Leer Archivo (pares de números)");
            System.out.println("2. Insertar Arista");
            System.out.println("3. Mostrar Grafo");
            System.out.println("4. Inicializar (padre=-1, marca=false)");
            System.out.println("5. Búsqueda en AMPLITUD (BFS)");
            System.out.println("6. Búsqueda en PROFUNDIDAD (DFS)");
            System.out.println("7. Mostrar Camino");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ruta del archivo: ");
                        String ruta = scanner.nextLine().trim();
                        grafo.leerArchivo(ruta);
                    }
                    
                    case 2 -> {
                        System.out.print("Nodo origen: ");
                        String origen = scanner.nextLine().trim();
                        System.out.print("Nodo destino: ");
                        String destino = scanner.nextLine().trim();
                        
                        if (!origen.isEmpty() && !destino.isEmpty()) {
                            grafo.insertarArista(origen, destino);
                        } else {
                            System.out.println("Entrada inválida.");
                        }
                    }
                    
                    case 3 -> {
                        grafo.mostrarGrafo();
                    }
                    
                    case 4 -> {
                        grafo.inicializar();
                    }
                    
                    case 5 -> {
                        if (grafo.getCantidad() == 0) {
                            System.out.println("Grafo vacío.");
                            break;
                        }
                        
                        System.out.print("Nodo inicial para BFS: ");
                        String nodo = scanner.nextLine().trim();
                        
                        if (!nodo.isEmpty()) {
                            grafo.busquedaAmplitud(nodo);
                        } else {
                            System.out.println("Entrada inválida.");
                        }
                    }
                    
                    case 6 -> {
                        if (grafo.getCantidad() == 0) {
                            System.out.println("Grafo vacío.");
                            break;
                        }
                        
                        System.out.print("Nodo inicial para DFS: ");
                        String nodo = scanner.nextLine().trim();
                        
                        if (!nodo.isEmpty()) {
                            grafo.busquedaProfundidad(nodo);
                        } else {
                            System.out.println("Entrada inválida.");
                        }
                    }
                    
                    case 7 -> {
                        if (grafo.getCantidad() == 0) {
                            System.out.println("Grafo vacío.");
                            break;
                        }
                        
                        System.out.print("Nodo destino: ");
                        String nodo = scanner.nextLine().trim();
                        
                        if (!nodo.isEmpty()) {
                            grafo.mostrarCamino(nodo);
                        } else {
                            System.out.println("Entrada inválida.");
                        }
                    }
                    
                    case 0 -> {
                        System.out.println("¡Adiós!");
                    }
                    
                    default -> {
                        System.out.println("Opción inválida.");
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida.");
                scanner.nextLine();
                opcion = -1;
            }
            
        } while (opcion != 0);
        
        scanner.close();
    }
}

