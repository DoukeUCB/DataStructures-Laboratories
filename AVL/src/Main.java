import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AVL<String> avl = new AVL<>();
        int opcion;
            
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Insertar");
            System.out.println("2. Eliminar elemento");
            System.out.println("3. Contar nodos");
            System.out.println("4. Calcular altura");
            System.out.println("5. Mostrar InOrder");
            System.out.println("6. Buscar menor");
            System.out.println("7. Buscar mayor");
            System.out.println("8. Eliminar TODO");
            System.out.println("9. Insertar mediante archivo");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Elemento: ");
                        String elem = scanner.nextLine();
                        System.out.println("Insertando: " + elem);
                        avl.insertar(elem);
                        System.out.println("Insertado correctamente.");
                    }
                    
                    case 2 -> {
                        if (avl.estaVacio()) {
                            System.out.println("Árbol vacío.");
                        } else {
                            System.out.print("Elemento a eliminar: ");
                            String eliminar = scanner.nextLine();
                            if (avl.eliminar(eliminar)) {
                                System.out.println("Elemento " + eliminar + " eliminado.");
                            } else {
                                System.out.println("Elemento no encontrado.");
                            }
                        }
                    }
                    
                    case 3 -> {
                        if (avl.estaVacio()) {
                            System.out.println("Árbol vacío.");
                        } else {
                            System.out.println("Total nodos: " + avl.contarNodos(avl.getRaiz()));
                        }
                    }
                    
                    case 4 -> {
                        if (avl.estaVacio()) {
                            System.out.println("Árbol vacío.");
                        } else {
                            System.out.println("Altura: " + avl.calcularAltura(avl.getRaiz()));
                        }
                    }
                    
                    case 5 -> {
                        if (avl.estaVacio()) {
                            System.out.println("Árbol vacío.");
                        } else {
                            System.out.println("InOrder [elemento[factor]]:");
                            avl.mostrarInOrder(avl.getRaiz());
                            System.out.println();
                        }
                    }
                    
                    case 6 -> {
                        if (avl.estaVacio()) {
                            System.out.println("Árbol vacío.");
                        } else {
                            NOD_AVL<String> menor = avl.buscarMenor(avl.getRaiz());
                            if (menor != null) {
                                System.out.println("Menor: " + menor.getEl() + " (Factor: " + avl.obtenerFactorBalance(menor) + ")");
                            }
                        }
                    }
                    
                    case 7 -> {
                        if (avl.estaVacio()) {
                            System.out.println("Árbol vacío.");
                        } else {
                            NOD_AVL<String> mayor = avl.buscarMayor(avl.getRaiz());
                            if (mayor != null) {
                                System.out.println("Mayor: " + mayor.getEl() + " (Factor: " + avl.obtenerFactorBalance(mayor) + ")");
                            }
                        }
                    }
                    
                    case 8 -> {
                        if (avl.estaVacio()) {
                            System.out.println("Ya está vacío.");
                        } else {
                            System.out.print("¿Seguro? (S/N): ");
                            String conf = scanner.nextLine().trim().toUpperCase();
                            if (conf.equals("S")) {
                                avl.eliminarTodo();
                            } else {
                                System.out.println("Cancelado.");
                            }
                        }
                    }
                    
                    case 9 -> {
                        System.out.print("Archivo: ");
                        String archivo = scanner.nextLine().trim();
                        insertarDesdeArchivo(avl, archivo);
                    }
                    
                    case 0 -> System.out.println("Adiós!");
                    
                    default -> System.out.println("Opción inválida.");
                }
                
            } catch (Exception e) {
                System.out.println("Error: Entrada inválida.");
                scanner.nextLine();
                opcion = -1;
            }
            
        } while (opcion != 0);
        
        scanner.close();
    }
    
    private static void insertarDesdeArchivo(AVL<String> avl, String archivo) {
        try {
            Scanner fs = new Scanner(new File(archivo), "ISO-8859-1");
            long inicioLectura = System.nanoTime();
            while (fs.hasNext()) {
                String pal = fs.next().replaceAll("[^a-zA-ZáéíóúñÁÉÍÓÚÑüÜ]", "").toLowerCase().trim();
                if (!pal.isEmpty() && pal.length() > 2) {
                    avl.insertar(pal);
                }
            }
            long finLectura = System.nanoTime();
            fs.close();
            
            double tiempoMs = (finLectura - inicioLectura) / 1_000_000.0;
            System.out.println("Nodos únicos: " + avl.contarNodos(avl.getRaiz()));
            System.out.println("Tiempo de inserción: " + String.format("%.2f", tiempoMs) + " ms");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
