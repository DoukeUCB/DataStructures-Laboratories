import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Arbol2<Integer> arbol = new Arbol2<>();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        
        do {
            System.out.println("\n Abol binario");
            System.out.println("1. Insertar datos usando RAND");
            System.out.println("2. Contar nodos");
            System.out.println("3. Calcular altura");
            System.out.println("4. Mostrar In-Order");
            System.out.println("5. Leer datos desde archivo");
            System.out.println("6. Mostrar contenido del árbol");
            System.out.println("7. Insertar dato manual");
            System.out.println("8. Mostrar árbol por niveles");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese la cantidad de datos aleatorios a insertar: ");
                    int cantidad = scanner.nextInt();
                    arbol.insertarDatosRandom(cantidad);
                }
                    
                case 2 -> {
                    int nodos = arbol.contarNodos();
                    System.out.println("Número de nodos en el árbol: " + nodos);
                }
                    
                case 3 -> {
                    int altura = arbol.calcularAltura();
                    System.out.println("Altura del árbol: " + altura);
                }
                    
                case 4 -> arbol.mostrarInOrder();
                    
                case 5 -> {
                    System.out.print("Ingrese el nombre del archivo: ");
                    String archivo = scanner.next();
                    arbol.leerDesdeArchivo(archivo);
                }
                    
                case 6 -> arbol.mostrarArbol();
                    
                case 7 -> {
                    System.out.print("Ingrese un número para insertar: ");
                    int numero = scanner.nextInt();
                    arbol.insertar(numero);
                    System.out.println("Número insertado correctamente");
                }
                    
                case 8 -> arbol.mostrarPorNiveles();
                    
                case 0 -> System.out.println("Bye!");
                    
                default -> System.out.println("Opción invalida");
            }
        } while (opcion != 0);
        
        scanner.close();
    }
}