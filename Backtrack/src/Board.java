public class Board {
    private final int numeroFilas;
    private final int numeroColumnas;
    private final int[][] tablero;
    private int casillasVisitadas;

    public Board(int filas, int columnas) {
        this.numeroFilas = filas;
        this.numeroColumnas = columnas;
        this.tablero = new int[filas][columnas];
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                tablero[fila][columna] = -1;
            }
        }
        this.casillasVisitadas = 0;
    }

    public boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < numeroFilas && columna >= 0 && columna < numeroColumnas;
    }

    public boolean esCasillaVisitada(int fila, int columna) {
        return tablero[fila][columna] != -1;
    }

    public void marcarCasilla(int fila, int columna, int numeroPaso) {
        if (!esCasillaVisitada(fila, columna)) {
            tablero[fila][columna] = numeroPaso;
            casillasVisitadas++;
        }
    }

    public void desmarcarCasilla(int fila, int columna) {
        if (esCasillaVisitada(fila, columna)) {
            tablero[fila][columna] = -1;
            casillasVisitadas--;
        }
    }

    public boolean todasCasillasVisitadas() {
        return casillasVisitadas == numeroFilas * numeroColumnas;
    }

    public void imprimir() {
        for (int fila = 0; fila < numeroFilas; fila++) {
            for (int columna = 0; columna < numeroColumnas; columna++) {
                System.out.printf("%3d ", tablero[fila][columna]);
            }
            System.out.println();
        }
    }

    public int getTamanioTotal() {
        return numeroFilas * numeroColumnas;
    }
    
    public int getNumeroFilas() {
        return numeroFilas;
    }
    
    public int getNumeroColumnas() {
        return numeroColumnas;
    }
}
