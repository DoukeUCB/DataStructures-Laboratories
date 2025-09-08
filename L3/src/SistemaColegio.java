import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SistemaColegio {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TablaHash tablaHash = new TablaHash();
    
    public static void main(String[] args) {
        try (scanner) {
            int opcion;
            
            System.out.println("BIENVENIDO AL DEL COLEGIO");
            
            do {
                mostrarMenu();
                opcion = leerOpcion();
                procesarOpcion(opcion);
            } while (opcion != 7);
            
            System.out.println("Gracias!");
        }
    }
    
    private static void mostrarMenu() {
        System.out.println("SISTEMA DE MATERIALES");
        System.out.println("");
        System.out.println("| 1. Insertar material dentro de asignatura");
        System.out.println("| 2. Mostrar lista de listas");
        System.out.println("| 3. Eliminar material de curso");
        System.out.println("| 4. Buscar materiales dentro de asignatura");
        System.out.println("| 5. Mostrar un curso");
        System.out.println("| 6. Cargar datos desde archivo");
        System.out.println("| 7. Salir");
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
            case 1 -> insertarMaterial();
            case 2 -> mostrarTodo();
            case 3 -> eliminarMaterial();
            case 4 -> buscarMateriales();
            case 5 -> mostrarCursoEspecifico();
            case 6 -> cargarDesdeArchivo();
            case 7 -> System.out.println("Saliendo del programa...");
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
        
        System.out.print("Ingrese Nivel (0 -> Primaria, 1 -> Secundaria): ");
        try {
            int nivel = scanner.nextInt();
            
            if (nivel != 0 && nivel != 1) {
                System.out.println("Error: El nivel debe ser 0 (Primaria) o 1 (Secundaria)");
                return;
            }
            
            System.out.print("Ingrese Curso (Del 1 al 6): ");
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
        
        System.out.print("Ingrese Nivel (0 es Primaria, 1 es Secundaria): ");
        try {
            int nivel = scanner.nextInt();
            
            if (nivel != 0 && nivel != 1) {
                System.out.println("Error: El nivel debe ser 0 (Primaria) o 1 (Secundaria)");
                return;
            }
            
            System.out.print("Ingrese Curso (Del 1 al 6): ");
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
    
    private static void cargarDesdeArchivo() {
        System.out.println("\nCARGAR DATOS DESDE ARCHIVO:");
        System.out.print("Ingrese el nombre del archivo (ejemplo: materiales.txt): ");
        
        try {
            String nombreArchivo = scanner.nextLine().trim();
            if (nombreArchivo.isEmpty()) {
                nombreArchivo = "L3/src/materiales.txt";
                System.out.println("Usando archivo por defecto: materiales.txt");
            }
            
            File archivo = new File(nombreArchivo);
            
            try (Scanner lectorArchivo = new Scanner(archivo)) {
                int contadorLineas = 0;
                int contadorExitosos = 0;
                int contadorErrores = 0;
                
                System.out.println("Cargando datos del archivo...\n");
                
                while (lectorArchivo.hasNextLine()) {
                    String linea = lectorArchivo.nextLine().trim();
                    contadorLineas++;
                    
                    if (linea.isEmpty()) {
                        continue;
                    }
                    
                    try {
                        String[] partes = linea.split(",");
                        
                        if (partes.length != 4) {
                            System.out.println("Error en linea " + contadorLineas + ": Formato incorrecto - " + linea);
                            contadorErrores++;
                            continue;
                        }
                        
                        int curso = Integer.parseInt(partes[0].trim());
                        int nivel = Integer.parseInt(partes[1].trim());
                        String asignatura = partes[2].trim();
                        String material = partes[3].trim();
                        
                        if (curso < 1 || curso > 6 || (nivel != 0 && nivel != 1)) {
                            System.out.println("Error en linea " + contadorLineas + ": Valores fuera de rango - " + linea);
                            contadorErrores++;
                            continue;
                        }
                        
                        tablaHash.insertarHash(curso, nivel, asignatura, material);
                        
                        String nivelStr = (nivel == 0) ? "Primaria" : "Secundaria";
                        System.out.println("Cargado: " + nivelStr + " - Curso " + curso + " - " + asignatura + " - " + material);
                        contadorExitosos++;
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error en linea " + contadorLineas + ": Numeros invalidos - " + linea);
                        contadorErrores++;
                    } catch (Exception e) {
                        System.out.println("Error en linea " + contadorLineas + ": " + e.getMessage());
                        contadorErrores++;
                    }
                }
                
                System.out.println("\n=== RESUMEN DE CARGA ===");
                System.out.println("Lineas procesadas: " + contadorLineas);
                System.out.println("Registros exitosos: " + contadorExitosos);
                System.out.println("Errores encontrados: " + contadorErrores);
                System.out.println("Archivo cargado exitosamente.");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado. Verifique que el archivo existe en el directorio actual.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
}
