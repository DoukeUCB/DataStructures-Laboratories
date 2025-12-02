import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TRIE trie = new TRIE();
        int opcion;
        
        System.out.println("=== TRIE ===\n");
        
        do {
            System.out.println("1. Insertar nueva palabra");
            System.out.println("2. Buscar una palabra");
            System.out.println("3. Mostrar palabras que empiecen con...");
            System.out.println("4. Mostrar todas las palabras");
            System.out.println("5. Buscar palabras contenidas en...");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1 -> {
                        System.out.print("Ingrese palabra a insertar: ");
                        String palabra = scanner.nextLine().trim();
                        if (!palabra.isEmpty()) {
                            trie.insertar(palabra);
                            System.out.println("Palabra '" + palabra + "' insertada.");
                        } else {
                            System.out.println("Palabra vacía no válida.");
                        }
                    }
                    
                    case 2 -> {
                        System.out.print("Ingrese palabra a buscar: ");
                        String palabra = scanner.nextLine().trim();
                        if (!palabra.isEmpty()) {
                            if (trie.buscar(palabra)) {
                                System.out.println("La palabra '" + palabra + "' SÍ existe en el Trie.");
                            } else {
                                System.out.println("La palabra '" + palabra + "' NO existe en el Trie.");
                            }
                        } else {
                            System.out.println("Palabra vacía no válida.");
                        }
                    }
                    
                    case 3 -> {
                        System.out.print("Ingrese prefijo: ");
                        String prefijo = scanner.nextLine().trim();
                        if (!prefijo.isEmpty()) {
                            trie.mostrarPalabrasCon(prefijo);
                        } else {
                            System.out.println("Prefijo vacío no válido.");
                        }
                    }
                    
                    case 4 -> {
                        trie.mostrarTodas();
                    }
                    
                    case 5 -> {
                        System.out.print("Ingrese palabra para buscar contenidas: ");
                        String palabra = scanner.nextLine().trim();
                        if (!palabra.isEmpty()) {
                            int cantidad = trie.buscarPalabras(palabra);
                            System.out.println("Cantidad de palabras del Trie contenidas en '" + palabra + "': " + cantidad);
                        } else {
                            System.out.println("Palabra vacía no válida.");
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

