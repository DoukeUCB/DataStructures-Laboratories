import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("BIENVENIDO AL SISTEMA DE ESTRUCTURAS DE DATOS");
        System.out.println("Seleccione el programa que desea ejecutar:");
        System.out.println();
        System.out.println("1. Lista8 - Operaciones basicas");
        System.out.println("2. Sistema de Gestion de Materiales del Colegio");
        System.out.println("3. Salir");
        System.out.println();
        System.out.print("Ingrese su opcion: ");
        
        try {
            int opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1 -> {
                    System.out.println("\nIniciando Sistema Lista8...\n");
                    Lista8Main.ejecutar();
                }
                case 2 -> {
                    System.out.println("\nIniciando Sistema de Gestion de Materiales...\n");
                    SistemaColegio.main(new String[0]);
                }
                case 3 -> {
                    System.out.println("Hasta luego!");
                }
                default -> {
                    System.out.println("Opcion no valida.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Ingrese un numero valido.");
        }
        
        scanner.close();
    }
}