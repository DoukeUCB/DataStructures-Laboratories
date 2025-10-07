import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static HashPorLongitud hashTexto500 = new HashPorLongitud();
    private static HashPorLongitud hashTexto10000 = new HashPorLongitud();
    
    public static void main(String[] args) {
        System.out.println("=== ANALIZADOR DE PALABRAS POR LONGITUD ===");
        System.out.println("Hash Table optimizada para clasificación por tamaño");
        System.out.println("============================================");
        
        while (true) {
            mostrarMenu();
            int opcion = leerOpcion();
            procesarOpcion(opcion);
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("\n|        MENU ANALIZADOR           |");
        System.out.println("===================================");
        System.out.println("| 1. Cargar texto de 500 palabras");
        System.out.println("| 2. Cargar texto de 10,000 palabras");
        System.out.println("| 3. Mostrar estadísticas texto 500");
        System.out.println("| 4. Mostrar estadísticas texto 10,000");
        System.out.println("| 5. Consultar longitud específica (500)");
        System.out.println("| 6. Consultar longitud específica (10,000)");
        System.out.println("| 7. Comparar distribuciones");
        System.out.println("| 8. Crear textos de prueba");
        System.out.println("| 9. Salir");
        System.out.println("===================================");
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
                cargarTexto(hashTexto500, "texto500.txt", "500 palabras");
                break;
            case 2:
                cargarTexto(hashTexto10000, "texto10000.txt", "10,000 palabras");
                break;
            case 3:
                mostrarEstadisticas(hashTexto500, "Texto 500");
                break;
            case 4:
                mostrarEstadisticas(hashTexto10000, "Texto 10,000");
                break;
            case 5:
                consultarLongitudEspecifica(hashTexto500, "Texto 500");
                break;
            case 6:
                consultarLongitudEspecifica(hashTexto10000, "Texto 10,000");
                break;
            case 7:
                compararDistribuciones();
                break;
            case 8:
                crearTextosDePrueba();
                break;
            case 9:
                salir();
                break;
            default:
                System.out.println("Opción no válida. Por favor, seleccione una opción del 1 al 9.");
        }
    }
    
    private static void cargarTexto(HashPorLongitud hash, String nombreArchivo, String descripcion) {
        System.out.println("\n=== CARGAR " + descripcion.toUpperCase() + " ===");
        System.out.print("Nombre del archivo [" + nombreArchivo + "]: ");
        String archivo = scanner.nextLine().trim();
        
        if (archivo.isEmpty()) {
            archivo = nombreArchivo;
        }
        
        try {
            File file = new File(archivo);
            Scanner fileScanner = new Scanner(file);
            
            hash.limpiar(); // Limpiar datos anteriores
            int contador = 0;
            long tiempoInicio = System.nanoTime();
            
            while (fileScanner.hasNext()) {
                String palabra = fileScanner.next()
                    .replaceAll("[^a-zA-ZáéíóúñÁÉÍÓÚÑüÜ]", "");
                
                if (!palabra.isEmpty() && palabra.length() > 0) {
                    hash.insertarPalabra(palabra);
                    contador++;
                }
            }
            
            long tiempoFin = System.nanoTime();
            double tiempoMs = (tiempoFin - tiempoInicio) / 1_000_000.0;
            
            fileScanner.close();
            
            System.out.println("✓ Archivo cargado exitosamente");
            System.out.println("  Palabras procesadas: " + contador);
            System.out.println("  Palabras únicas: " + hash.contarPalabrasUnicas());
            System.out.println("  Tiempo de procesamiento: " + String.format("%.3f ms", tiempoMs));
            
        } catch (FileNotFoundException e) {
            System.out.println("❌ Error: Archivo '" + archivo + "' no encontrado.");
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }
    
    private static void mostrarEstadisticas(HashPorLongitud hash, String nombre) {
        System.out.println("\n=== ESTADÍSTICAS " + nombre.toUpperCase() + " ===");
        
        if (hash.contarTotalPalabras() == 0) {
            System.out.println("No hay datos cargados para " + nombre);
            return;
        }
        
        hash.mostrarEstadisticasPorLongitud();
        hash.mostrarDistribucionOrdenada();
    }
    
    private static void consultarLongitudEspecifica(HashPorLongitud hash, String nombre) {
        System.out.println("\n=== CONSULTA LONGITUD - " + nombre.toUpperCase() + " ===");
        
        if (hash.contarTotalPalabras() == 0) {
            System.out.println("No hay datos cargados para " + nombre);
            return;
        }
        
        System.out.print("Ingrese la longitud a consultar: ");
        try {
            int longitud = Integer.parseInt(scanner.nextLine().trim());
            
            long tiempoInicio = System.nanoTime();
            int cantidad = hash.contarPalabrasDeLongitud(longitud);
            long tiempoFin = System.nanoTime();
            
            double tiempoMs = (tiempoFin - tiempoInicio) / 1_000_000.0;
            
            System.out.printf("Resultado: %d palabras de longitud %d\n", cantidad, longitud);
            System.out.printf("Tiempo de consulta: %.6f ms (¡O(1) acceso!)\n", tiempoMs);
            
            if (cantidad > 0) {
                System.out.print("¿Mostrar las palabras? (s/n): ");
                String respuesta = scanner.nextLine().trim();
                if (respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("si")) {
                    hash.mostrarPalabrasDeLongitud(longitud);
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un número válido.");
        }
    }
    
    private static void compararDistribuciones() {
        System.out.println("\n=== COMPARACIÓN DE DISTRIBUCIONES ===");
        
        if (hashTexto500.contarTotalPalabras() == 0 || hashTexto10000.contarTotalPalabras() == 0) {
            System.out.println("Debe cargar ambos textos primero.");
            return;
        }
        
        System.out.println("\n📊 COMPARACIÓN LADO A LADO:");
        System.out.printf("%-10s | %-15s | %-15s\n", "Longitud", "Texto 500", "Texto 10,000");
        System.out.println("-----------|-----------------|------------------");
        
        for (int i = 1; i <= 20; i++) {
            int count500 = hashTexto500.contarPalabrasDeLongitud(i);
            int count10000 = hashTexto10000.contarPalabrasDeLongitud(i);
            
            if (count500 > 0 || count10000 > 0) {
                System.out.printf("%-10d | %-15d | %-15d\n", i, count500, count10000);
            }
        }
        
        System.out.println("\n📈 RESUMEN:");
        System.out.printf("Total 500: %d palabras (%d únicas)\n", 
            hashTexto500.contarTotalPalabras(), hashTexto500.contarPalabrasUnicas());
        System.out.printf("Total 10,000: %d palabras (%d únicas)\n", 
            hashTexto10000.contarTotalPalabras(), hashTexto10000.contarPalabrasUnicas());
    }
    
    private static void crearTextosDePrueba() {
        System.out.println("\n=== CREAR TEXTOS DE PRUEBA ===");
        
        // Crear texto de 500 palabras
        crearTexto500();
        // Crear texto de 10,000 palabras  
        crearTexto10000();
        
        System.out.println("✓ Textos de prueba creados exitosamente");
        System.out.println("  - texto500.txt (aproximadamente 500 palabras)");
        System.out.println("  - texto10000.txt (aproximadamente 10,000 palabras)");
    }
    
    private static void crearTexto500() {
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter("texto500.txt");
            
            String[] palabras = {
                "el", "la", "de", "que", "y", "en", "un", "es", "se", "no", "te", "lo", "le", "da", "su", "por", "son", "con", "para", "al",
                "casa", "agua", "mesa", "libro", "perro", "gato", "mundo", "tiempo", "vida", "amor", "trabajo", "escuela", "ciudad", "campo", "montaña",
                "hermano", "hermana", "familia", "amigos", "persona", "momento", "problema", "solución", "historia", "cultura", "sociedad", "gobierno",
                "universidad", "estudiante", "profesor", "conocimiento", "aprendizaje", "educación", "tecnología", "computadora", "internet", "comunicación",
                "extraordinario", "maravilloso", "espectacular", "increíble", "fantástico", "excepcional", "revolucionario", "transformación", "globalización",
                "responsabilidad", "sostenibilidad", "biodiversidad", "multiculturalismo", "interdisciplinario", "internacionalización", "democratización"
            };
            
            for (int i = 0; i < 500; i++) {
                String palabra = palabras[i % palabras.length];
                if (i % 10 == 9) palabra += String.valueOf(i/10); // Agregar variación
                writer.print(palabra + " ");
                if (i % 20 == 19) writer.println(); // Nueva línea cada 20 palabras
            }
            
            writer.close();
        } catch (Exception e) {
            System.out.println("Error creando texto500.txt: " + e.getMessage());
        }
    }
    
    private static void crearTexto10000() {
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter("texto10000.txt");
            
            String[] bases = {"car", "com", "des", "pre", "sub", "inter", "trans", "multi", "super", "anti"};
            String[] medios = {"put", "form", "struct", "tect", "spect", "duct", "vent", "plic", "tend", "gress"};
            String[] finales = {"ar", "er", "ir", "ado", "ido", "ción", "sión", "mente", "able", "ible", "ante", "ente"};
            
            for (int i = 0; i < 10000; i++) {
                String palabra = bases[i % bases.length] + 
                               medios[(i/10) % medios.length] + 
                               finales[(i/100) % finales.length];
                
                // Agregar palabras cortas ocasionalmente
                if (i % 50 == 0) palabra = "a";
                if (i % 51 == 0) palabra = "el";
                if (i % 52 == 0) palabra = "de";
                
                writer.print(palabra + " ");
                if (i % 15 == 14) writer.println();
            }
            
            writer.close();
        } catch (Exception e) {
            System.out.println("Error creando texto10000.txt: " + e.getMessage());
        }
    }
    
    private static void salir() {
        System.out.println("\n=== ANÁLISIS FINAL ===");
        System.out.println("Estructura utilizada: Hash Table con clave = longitud");
        System.out.println("Ventajas:");
        System.out.println("  ✓ Acceso O(1) para consultas por longitud");
        System.out.println("  ✓ Ordenamiento automático por longitud");
        System.out.println("  ✓ Agrupación eficiente de palabras similares");
        System.out.println("  ✓ Conteo preciso con contadores");
        System.out.println("\n¡Hasta luego!");
        scanner.close();
        System.exit(0);
    }
}
