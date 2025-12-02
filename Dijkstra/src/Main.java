import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Grafo grafo = new Grafo();
        int opcion;
        
        do {
            System.out.println("\n=== DIJKSTRA ===");
            System.out.println("1. Leer Archivo");
            System.out.println("2. Insertar Arista");
            System.out.println("3. Mostrar Grafo");
            System.out.println("4. Inicializar Grafo");
            System.out.println("5. Ejecutar Dijkstra");
            System.out.println("6. Mostrar Todos los Caminos desde Origen");
            System.out.println("7. Salir");
            System.out.print("Opcion: ");
            
            opcion = sc.nextInt();
            sc.nextLine();
            
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del archivo: ");
                    String nombreArchivo = sc.nextLine();
                    grafo.leerArchivo(nombreArchivo);
                    break;
                    
                case 2:
                    System.out.print("Ingrese el vértice origen: ");
                    char ori = sc.next().charAt(0);
                    System.out.print("Ingrese el vértice destino: ");
                    char dest = sc.next().charAt(0);
                    System.out.print("Ingrese el peso: ");
                    int peso = sc.nextInt();
                    grafo.insertarArista(ori, dest, peso);
                    System.out.println("Arista insertada correctamente.");
                    break;
                    
                case 3:
                    System.out.println("\nGrafo completo:");
                    grafo.mostrarGrafo();
                    break;
                    
                case 4:
                    grafo.inicializar();
                    System.out.println("Padre, Marca y Distancia inicializados.");
                    break;
                    
                case 5:
                    System.out.print("Ingrese nodo origen: ");
                    char origen = sc.nextLine().toUpperCase().charAt(0);
                    grafo.dijkstra(origen);
                    break;
                    
                case 6:
                    System.out.print("Ingrese nodo origen: ");
                    char origenCaminos = sc.nextLine().toUpperCase().charAt(0);
                    grafo.mostrarTodosLosCaminos(origenCaminos);
                    break;
                    
                case 7:
                    System.out.println("Saliendo...");
                    break;
                    
                default:
                    System.out.println("Opción inválida.");
            }
            
        } while (opcion != 7);
        
        sc.close();
    }
}
