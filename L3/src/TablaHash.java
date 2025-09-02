public class TablaHash {
    private static final int TAM = 12;
    private final ListaLigada[] tabla;
    
    public TablaHash() {
        tabla = new ListaLigada[TAM];
        for (int i = 0; i < TAM; i++) {
            tabla[i] = new ListaLigada();
        }
    }
    
    private int funcionHash(int curso, int nivel) {
        return (curso - 1) + (nivel * 6);
    }
    
    public void insertarHash(int curso, int nivel, String asignatura, String material) {
        if (curso >= 1 && curso <= 6 && (nivel == 0 || nivel == 1)) {
            int posicion = funcionHash(curso, nivel);
            tabla[posicion].insertarAsignatura(asignatura, material);
        } else {
            System.out.println("Error: El curso debe estar entre 1 y 6, y el nivel debe ser 0 (primaria) o 1 (secundaria)");
        }
    }
    
    public boolean eliminar(int curso, int nivel, String material) {
        if (curso >= 1 && curso <= 6 && (nivel == 0 || nivel == 1)) {
            int posicion = funcionHash(curso, nivel);
            return tabla[posicion].eliminarMaterial(material);
        } else {
            System.out.println("Error: El curso debe estar entre 1 y 6, y el nivel debe ser 0 (primaria) o 1 (secundaria)");
            return false;
        }
    }
    
    public void mostrarTodo() {
        System.out.println("\n=== TABLA HASH DE MATERIALES POR CURSO ===");
        System.out.println("\nPRIMARIA:");
        for (int i = 0; i < 6; i++) {
            System.out.println("\n  CURSO " + (i + 1) + ":");
            if (!tabla[i].estaVacia()) {
                tabla[i].mostrarAsignaturas();
            } else {
                System.out.println("    Sin asignaturas registradas");
            }
        }
        System.out.println("\nSECUNDARIA:");
        for (int i = 6; i < TAM; i++) {
            System.out.println("\n  CURSO " + (i - 5) + ":");
            if (!tabla[i].estaVacia()) {
                tabla[i].mostrarAsignaturas();
            } else {
                System.out.println("    Sin asignaturas registradas");
            }
        }
    }
    
    public boolean buscarMaterialesDeAsignatura(int curso, int nivel, String asignatura) {
        if (curso >= 1 && curso <= 6 && (nivel == 0 || nivel == 1)) {
            int posicion = funcionHash(curso, nivel);
            String nivelStr = (nivel == 0) ? "PRIMARIA" : "SECUNDARIA";
            System.out.println("Buscando en " + nivelStr + " - CURSO " + curso + ":");
            return tabla[posicion].buscarMaterialesDeAsignatura(asignatura);
        } else {
            System.out.println("Error: El curso debe estar entre 1 y 6, y el nivel debe ser 0 (primaria) o 1 (secundaria)");
            return false;
        }
    }
    
    public void mostrarCurso(int curso, int nivel) {
        if (curso >= 1 && curso <= 6 && (nivel == 0 || nivel == 1)) {
            int posicion = funcionHash(curso, nivel);
            String nivelStr = (nivel == 0) ? "PRIMARIA" : "SECUNDARIA";
            System.out.println("\n" + nivelStr + " - CURSO " + curso + ":");
            if (!tabla[posicion].estaVacia()) {
                tabla[posicion].mostrarAsignaturas();
            } else {
                System.out.println("    Sin asignaturas registradas");
            }
        } else {
            System.out.println("Error: El curso debe estar entre 1 y 6, y el nivel debe ser 0 (primaria) o 1 (secundaria)");
        }
    }
}
