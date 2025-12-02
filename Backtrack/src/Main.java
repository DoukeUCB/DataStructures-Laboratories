import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("MOVIMIENTOS DEL CABALLO\n");
            
            int numeroFilas = 0;
            int numeroColumnas = 0;
            int filaInicial = 0;
            int columnaInicial = 0;
            
            while (numeroFilas <= 0) {
                try {
                    System.out.print("Ingrese el número de filas del tablero: ");
                    numeroFilas = Integer.parseInt(scanner.nextLine().trim());
                    if (numeroFilas <= 0) {
                        System.out.println("Error: El número de filas debe ser mayor a 0.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número entero.\n");
                }
            }
            
            while (numeroColumnas <= 0) {
                try {
                    System.out.print("Ingrese el número de columnas del tablero: ");
                    numeroColumnas = Integer.parseInt(scanner.nextLine().trim());
                    if (numeroColumnas <= 0) {
                        System.out.println("Error: El número de columnas debe ser mayor a 0.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número entero.\n");
                }
            }
            
            boolean filaValida = false;
            while (!filaValida) {
                try {
                    System.out.print("Ingrese la fila inicial del caballo (0 a " + (numeroFilas - 1) + "): ");
                    filaInicial = Integer.parseInt(scanner.nextLine().trim());
                    if (filaInicial < 0 || filaInicial >= numeroFilas) {
                        System.out.println("Error: La fila debe estar entre 0 y " + (numeroFilas - 1) + ".\n");
                    } else {
                        filaValida = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número entero.\n");
                }
            }
            
            boolean columnaValida = false;
            while (!columnaValida) {
                try {
                    System.out.print("Ingrese la columna inicial del caballo (0 a " + (numeroColumnas - 1) + "): ");
                    columnaInicial = Integer.parseInt(scanner.nextLine().trim());
                    if (columnaInicial < 0 || columnaInicial >= numeroColumnas) {
                        System.out.println("Error: La columna debe estar entre 0 y " + (numeroColumnas - 1) + ".\n");
                    } else {
                        columnaValida = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número entero.\n");
                }
            }
            
            System.out.println("\nEjecutando backtracking " + numeroFilas + " x " + numeroColumnas);
            System.out.println(" Comenzando en la posicion (" + filaInicial + ", " + columnaInicial + ")...\n");
            
            KnightTour tourCaballo = new KnightTour(numeroFilas, numeroColumnas);
            long tiempoInicio = System.currentTimeMillis();
            boolean solucionEncontrada = tourCaballo.resolver(filaInicial, columnaInicial);
            long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
            
            if (solucionEncontrada) {
                System.out.println("EXITO");
                System.out.println("Tiempo de ejecución: " + tiempoTranscurrido + " ms\n");
                System.out.println("Solucion:");
                tourCaballo.imprimirSolucion();
            } else {
                System.out.println("FRACASO");
                System.out.println("Tiempo de ejecución: " + tiempoTranscurrido + " ms\n");
                System.out.println("Estado parcial del tablero:");
                tourCaballo.imprimirSolucion();
            }
        }
    }
}