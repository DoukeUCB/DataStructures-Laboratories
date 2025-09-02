import java.util.Scanner;

public class SistemaColegio {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TablaHash tablaHash = new TablaHash();
    
    public static void main(String[] args) {
        int opcion;
        
        System.out.println("BIENVENIDO AL SISTEMA DE GESTION DE MATERIALES DEL COLEGIO");
        
        do {
            mostrarMenu();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 6);
        
        System.out.println("Gracias por usar el Sistema de Gestion de Materiales!");
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n+================================================+");
        System.out.println("|         SISTEMA DE GESTION DE MATERIALES      |");
        System.out.println("+================================================+");
        System.out.println("| 1. Insertar Material en Asignatura           |");
        System.out.println("| 2. Mostrar toda la estructura                 |");
        System.out.println("| 3. Eliminar Material                          |");
        System.out.println("| 4. Buscar Materiales de Asignatura            |");
        System.out.println("| 5. Mostrar Curso Especifico                   |");
        System.out.println("| 6. Salir                                      |");
        System.out.println("+================================================+");
        System.out.print("Seleccione una opcion: ");
    }
    
    private static int leerOpcion() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Limpiar buffer
            return -1;
        }
    }
    
    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> insertarMaterial();
            case 2 -> mostrarTodo();
            case 3 -> eliminarMaterial();
            case 4 -> buscarMateriales();
            case 5 -> mostrarCursoEspecifico();
            case 6 -> System.out.println("Saliendo del programa...");
            default -> System.out.println("Opcion no valida. Intente nuevamente.");
        }
    }
    
    private static void insertarMaterial() {
        System.out.println("\nINSERTAR MATERIAL:");
        
        System.out.print("Ingrese Nivel (0=Primaria, 1=Secundaria): ");
        try {
            int nivel = scanner.nextInt();
            
            if (nivel != 0 && nivel != 1) {
                System.out.println("Error: El nivel debe ser 0 (Primaria) o 1 (Secundaria)");
                return;
            }
            
            System.out.print("Ingrese Curso (1-6): ");
            int curso = scanner.nextInt();
            scanner.nextLine();
            
            if (curso < 1 || curso > 6) {
                System.out.println("Error: El curso debe estar entre 1 y 6");
                return;
            }
            
            System.out.print("Ingrese Asignatura: ");
            String asignatura = scanner.nextLine().trim();
            
            if (asignatura.isEmpty()) {
                System.out.println("Error: La asignatura no puede estar vacia");
                return;
            }
            
            System.out.print("Ingrese Material: ");
            String material = scanner.nextLine().trim();
            
            if (material.isEmpty()) {
                System.out.println("Error: El material no puede estar vacio");
                return;
            }
            
            tablaHash.insertarHash(curso, nivel, asignatura, material);
            System.out.println("Material insertado exitosamente");
            
        } catch (Exception e) {
            System.out.println("Error: Ingrese un numero valido");
            scanner.nextLine();
        }
    }
    
    private static void mostrarTodo() {
        tablaHash.mostrarTodo();
    }
    
    private static void eliminarMaterial() {
        System.out.println("\nELIMINAR MATERIAL:");
        
        System.out.print("Ingrese Nivel (0=Primaria, 1=Secundaria): ");
        try {
            int nivel = scanner.nextInt();
            
            if (nivel != 0 && nivel != 1) {
                System.out.println("Error: El nivel debe ser 0 (Primaria) o 1 (Secundaria)");
                return;
            }
            
            System.out.print("Ingrese Curso (1-6): ");
            int curso = scanner.nextInt();
            scanner.nextLine();
            
            if (curso < 1 || curso > 6) {
                System.out.println("Error: El curso debe estar entre 1 y 6");
                return;
            }
            
            System.out.print("Ingrese Material a eliminar: ");
            String material = scanner.nextLine().trim();
            
            if (material.isEmpty()) {
                System.out.println("Error: El material no puede estar vacio");
                return;
            }
            
            if (tablaHash.eliminar(curso, nivel, material)) {
                System.out.println("Material eliminado exitosamente");
            } else {
                System.out.println("Material no encontrado");
            }
            
        } catch (Exception e) {
            System.out.println("Error: Ingrese un numero valido");
            scanner.nextLine();
        }
    }
    
    private static void buscarMateriales() {
        System.out.println("\nBUSCAR MATERIALES:");
        
        System.out.print("Ingrese Nivel (0=Primaria, 1=Secundaria): ");
        try {
            int nivel = scanner.nextInt();
            
            if (nivel != 0 && nivel != 1) {
                System.out.println("Error: El nivel debe ser 0 (Primaria) o 1 (Secundaria)");
                return;
            }
            
            System.out.print("Ingrese Curso (1-6): ");
            int curso = scanner.nextInt();
            scanner.nextLine();
            
            if (curso < 1 || curso > 6) {
                System.out.println("Error: El curso debe estar entre 1 y 6");
                return;
            }
            
            System.out.print("Ingrese Asignatura: ");
            String asignatura = scanner.nextLine().trim();
            
            if (asignatura.isEmpty()) {
                System.out.println("Error: La asignatura no puede estar vacia");
                return;
            }
            
            tablaHash.buscarMaterialesDeAsignatura(curso, nivel, asignatura);
            
        } catch (Exception e) {
            System.out.println("Error: Ingrese un numero valido");
            scanner.nextLine();
        }
    }
    
    private static void mostrarCursoEspecifico() {
        System.out.println("\nMOSTRAR CURSO ESPECIFICO:");
        
        System.out.print("Ingrese Nivel (0=Primaria, 1=Secundaria): ");
        try {
            int nivel = scanner.nextInt();
            
            if (nivel != 0 && nivel != 1) {
                System.out.println("Error: El nivel debe ser 0 (Primaria) o 1 (Secundaria)");
                return;
            }
            
            System.out.print("Ingrese Curso (1-6): ");
            int curso = scanner.nextInt();
            
            if (curso < 1 || curso > 6) {
                System.out.println("Error: El curso debe estar entre 1 y 6");
                return;
            }
            
            tablaHash.mostrarCurso(curso, nivel);
            
        } catch (Exception e) {
            System.out.println("Error: Ingrese un numero valido");
            scanner.nextLine();
        }
    }
}
