import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class ProgramaPrincipal {

    private final Scanner lectorEntrada = new Scanner(System.in);
    private GrafoCiudades grafoActual;
    private int iteracionesPersonalizadas = -1;

    public static void main(String[] args) throws IOException {
        new ProgramaPrincipal().ejecutar();
    }

    private void ejecutar() throws IOException {
        System.out.println("CALCULADORA DE CORTE MÍNIMO (KARGER)");
        boolean salir = false;

        while (!salir) {
            switch (mostrarMenuYLeerOpcion()) {
                case 1 -> cargarGrafoDesdeArchivo();
                case 2 -> configurarIteraciones();
                case 3 -> ejecutarAlgoritmo();
                case 4 -> salir = true;
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        System.out.println("\n¡Hasta Luego!");
    }

    private int mostrarMenuYLeerOpcion() {
        System.out.println();
        System.out.println(" MENÚ PRINCIPAL");
        System.out.println(" 1) Cargar grafo desde archivo");
        System.out.println(" 2) Configurar iteraciones (" + obtenerEtiquetaIteraciones() + ")");
        System.out.println(" 3) Buscar corte mínimo");
        System.out.println(" 4) Salir");
        System.out.print("Seleccione una opción: ");

        String lineaIngresada = lectorEntrada.nextLine();
        return convertirAEntero(lineaIngresada, -1);
    }

    private void cargarGrafoDesdeArchivo() {
        System.out.print("\nIngrese la ruta del archivo con el grafo: ");
        String rutaIngresada = lectorEntrada.nextLine().trim();

        if (rutaIngresada.isEmpty()) {
            System.out.println("Ruta vacía. Operación cancelada.");
            return;
        }

        Path rutaArchivo = Path.of(rutaIngresada);

        if (!Files.exists(rutaArchivo)) {
            System.out.println("El archivo no existe: " + rutaArchivo);
            return;
        }

        LectorGrafo lector = new LectorGrafo();

        try (InputStream flujoArchivo = Files.newInputStream(rutaArchivo)) {
            grafoActual = lector.leerGrafo(flujoArchivo);

            if (grafoActual.cantidadDeCiudades() < 2) {
                System.out.println("Advertencia: el grafo tiene menos de 2 ciudades.");
            } else {
                System.out.printf("Grafo cargado exitosamente:%n");
                System.out.printf("  • Ciudades: %d%n", grafoActual.cantidadDeCiudades());
                System.out.printf("  • Conexiones: %d%n", grafoActual.cantidadDeAristas());
            }
        } catch (IOException excepcion) {
            System.out.println("Error al leer el archivo: " + excepcion.getMessage());
        }
    }

    private void configurarIteraciones() {
        if (!verificarGrafoCargado()) {
            return;
        }

        int sugerencia = AlgoritmoKarger.calcularIteracionesRecomendadas(grafoActual.cantidadDeCiudades());

        System.out.println();
        System.out.println("CONFIGURACIÓN DE ITERACIONES DEL ALGORITMO");
        System.out.printf("Ciudades en el grafo: %d%n", grafoActual.cantidadDeCiudades());
        System.out.printf("Iteraciones sugeridas (n²·log n): %d%n", sugerencia);

        System.out.print("\nIngrese número de iteraciones (0 para usar la sugerencia): ");
        int valorIngresado = convertirAEntero(lectorEntrada.nextLine(), -1);

        if (valorIngresado < 0) {
            System.out.println("Valor inválido. Se mantiene la configuración actual.");
            return;
        }

        iteracionesPersonalizadas = (valorIngresado == 0) ? -1 : valorIngresado;
    }

    private void ejecutarAlgoritmo() {
        if (!verificarGrafoCargado()) {
            return;
        }

        if (grafoActual.cantidadDeAristas() == 0) {
            System.out.println("El grafo no tiene aristas.");
            return;
        }

        AlgoritmoKarger algoritmo = new AlgoritmoKarger();

        int iteracionesAEjecutar = (iteracionesPersonalizadas > 0)
            ? iteracionesPersonalizadas
            : AlgoritmoKarger.calcularIteracionesRecomendadas(grafoActual.cantidadDeCiudades());

        System.out.println();
        System.out.println("BUSCANDO CORTE MÍNIMO");
        System.out.printf("Ciudades: %d | Aristas: %d | Iteraciones: %d%n",
            grafoActual.cantidadDeCiudades(),
            grafoActual.cantidadDeAristas(),
            iteracionesAEjecutar);

        try {
            AlgoritmoKarger.ResultadoMinimoCorte resultado =
                algoritmo.encontrarCorteMinimo(grafoActual, iteracionesAEjecutar);
            mostrarResultado(resultado);
        } catch (IllegalArgumentException | IllegalStateException error) {
            System.out.println("Error al ejecutar el algoritmo: " + error.getMessage());
        }
    }

    private void mostrarResultado(AlgoritmoKarger.ResultadoMinimoCorte resultado) {
        System.out.println();
        System.out.println("═ RESULTADO ═");
        System.out.printf("Corte mínimo encontrado: %d aristas%n", resultado.obtenerTamanoCorte());
        System.out.printf("Iteración en la que apareció: %d%n", resultado.obtenerIteracionEncontrada());

        mostrarParticion("Partición A", resultado.obtenerParticionA());
        mostrarParticion("Partición B", resultado.obtenerParticionB());
        mostrarAristasCorte(resultado.obtenerAristasCorte());
        mostrarAnalisisComplejidad();
    }

    private void mostrarParticion(String titulo, List<String> ciudades) {
        System.out.println();
        System.out.printf("%s (%d ciudades):%n", titulo, ciudades.size());
        if (ciudades.isEmpty()) {
            System.out.println("  (vacío)");
            return;
        }

        for (int i = 0; i < ciudades.size(); i++) {
            System.out.printf("  %3d) %s%n", i + 1, ciudades.get(i));
        }
    }

    private void mostrarAristasCorte(List<String> aristas) {
        System.out.println();
        System.out.printf("Aristas que conforman el corte (%d):%n", aristas.size());
        if (aristas.isEmpty()) {
            System.out.println("  No se encontraron aristas entre las particiones.");
            return;
        }

        for (int i = 0; i < aristas.size(); i++) {
            System.out.printf("  %3d) %s%n", i + 1, aristas.get(i));
        }
    }

    private void mostrarAnalisisComplejidad() {
        System.out.println();
        System.out.println("─ Complejidad y diseño ─");
        System.out.println("• Selección aleatoria: O(m). Elegimos una arista de la lista completa sin estructuras auxiliares.");
        System.out.println("• Contracción: O(m). Cada contracción recorre las aristas para redirigir extremos y eliminar lazos.");
        System.out.println("• Iteración completa de Karger: O(n·m). Se ejecutan n−2 contracciones preservando aristas paralelas.");
        System.out.println("• Iteraciones recomendadas (n²·log n) elevan la probabilidad de encontrar el corte mínimo.");
        System.out.println("• Se usan listas simples para mantener paralelismo y permitir contracciones sin union-find, como requiere Karger.");
    }

    private boolean verificarGrafoCargado() {
        if (grafoActual == null) {
            System.out.println("Primero debe cargar un grafo (opción 1).");
            return false;
        }
        return true;
    }

    private String obtenerEtiquetaIteraciones() {
        if (iteracionesPersonalizadas > 0) {
            return String.valueOf(iteracionesPersonalizadas);
        }
        if (grafoActual != null) {
            return AlgoritmoKarger.calcularIteracionesRecomendadas(grafoActual.cantidadDeCiudades()) + " (n²·log n)";
        }
        return "n²·log n";
    }

    private static int convertirAEntero(String texto, int valorPorDefecto) {
        if (texto == null) {
            return valorPorDefecto;
        }
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException excepcion) {
            return valorPorDefecto;
        }
    }


}
