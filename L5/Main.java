import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Hash tablaHash = new Hash();
    
    public static void main(String[] args) {
        System.out.println("SISTEMA DE GESTION DE TRABAJADORES");
        System.out.println("Tabla Hash con tamaño: " + tablaHash.getTamaño());
        System.out.println("Capacidad maxima: 50 trabajadores");
        
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 9);
        
        System.out.println("Hasta luego!");
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("|        MENU TABLA HASH TRABAJADORES     |");
        System.out.println("");
        System.out.println("| 1. Insertar nombre");
        System.out.println("| 2. Eliminar nombre");
        System.out.println("| 3. Buscar nombre");
        System.out.println("| 4. Mostrar tabla hash completa");
        System.out.println("| 5. Mostrar solo posiciones ocupadas");
        System.out.println("| 6. Leer nombres desde archivo");
        System.out.println("| 7. Comparar tiempos de hash");
        System.out.println("| 8. Procesar archivo con contadores");
        System.out.println("| 9. Salir");
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
        scanner.nextLine();
        
        switch (opcion) {
            case 1 -> insertarNombre();
            case 2 -> eliminarNombre();
            case 3 -> buscarNombre();
            case 4 -> mostrarTablaCompleta();
            case 5 -> mostrarSoloOcupadas();
            case 6 -> leerArchivo();
            case 7 -> compararTiempos();
            case 8 -> procesarArchivoConContadores();
            case 9 -> System.out.println("Saliendo del sistema...");
            default -> System.out.println("Opcion no valida. Intente nuevamente.");
        }
    }
    
    private static void insertarNombre() {
        System.out.println("\n=== INSERTAR NOMBRE ===");
        System.out.print("Ingrese el nombre completo del trabajador: ");
        String nombre = scanner.nextLine().trim();
        
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacio.");
            return;
        }
        
        int posicionHash = tablaHash.funcionHash(nombre);
        System.out.println("Posicion calculada (hash): " + posicionHash);
        
        if (tablaHash.insertarNombre(nombre)) {
            System.out.println("Nombre insertado exitosamente en la posicion " + posicionHash);
            System.out.println("Total de trabajadores: " + tablaHash.contarElementos());
        } else {
            System.out.println("Error: El nombre ya existe en la tabla hash.");
        }
    }
    
    private static void eliminarNombre() {
        System.out.println("\n=== ELIMINAR NOMBRE ===");
        System.out.print("Ingrese el nombre a eliminar: ");
        String nombre = scanner.nextLine().trim();
        
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacio.");
            return;
        }
        
        int posicionHash = tablaHash.funcionHash(nombre);
        System.out.println("Buscando en posicion: " + posicionHash);
        
        if (tablaHash.eliminarNombre(nombre)) {
            System.out.println("Nombre eliminado exitosamente.");
            System.out.println("Total de trabajadores: " + tablaHash.contarElementos());
        } else {
            System.out.println("Error: El nombre no fue encontrado.");
        }
    }
    
    private static void buscarNombre() {
        System.out.println("\n=== BUSCAR NOMBRE ===");
        System.out.print("Ingrese el nombre a buscar: ");
        String nombre = scanner.nextLine().trim();
        
        if (nombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacio.");
            return;
        }
        
        int posicionHash = tablaHash.funcionHash(nombre);
        System.out.println("Buscando en posicion: " + posicionHash);
        
        if (tablaHash.buscarNombre(nombre)) {
            System.out.println("ENCONTRADO: El trabajador '" + nombre + "' esta registrado.");
            tablaHash.mostrarPosicion(posicionHash);
        } else {
            System.out.println("NO ENCONTRADO: El trabajador '" + nombre + "' no esta registrado.");
        }
    }
    
    private static void mostrarTablaCompleta() {
        tablaHash.mostrarTablaHash();
    }
    
    private static void mostrarSoloOcupadas() {
        tablaHash.mostrarSoloOcupadas();
    }
    
    private static void leerArchivo() {
        System.out.println("\n=== LEER NOMBRES DESDE ARCHIVO ===");
        System.out.print("Ingrese el nombre del archivo: ");
        String nombreArchivo = scanner.nextLine().trim();
        
        try {
            File archivo = new File(nombreArchivo);
            
            try (Scanner lectorArchivo = new Scanner(archivo)) {
                int lineasProcesadas = 0;
                int insertadosExitosos = 0;
                int duplicados = 0;
                
                System.out.println("Cargando nombres del archivo...\n");
                
                while (lectorArchivo.hasNextLine()) {
                    String nombre = lectorArchivo.nextLine().trim();
                    lineasProcesadas++;
                    
                    if (nombre.isEmpty()) {
                        continue;
                    }
                    
                    int posicionHash = tablaHash.funcionHash(nombre);
                    
                    if (tablaHash.insertarNombre(nombre)) {
                        System.out.println("Insertado: " + nombre + " -> Posicion " + posicionHash);
                        insertadosExitosos++;
                    } else {
                        System.out.println("Duplicado: " + nombre + " (ya existe)");
                        duplicados++;
                    }
                }
                
                System.out.println("\n=== RESUMEN DE CARGA ===");
                System.out.println("Lineas procesadas: " + lineasProcesadas);
                System.out.println("Nombres insertados: " + insertadosExitosos);
                System.out.println("Duplicados omitidos: " + duplicados);
                System.out.println("Total en tabla: " + tablaHash.contarElementos());
                
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo '" + nombreArchivo + "' no encontrado.");
            System.out.println("Verifique que el archivo existe en el directorio actual.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
    
    private static void compararTiempos() {
        System.out.println("\n=== COMPARACION DE TIEMPOS DE HASH ===");
        System.out.print("Ingrese el nombre del archivo (ejemplo: cienaniosdesoledad.txt): ");
        String nombreArchivo = scanner.nextLine().trim();
        
        if (nombreArchivo.isEmpty()) {
            nombreArchivo = "cienaniosdesoledad.txt";
            System.out.println("Usando archivo por defecto: " + nombreArchivo);
        }
        
        Hash hashResiduo = new Hash();
        Hash hashMultiplicacion = new Hash();
        
        try {
            File archivo = new File(nombreArchivo);
            
            Scanner scannerFile = new Scanner(archivo);
            long tiempoInicio1 = System.nanoTime();
            
            while (scannerFile.hasNext()) {
                String palabra = scannerFile.next().replaceAll("[^a-zA-Z]", "");
                if (!palabra.isEmpty()) {
                    hashResiduo.insertarPalabraConContador(palabra);
                }
            }
            
            long tiempoFin1 = System.nanoTime();
            long tiempoResiduo = tiempoFin1 - tiempoInicio1;
            scannerFile.close();
            
            Scanner scanner2 = new Scanner(archivo);
            long tiempoInicio2 = System.nanoTime();
            
            while (scanner2.hasNext()) {
                String palabra = scanner2.next().replaceAll("[^a-zA-Z]", "");
                if (!palabra.isEmpty()) {
                    hashMultiplicacion.insertarPalabraConContadorMultiplicacion(palabra);
                }
            }
            
            long tiempoFin2 = System.nanoTime();
            long tiempoMultiplicacion = tiempoFin2 - tiempoInicio2;
            scanner2.close();
            
            System.out.println("\n=== RESULTADOS DE TIEMPOS ===");
            System.out.printf("Tiempo Hash por Residuo: %.6f ms%n", tiempoResiduo / 1_000_000.0);
            System.out.printf("Tiempo Hash por Multiplicacion: %.6f ms%n", tiempoMultiplicacion / 1_000_000.0);
            
            if (tiempoResiduo < tiempoMultiplicacion) {
                System.out.printf("Hash por Residuo es %.2fx mas rapido%n", 
                    (double) tiempoMultiplicacion / tiempoResiduo);
            } else {
                System.out.printf("Hash por Multiplicacion es %.2fx mas rapido%n", 
                    (double) tiempoResiduo / tiempoMultiplicacion);
            }
            
            System.out.println("\n=== HASH POR RESIDUO ===");
            hashResiduo.mostrarTablaConContadores();
            
            System.out.println("\n=== HASH POR MULTIPLICACION ===");
            hashMultiplicacion.mostrarTablaConContadores();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado - " + nombreArchivo);
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
    
    private static void procesarArchivoConContadores() {
        System.out.println("\n=== PROCESAR ARCHIVO CON CONTADORES ===");
        System.out.print("Ingrese el nombre del archivo (ejemplo: cienaniosdesoledad.txt): ");
        String nombreArchivo = scanner.nextLine().trim();
    
        System.out.println("\n=== PROCESANDO ARCHIVO: " + nombreArchivo + " ===");
        
        Hash hash = new Hash();
        
        try {
            File archivo = new File(nombreArchivo);
            Scanner scanner = new Scanner(archivo);
            
            int totalPalabras = 0;
            
            while (scanner.hasNext()) {
                String palabra = scanner.next().replaceAll("[^a-zA-Z]", "");
                if (!palabra.isEmpty()) {
                    hash.insertarPalabraConContador(palabra);
                    totalPalabras++;
                }
            }
            
            scanner.close();
            
            System.out.println("Total de palabras procesadas: " + totalPalabras);
            hash.mostrarTablaConContadores();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado - " + nombreArchivo);
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    public static Hash getTablaHash() {
        return tablaHash;
    }

    public static void setTablaHash(Hash tablaHash) {
        Main.tablaHash = tablaHash;
    }
}