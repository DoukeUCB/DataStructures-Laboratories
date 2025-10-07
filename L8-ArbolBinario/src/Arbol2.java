import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

class Arbol2<T> {
    private static final int ALTURA = 4;
    private static final int TAM = (int) Math.pow(2, ALTURA + 1) - 1;
    private Par<T>[] vec;
    private Random rand;
    
    public Arbol2() {
        vec = new Par[TAM];
        for (int i = 0; i < TAM; i++) {
            vec[i] = new Par<T>();
        }
        rand = new Random();
    }
    
    public void insertar(T el) {
        insertar(el, 0);
    }
    
    private void insertar(T el, int i) {
        if (i >= TAM)return;
        
        if (vec[i].getBandera() == false) {
            vec[i].setElem(el);
            vec[i].setBandera(true);
        } else {
            if (rand.nextInt(2) == 0) {
                insertar(el, i * 2 + 1);
            } else {
                insertar(el, i * 2 + 2);
            }
        }
    }
    
    public int contarNodos() {
        return contarNodos(0);
    }
    
    private int contarNodos(int i) {
        if (i >= TAM || vec[i].getBandera() == false) {
            return 0;
        }
        return 1 + contarNodos(i * 2 + 1) + contarNodos(i * 2 + 2);
    }
    
    public int calcularAltura() {
        return calcAlt(0);
    }
    
    private int calcAlt(int i) {
        if (i >= TAM || vec[i].getBandera() == false) {
            return 0;
        } else {
            int A1 = calcAlt(i * 2 + 1);
            int A2 = calcAlt(i * 2 + 2);
            return 1 + Math.max(A1, A2);
        }
    }
    
    public void mostrarInOrder() {
        System.out.print("Recorrido In-Order: ");
        inOrder(0);
        System.out.println();
    }
    
    private void inOrder(int i) {
        if (i >= TAM || vec[i].getBandera() == false) {
            return;
        }
        
        inOrder(i * 2 + 1);
        System.out.print(vec[i].getElem() + " ");
        inOrder(i * 2 + 2);
    }
    
    public void leerDesdeArchivo(String nombreArchivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            String linea;
            
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (!linea.isEmpty()) {

                    T elemento = (T) Integer.valueOf(Integer.parseInt(linea));
                    insertar(elemento);
                }
            }
            br.close();
            System.out.println("Datos cargados desde archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error de formato en el archivo: " + e.getMessage());
        }
    }
    
    public void mostrarPorNiveles() {
        System.out.println("Árbol por niveles:");
        int nivel = 1;
        int nodosEnNivel = 1;
        int indiceInicial = 0;
        
        while (indiceInicial < TAM) {
            System.out.print("Nivel " + nivel + ": ");
            boolean hayNodosEnNivel = false;
            
            for (int i = 0; i < nodosEnNivel && (indiceInicial + i) < TAM; i++) {
                int indiceActual = indiceInicial + i;
                if (vec[indiceActual].getBandera()) {
                    System.out.print(vec[indiceActual].getElem() + " ");
                    hayNodosEnNivel = true;
                } else {
                    System.out.print("- ");
                }
            }
            
            System.out.println();
            
            if (!hayNodosEnNivel) {
                break;
            }
            
            indiceInicial += nodosEnNivel;
            nodosEnNivel *= 2;
            nivel++;
        }
    }
    
    public void mostrarArbol() {
        System.out.println("Contenido del árbol (índice: valor):");
        for (int i = 0; i < TAM; i++) {
            if (vec[i].getBandera()) {
                System.out.println("Índice " + i + ": " + vec[i].getElem());
            }
        }
    }
    
    public void insertarDatosRandom(int cantidad) {
        Random r = new Random();
        for (int i = 0; i < cantidad; i++) {
            T elemento = (T) Integer.valueOf(r.nextInt(100) + 1);
            insertar(elemento);
        }
        System.out.println("Se insertaron " + cantidad + " datos aleatorios");
    }
}
