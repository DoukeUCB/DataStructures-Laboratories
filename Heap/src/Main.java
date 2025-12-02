import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Heap<Integer> heap = new Heap<>();
        int opcion;
        
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Insertar al HEAP");
            System.out.println("2. Eliminar RAÍZ");
            System.out.println("3. Mostrar ARRAY");
            System.out.println("4. Leer archivo (10,000 números)");
            System.out.println("5. Heap Sort");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Número a insertar: ");
                        int num = scanner.nextInt();
                        heap.insertar(num);
                        System.out.println("Insertado: " + num);
                        System.out.println("Raíz actual: " + heap.verRaiz());
                    }
                    
                    case 2 -> {
                        if (heap.estaVacio()) {
                            System.out.println("Heap vacío.");
                        } else {
                            Integer raiz = heap.eliminarRaiz();
                            System.out.println("Raíz eliminada: " + raiz);
                            if (!heap.estaVacio()) {
                                System.out.println("Nueva raíz: " + heap.verRaiz());
                            } else {
                                System.out.println("Heap ahora está vacío.");
                            }
                        }
                    }
                    
                    case 3 -> {
                        heap.mostrarArray();
                    }
                    
                    case 4 -> {
                        System.out.print("Ruta del archivo: ");
                        String rutaArchivo = scanner.nextLine().trim();
                        leerArchivo(heap, rutaArchivo);
                    }
                    
                    case 5 -> {
                        if (heap.estaVacio()) {
                            System.out.println("Heap vacío.");
                        } else {
                            heap.heapSort();
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
    
    private static void leerArchivo(Heap<Integer> heap, String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        
        if (!archivo.exists()) {
            System.out.println("Error: Archivo no encontrado.");
            return;
        }
        
        System.out.println("\nLeyendo archivo: " + archivo.getName());
        
        int numerosInsertados = 0;
        long tiempoInicio = System.nanoTime();
        
        try (Scanner fileScanner = new Scanner(archivo)) {
            while (fileScanner.hasNext()) {
                if (fileScanner.hasNextInt()) {
                    int numero = fileScanner.nextInt();
                    heap.insertar(numero);
                    numerosInsertados++;
                } else {
                    fileScanner.next();
                }
            }
            
            long tiempoFin = System.nanoTime();
            double tiempoMs = (tiempoFin - tiempoInicio) / 1_000_000.0;
            
            System.out.println("Inserción completada!");
            System.out.println("Estadísticas:");
            System.out.println("   - Números insertados: " + numerosInsertados);
            System.out.println("   - Tamaño del heap: " + heap.tamanio());
            System.out.println("   - Raíz (máximo): " + heap.verRaiz());
            System.out.println("   - Tiempo total: " + String.format("%.2f", tiempoMs) + " ms");
            System.out.println("   - Promedio: " + String.format("%.4f", tiempoMs / numerosInsertados) + " ms/número");
            
        } catch (FileNotFoundException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

