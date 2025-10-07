import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ListaDeHash restaurante = new ListaDeHash();
    
    public static void main(String[] args) {
        System.out.println("SISTEMA DE GESTION DE RESTAURANTE");
        System.out.println("");
        
        while (true) {
            mostrarMenu();
            int opcion = leerOpcion();
            procesarOpcion(opcion);
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("\n|        MENU RESTAURANTE           |");
        System.out.println("");
        System.out.println("| 1. Insertar Platos y sus Ingredientes");
        System.out.println("| 2. Mostrar todos los Platos y sus Ingredientes");
        System.out.println("| 3. Mostrar Platos que NO tienen un Ingrediente");
        System.out.println("| 4. Mostrar estadísticas del restaurante");
        System.out.println("| 5. Cargar datos de prueba");
        System.out.println("| 6. Mostrar distribución de Hash Table");
        System.out.println("| 7. Salir");
        System.out.println("====================================");
        System.out.print("Seleccione una opcion: ");
    }
    
    private static int leerOpcion() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            return opcion;
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un número válido.");
            return -1;
        }
    }
    
    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                insertarPlatoConIngredientes();
                break;
            case 2:
                mostrarTodosLosPlatos();
                break;
            case 3:
                mostrarPlatosSinIngrediente();
                break;
            case 4:
                mostrarEstadisticas();
                break;
            case 5:
                cargarDatosDePrueba();
                break;
            case 6:
                mostrarDistribucionHashTable();
                break;
            case 7:
                salir();
                break;
            default:
                System.out.println("Opción no válida. Por favor, seleccione una opción del 1 al 7.");
        }
    }
    
    private static void insertarPlatoConIngredientes() {
        System.out.println("\n=== INSERTAR PLATO CON INGREDIENTES ===");
        
        System.out.print("Ingrese el nombre del plato: ");
        String nombrePlato = scanner.nextLine().trim();
        
        if (nombrePlato.isEmpty()) {
            System.out.println("El nombre del plato no puede estar vacío.");
            return;
        }
        
        // Verificar si el plato ya existe
        if (restaurante.buscarPlato(nombrePlato) != null) {
            System.out.println("El plato ya existe. Se agregarán ingredientes al plato existente.");
        } else {
            restaurante.insertarPlato(nombrePlato);
            System.out.println("Plato '" + nombrePlato + "' creado exitosamente.");
        }
        
        System.out.println("Ingrese los ingredientes (escriba 'fin' para terminar):");
        
        while (true) {
            System.out.print("Ingrediente: ");
            String ingrediente = scanner.nextLine().trim();
            
            if (ingrediente.equalsIgnoreCase("fin")) {
                break;
            }
            
            if (!ingrediente.isEmpty()) {
                if (restaurante.agregarIngredienteAPlato(nombrePlato, ingrediente)) {
                    System.out.println("Ingrediente '" + ingrediente + "' agregado.");
                } else {
                    System.out.println("Error al agregar el ingrediente.");
                }
            } else {
                System.out.println("El ingrediente no puede estar vacío.");
            }
        }
        
        System.out.println("Plato completado.");
    }
    
    private static void mostrarTodosLosPlatos() {
        restaurante.mostrarTodosLosPlatos();
    }
    
    private static void mostrarPlatosSinIngrediente() {
        System.out.println("\n=== BUSCAR PLATOS SIN INGREDIENTE ===");
        System.out.print("Ingrese el ingrediente a buscar: ");
        String ingrediente = scanner.nextLine().trim();
        
        if (ingrediente.isEmpty()) {
            System.out.println("El ingrediente no puede estar vacío.");
            return;
        }
        
        restaurante.mostrarPlatosSinIngrediente(ingrediente);
    }
    
    private static void mostrarEstadisticas() {
        restaurante.mostrarEstadisticas();
    }
    
    private static void cargarDatosDePrueba() {
        System.out.println("\n=== CARGANDO DATOS DE PRUEBA ===");
        
        String[][] platosDePrueba = {
            {"Pasta Carbonara", "pasta", "huevo", "tocino", "queso", "pimienta", "sal"},
            {"Pizza Margherita", "masa", "tomate", "mozzarella", "albahaca", "aceite", "sal"},
            {"Ensalada César", "lechuga", "pollo", "queso", "pan", "aderezo", "limón"},
            {"Arroz con Pollo", "arroz", "pollo", "cebolla", "ajo", "pimiento", "sal", "aceite"},
            {"Hamburguesa Clásica", "pan", "carne", "lechuga", "tomate", "cebolla", "queso"}
        };
        
        for (String[] plato : platosDePrueba) {
            String nombrePlato = plato[0];
            restaurante.insertarPlato(nombrePlato);
            
            for (int i = 1; i < plato.length; i++) {
                restaurante.agregarIngredienteAPlato(nombrePlato, plato[i]);
            }
        }
        
        System.out.println("Datos de prueba cargados exitosamente.");
        System.out.println("Se han agregado " + platosDePrueba.length + " platos al restaurante.");
    }
    
    private static void salir() {
        System.out.println("Saliendo del sistema...");
        System.out.println("¡Hasta luego!");
        scanner.close();
        System.exit(0);
    }
    
    private static void mostrarDistribucionHashTable() {
        System.out.println("\n=== DISTRIBUCIÓN DE HASH TABLES ===");
        
        if (restaurante.contarPlatos() == 0) {
            System.out.println("No hay platos en el restaurante.");
            return;
        }
        
        restaurante.mostrarDistribucionesTodosLosPlatos();
    }
}
