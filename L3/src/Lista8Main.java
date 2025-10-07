import java.util.Scanner;

import L6.Lista8;

public class Lista8Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Lista8<Integer> lista = new Lista8<>();
    
    public static void ejecutar() {
        int opcion;
        
        do {
            mostrarMenu();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 9);
        
        System.out.println("¡Gracias por usar Lista8!");
    }
    
    private static void mostrarMenu() {
        System.out.println("MENU LISTA 8");
        System.out.println("");
        System.out.println("| 1. Insertar al Principio");
        System.out.println("| 2. Insertar al Final");
        System.out.println("| 3. Eliminar del Principio");
        System.out.println("| 4. Eliminar del Final");
        System.out.println("| 5. Mostrar Lista");
        System.out.println("| 6. Buscar Secuencialmente");
        System.out.println("| 7. Buscar Recursivamente");
        System.out.println("| 8. Buscar y Eliminar");
        System.out.println("| 9. Volver al Menu Principal");
        System.out.println("");
        System.out.print("Seleccione una opcion: ");
    }
    
    private static int leerOpcion() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
    
    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> insertarPrincipio();
            case 2 -> insertarFinal();
            case 3 -> eliminarPrincipio();
            case 4 -> eliminarFinal();
            case 5 -> mostrarLista();
            case 6 -> buscarSecuencialmente();
            case 7 -> buscarRecursivamente();
            case 8 -> buscarYEliminar();
            case 9 -> System.out.println("Volviendo al menu principal...");
            default -> System.out.println("Opcion no valida. Intente nuevamente.");
        }
    }
    
    private static void insertarPrincipio() {
        System.out.print("Ingrese el elemento a insertar al principio: ");
        try {
            int elemento = scanner.nextInt();
            boolean resultado = lista.insertarPrincipio(elemento);
            
            if (resultado) {
                System.out.println("Elemento " + elemento + " insertado al principio correctamente.");
            } else {
                System.out.println("No se pudo insertar el elemento.");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }
    
    private static void insertarFinal() {
        System.out.print("Ingrese el elemento a insertar al final: ");
        try {
            int elemento = scanner.nextInt();
            boolean resultado = lista.insertarFinal(elemento);
            
            if (resultado) {
                System.out.println("Elemento " + elemento + " insertado al final correctamente.");
            } else {
                System.out.println("No se pudo insertar el elemento.");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }
    
    private static void eliminarPrincipio() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía. No hay elementos para eliminar.");
            return;
        }
        
        boolean resultado = lista.eliminarPrincipio();
        
        if (resultado) {
            System.out.println("Elemento del principio eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el elemento del principio.");
        }
    }
    
    private static void eliminarFinal() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía. No hay elementos para eliminar.");
            return;
        }
        
        boolean resultado = lista.eliminarFinal();
        
        if (resultado) {
            System.out.println("Elemento del final eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el elemento del final.");
        }
    }
    
    private static void mostrarLista() {
        System.out.println("\n Estado actual de la lista:");
        lista.mostrarLista();
        System.out.println("Tamaño actual: " + lista.obtenerTamaño() + " elementos");
    }
    
    private static void buscarSecuencialmente() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía. No hay elementos para buscar.");
            return;
        }
        
        System.out.print("Ingrese el elemento a buscar (búsqueda secuencial): ");
        try {
            int elemento = scanner.nextInt();
            boolean encontrado = lista.buscarSecuencialmente(elemento);
            
            if (encontrado) {
                System.out.println("Elemento " + elemento + " ENCONTRADO mediante búsqueda secuencial.");
            } else {
                System.out.println("Elemento " + elemento + " NO ENCONTRADO mediante búsqueda secuencial.");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }
    
    private static void buscarRecursivamente() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía. No hay elementos para buscar.");
            return;
        }
        
        System.out.print("Ingrese el elemento a buscar (búsqueda recursiva): ");
        try {
            int elemento = scanner.nextInt();
            boolean encontrado = lista.buscarRecursivo(elemento);
            
            if (encontrado) {
                System.out.println("Elemento " + elemento + " ENCONTRADO mediante búsqueda recursiva.");
            } else {
                System.out.println("Elemento " + elemento + " NO ENCONTRADO mediante búsqueda recursiva.");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }
    
    private static void buscarYEliminar() {
        if (lista.estaVacia()) {
            System.out.println("La lista está vacía. No hay elementos para buscar y eliminar.");
            return;
        }
        
        System.out.print("Ingrese el elemento a buscar y eliminar: ");
        try {
            int elemento = scanner.nextInt();
            boolean eliminado = lista.buscarYEliminar(elemento);
            
            if (eliminado) {
                System.out.println("Elemento " + elemento + " ENCONTRADO Y ELIMINADO correctamente.");
                System.out.println("Estado actual de la lista:");
                lista.mostrarLista();
            } else {
                System.out.println("Elemento " + elemento + " NO ENCONTRADO. No se eliminó ningún elemento.");
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }
}
