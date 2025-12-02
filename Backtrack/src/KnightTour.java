public class KnightTour {
    // Movimientos del caballo, 8 posibles movimientos
    private static final int[] desplazamientoFilas = {-2, -1, 1, 2, 2, 1, -1, -2};
    private static final int[] desplazamientoColumnas = {1, 2, 2, 1, -1, -2, -2, -1};

    private final Board tablero;
    private boolean solucionEncontrada;

    public KnightTour(int numeroFilas, int numeroColumnas) {
        tablero = new Board(numeroFilas, numeroColumnas);
        solucionEncontrada = false;
    }

    public boolean resolver(int filaInicial, int columnaInicial) {
        if (!tablero.esPosicionValida(filaInicial, columnaInicial)) {
            System.out.println("Posición inicial fuera de rango.");
            return false;
        }
        
        tablero.marcarCasilla(filaInicial, columnaInicial, 0);
        solucionEncontrada = backtrack(filaInicial, columnaInicial, 1);
        return solucionEncontrada;
    }

    private boolean backtrack(int filaActual, int columnaActual, int numeroPaso) {
        boolean exito;
        
        // Si (Estado = Estado Final) entonces Éxito = Si
        if (tablero.todasCasillasVisitadas()) {
            exito = true;
        } else {
            // C/c: Mientras (Éxito = No y Existan Reglas) hacer
            exito = false;
            int movimiento = 0;
            
            while (!exito && movimiento < 8) {
                // SubRegla <- Seleccionar(Regla)
                int nuevaFila = filaActual + desplazamientoFilas[movimiento];
                int nuevaColumna = columnaActual + desplazamientoColumnas[movimiento];
                
                // Si (SubRegla = Aplicable) entonces
                if (tablero.esPosicionValida(nuevaFila, nuevaColumna) && 
                    !tablero.esCasillaVisitada(nuevaFila, nuevaColumna)) {
                    
                    // Estado2 <- Aplicar(Estado, SubRegla)
                    tablero.marcarCasilla(nuevaFila, nuevaColumna, numeroPaso);
                    
                    // Éxito <- Backtrack(Estructura, Reglas, Estado2)
                    exito = backtrack(nuevaFila, nuevaColumna, numeroPaso + 1);
                    
                    // Si (Éxito = No) entonces Estado <- VolverAtras(Estado2, SubRegla)
                    if (!exito) {
                        tablero.desmarcarCasilla(nuevaFila, nuevaColumna);
                    }
                }
                
                movimiento++;
            }
        }
        
        // Terminar devolviendo Éxito
        return exito;
    }

    public void imprimirSolucion() {
        tablero.imprimir();
    }

    public Board getTablero() {
        return tablero;
    }
}
