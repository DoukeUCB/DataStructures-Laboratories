import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RBT<String> arbol = new RBT<>();
        int opcion;
                
        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Insertar");
            System.out.println("2. Insertar por archivo");
            System.out.println("3. Contar nodos");
            System.out.println("4. Calcular altura");
            System.out.println("5. Mostrar InOrder");
            System.out.println("6. Buscar");
            System.out.println("7. Eliminar TODO");
            System.out.println("8. Comparar RBT y TreeMap");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                
                if (opcion == 1) {
                    System.out.print("Palabra: ");
                    String pal = scanner.nextLine().trim().toLowerCase();
                    if (!pal.isEmpty()) {
                        arbol.insertar(pal);
                        System.out.println("Insertado.");
                    }
                } else if (opcion == 2) {
                    System.out.print("Archivo: ");
                    String arch = scanner.nextLine().trim();
                    insertarArchivo(arbol, arch);
                } else if (opcion == 3) {
                    if (!arbol.estaVacio()) {
                        System.out.println("Nodos: " + arbol.contarNodos(arbol.getRaiz()));
                    } else {
                        System.out.println("Vacio");
                    }
                } else if (opcion == 4) {
                    if (!arbol.estaVacio()) {
                        System.out.println("Altura: " + arbol.calcularAltura(arbol.getRaiz()));
                    } else {
                        System.out.println("Vacio");
                    }
                } else if (opcion == 5) {
                    if (!arbol.estaVacio()) {
                        arbol.mostrarInOrder(arbol.getRaiz());
                        System.out.println();
                    } else {
                        System.out.println("Vacio");
                    }
                } else if (opcion == 6) {
                    if (!arbol.estaVacio()) {
                        System.out.print("Buscar: ");
                        String b = scanner.nextLine().trim().toLowerCase();
                        NOD_RBT<String> r = arbol.buscarElemento(b, arbol.getRaiz());
                        if (r != null) {
                            System.out.println("Encontrado: " + r.getElement() + 
                                " (" + r.getColorString() + ":" + r.getContador() + ")");
                        } else {
                            System.out.println("No encontrado");
                        }
                    } else {
                        System.out.println("Vacio");
                    }
                } else if (opcion == 7) {
                    arbol.eliminarTodo();
                } else if (opcion == 8) {
                    System.out.print("Archivo: ");
                    String arch = scanner.nextLine().trim();
                    comparar(arch);
                } else if (opcion == 0) {
                    System.out.println("Adios");
                }
            } catch (Exception e) {
                scanner.nextLine();
                opcion = -1;
            }
        } while (opcion != 0);
        
        scanner.close();
    }
    
    private static void insertarArchivo(RBT<String> arbol, String archivo) {
        try {
            Scanner fs = new Scanner(new File(archivo), "ISO-8859-1");
            long inicioLectura = System.nanoTime();
            while (fs.hasNext()) {
                String pal = fs.next().replaceAll("[^a-zA-ZáéíóúñÁÉÍÓÚÑüÜ]", "").toLowerCase().trim();
                if (!pal.isEmpty() && pal.length() > 2) {
                    arbol.insertar(pal);
                }
            }
            long finLectura = System.nanoTime();
            fs.close();
            
            double tiempoMs = (finLectura - inicioLectura) / 1_000_000.0;
            System.out.println("Nodos únicos: " + arbol.contarNodos(arbol.getRaiz()));
            System.out.println("Tiempo de inserción: " + String.format("%.2f", tiempoMs) + " ms");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void comparar(String archivo) {
        java.util.List<String> palabras = new java.util.ArrayList<>();
        try {
            Scanner fs = new Scanner(new File(archivo), "ISO-8859-1");
            while (fs.hasNext()) {
                String pal = fs.next().replaceAll("[^a-zA-ZáéíóúñÁÉÍÓÚÑüÜ]", "").toLowerCase().trim();
                if (!pal.isEmpty() && pal.length() > 2) {
                    palabras.add(pal);
                }
            }
            fs.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
            return;
        }

        System.out.println("\n--- Mi RBT ---");
        RBT<String> miRBT = new RBT<>();
        long inicioRBT = System.nanoTime();
        for (String palabra : palabras) {
            miRBT.insertar(palabra);
        }
        long finRBT = System.nanoTime();
        double tiempoRBT = (finRBT - inicioRBT) / 1_000_000.0;
        
        System.out.println("Nodos: " + miRBT.contarNodos(miRBT.getRaiz()));
        System.out.println("Altura: " + miRBT.calcularAltura(miRBT.getRaiz()));
        System.out.println("Tiempo: " + String.format("%.2f", tiempoRBT) + " ms");
        


        System.out.println("\n--- TreeMap de Java ---");
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        long inicioTreeMap = System.nanoTime();
        for (String palabra : palabras) {
            treeMap.put(palabra, treeMap.getOrDefault(palabra, 0) + 1);
        }
        long finTreeMap = System.nanoTime();
        double tiempoTreeMap = (finTreeMap - inicioTreeMap) / 1_000_000.0;
        
        System.out.println("Nodos: " + treeMap.size());
        System.out.println("Tiempo: " + String.format("%.2f", tiempoTreeMap) + " ms");
        

        
        System.out.println("\nTiempos:");
        if (tiempoRBT < tiempoTreeMap) {
            System.out.println("Mi RBT es " + String.format("%.2f", tiempoTreeMap / tiempoRBT) + "x más rápido");
        } else {
            System.out.println("TreeMap es " + String.format("%.2f", tiempoRBT / tiempoTreeMap) + "x más rápido");
        }
    }
}

