import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BST<Integer> arbol = new BST<>();
        Scanner scanner = new Scanner(System.in);
        int opcion;
        
        do {
            System.out.println("\nBST");
            System.out.println("1. Insertar");
            System.out.println("2. Contar nodos");
            System.out.println("3. Calcular altura");
            System.out.println("4. Mostrar in order");
            System.out.println("5. Mostrar un nivel dado");
            System.out.println("6. Mostrar por niveles");
            System.out.println("7. Buscar elemento");
            System.out.println("8. Menor");
            System.out.println("9. Mayor");
            System.out.println("10. Eliminar elemento");
            System.out.println("0. Salir");
            System.out.println("");
            System.out.print("Seleccione una opcion: ");
            
            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese el elemento a insertar: ");
                    int elemento = scanner.nextInt();
                    arbol.setRaiz(arbol.insertar(arbol.getRaiz(), elemento));
                    System.out.println("Elemento insertado correctamente.");
                }
                    
                case 2 -> System.out.println("Cantidad de nodos: " + arbol.contarNodos(arbol.getRaiz()));
                    
                case 3 -> System.out.println("Altura del árbol: " + arbol.calcularAltura(arbol.getRaiz()));
                    
                case 4 -> {
                    if (arbol.estaVacio()) {
                        System.out.println("El árbol está vacío.");
                    } else {
                        System.out.print("InOrder: ");
                        arbol.mostrarInOrder(arbol.getRaiz());
                        System.out.println();
                    }
                }
                    
                case 5 -> {
                    if (arbol.estaVacio()) {
                        System.out.println("El árbol está vacío.");
                    } else {
                        System.out.print("Ingrese el nivel a mostrar: ");
                        int nivel = scanner.nextInt();
                        System.out.print("Nivel " + nivel + ": ");
                        arbol.mostrarNivel(arbol.getRaiz(), nivel, 1);
                        System.out.println();
                    }
                }
                    
                case 6 -> {
                    if (arbol.estaVacio()) {
                        System.out.println("El árbol está vacío.");
                    } else {
                        arbol.mostrarPorNiveles(arbol.getRaiz());
                    }
                }
                    
                case 7 -> {
                    System.out.print("Ingrese el elemento a buscar: ");
                    int buscar = scanner.nextInt();
                    NOD<Integer> resultado = arbol.buscarElemento(buscar, arbol.getRaiz());
                    if (resultado != null) {
                        System.out.println("Elemento " + resultado.getElement() + " encontrado.");
                    } else {
                        System.out.println("Elemento no encontrado.");
                    }
                }
                    
                case 8 -> {
                    NOD<Integer> menor = arbol.menor(arbol.getRaiz());
                    if (menor != null) {
                        System.out.println("Elemento menor: " + menor.getElement());
                    } else {
                        System.out.println("El árbol está vacío.");
                    }
                }
                    
                case 9 -> {
                    NOD<Integer> mayor = arbol.mayor(arbol.getRaiz());
                    if (mayor != null) {
                        System.out.println("Elemento mayor: " + mayor.getElement());
                    } else {
                        System.out.println("El árbol está vacío.");
                    }
                }
                    
                case 10 -> {
                    if (arbol.estaVacio()) {
                        System.out.println("El árbol está vacío.");
                    } else {
                        System.out.print("Ingrese el elemento a eliminar: ");
                        int eliminar = scanner.nextInt();
                        boolean eliminado = arbol.eliminar(arbol.getRaiz(), eliminar);
                        if (eliminado) {
                            System.out.println("Elemento " + eliminar + " eliminado correctamente.");
                        } else {
                            System.out.println("Elemento " + eliminar + " no encontrado.");
                        }
                    }
                }
                    
                case 0 -> System.out.println("Saliendo del programa...");
                    
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
        
        scanner.close();
    }
}