import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Grafo grafo = new Grafo();
        Grafo.ResultadoKruskal resultado = null;
        int opcion;
        
        System.out.println("ALGORITMO DE KRUSKAL\n");
        
        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Insertar Arista con peso");
            System.out.println("2. Leer de archivo");
            System.out.println("3. Mostrar Grafo");
            System.out.println("4. Ejecutar Kruskal");
            System.out.println("5. Mostrar Árbol de Expansión Mínima (AEM)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            
            try {
                opcion = sc.nextInt();
                sc.nextLine();
                
                switch (opcion) {
                    case 1:
                        System.out.print("Vértice origen: ");
                        String origen = sc.nextLine().trim().toUpperCase();
                        
                        System.out.print("Vértice destino: ");
                        String destino = sc.nextLine().trim().toUpperCase();
                        
                        System.out.print("Peso: ");
                        int peso = sc.nextInt();
                        sc.nextLine();
                        
                        grafo.insertarArista(origen, destino, peso);
                        break;
                        
                    case 2:
                        System.out.print("Nombre del archivo: ");
                        String archivo = sc.nextLine().trim();
                        grafo.leerArchivo(archivo);
                        break;
                        
                    case 3:
                        grafo.mostrarGrafo();
                        break;
                        
                    case 4:
                        if (grafo.estaVacio()) {
                            System.out.println("El grafo está vacío. Agregue aristas primero.");
                        } else {
                            resultado = grafo.kruskal();
                        }
                        break;
                        
                    case 5:
                        if (resultado == null) {
                            System.out.println("Debe ejecutar Kruskal primero (opción 4).");
                        } else {
                            System.out.println("\nÁRBOL DE EXPANSIÓN MÍNIMA");
                            System.out.println("Peso total: " + resultado.getTotal());
                            resultado.getAE().mostrarGrafo();
                        }
                        break;
                        
                    case 0:
                        System.out.println("¡Hasta luego!");
                        break;
                        
                    default:
                        System.out.println("Opción inválida.");
                }
                
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida.");
                sc.nextLine();
                opcion = -1;
            }
            
        } while (opcion != 0);
        
        sc.close();
    }
}
