import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class AlgoritmoKarger {

    /*
     * Notación: N = número de ciudades (nodos) y M = número de aristas originales.
     * Cada comentario de complejidad describe el costo de la línea / bloque inmediato.
     */

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public ResultadoMinimoCorte encontrarCorteMinimo(GrafoCiudades grafo, int iteracionesSolicitadas) {
        if (grafo.cantidadDeCiudades() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 ciudades para calcular un corte mínimo.");
        }
        if (grafo.cantidadDeAristas() == 0) {
            throw new IllegalArgumentException("El grafo no tiene aristas.");
        }

        int iteraciones = iteracionesSolicitadas > 0
            ? iteracionesSolicitadas
            : calcularIteracionesRecomendadas(grafo.cantidadDeCiudades());

        List<String> nombresCiudades = grafo.obtenerNombresCiudades(); // O(1) al compartir referencia interna.
        List<GrafoCiudades.Arista> aristasOriginales = grafo.obtenerAristas(); // O(1) referencia inmutable.
        ResultadoMinimoCorte mejorResultado = null;
        int mejorCorteHastaAhora = Integer.MAX_VALUE;
        int intervaloReporte = Math.max(1, iteraciones / 10);

        for (int i = 1; i <= iteraciones; i++) { // O(iteraciones * costoIteracion)
            ResultadoIteracion resultadoActual = ejecutarIteracion(nombresCiudades, aristasOriginales); // O(N*M) ver método.

            if (!resultadoActual.esValido()) {
                continue;
            }

            if (mejorResultado == null || resultadoActual.tamanoCorte < mejorResultado.tamanoCorte) {
                mejorCorteHastaAhora = resultadoActual.tamanoCorte;
                mejorResultado = new ResultadoMinimoCorte(
                    resultadoActual.particionA,
                    resultadoActual.particionB,
                    resultadoActual.aristasCorte,
                    resultadoActual.tamanoCorte,
                    i
                );
            }

            if (i == 1 || i % intervaloReporte == 0 || i == iteraciones) { // Salidas O(1) cada ~10% de iteraciones.
                System.out.printf("[Iter %d/%d] Mejor corte hasta ahora: %s%n",
                    i,
                    iteraciones,
                    (mejorCorteHastaAhora == Integer.MAX_VALUE ? "-" : mejorCorteHastaAhora + " aristas"));
            }
        }

        if (mejorResultado == null) {
            throw new IllegalStateException("No fue posible encontrar un corte válido. Verifique el grafo de entrada.");
        }

        return mejorResultado;
    }

    public static int calcularIteracionesRecomendadas(int numeroCiudades) {
        if (numeroCiudades <= 1) {
            return 1;
        }
        double logN = Math.max(1.0, Math.log(numeroCiudades)); // O(1)
        double estimacion = Math.pow(numeroCiudades, 2) * logN; // O(1)
        long valor = Math.round(estimacion); // O(1)
        if (valor <= 0) {
            valor = numeroCiudades * (long) numeroCiudades; // O(1)
        }
        return (valor > Integer.MAX_VALUE) ? Integer.MAX_VALUE : (int) valor; // O(1)
    }

    private ResultadoIteracion ejecutarIteracion(List<String> nombresCiudades, List<GrafoCiudades.Arista> aristasOriginales) {
        List<NodoSuper> nodos = construirNodos(nombresCiudades); // O(N)
        List<AristaMutable> aristas = construirAristas(aristasOriginales, nodos); // O(M)

        if (aristas.isEmpty()) {
            return ResultadoIteracion.invalido();
        }

        while (nodos.size() > 2 && !aristas.isEmpty()) { // Se ejecuta N-2 veces ⇒ O(N).
            AristaMutable seleccionada = aristas.get(random.nextInt(aristas.size())); // O(1) selección uniforme.

            if (seleccionada.origen == seleccionada.destino) {
                aristas.remove(seleccionada); // O(1) amortizado sobre ArrayList.
                continue;
            }

            contraer(seleccionada.origen, seleccionada.destino, nodos, aristas); // O(M) por contracción.
        }

        if (nodos.size() != 2) {
            return ResultadoIteracion.invalido();
        }

        List<String> particionA = new ArrayList<>(nodos.get(0).ciudades); // O(N)
        List<String> particionB = new ArrayList<>(nodos.get(1).ciudades); // O(N)
        List<String> aristasCorte = recopilarAristas(particionA, particionB, aristasOriginales, nombresCiudades); // O(M)

        return new ResultadoIteracion(particionA, particionB, aristasCorte, aristasCorte.size());
    }

    private void contraer(NodoSuper principal, NodoSuper absorbida, List<NodoSuper> nodos, List<AristaMutable> aristas) {
        principal.ciudades.addAll(absorbida.ciudades); // O(tamaño de absorbida) pero total amortizado O(N).

        for (AristaMutable arista : aristas) { // Recorre todas las aristas ⇒ O(M) por contracción.
            if (arista.origen == absorbida) {
                arista.origen = principal;
            }
            if (arista.destino == absorbida) {
                arista.destino = principal;
            }
        }

        aristas.removeIf(arista -> arista.origen == arista.destino); // O(M) filtrando lazos.
        nodos.remove(absorbida); // O(N) pero solo una vez por contracción.
    }

    private static List<NodoSuper> construirNodos(List<String> nombresCiudades) {
        List<NodoSuper> nodos = new ArrayList<>(nombresCiudades.size());
        for (String nombre : nombresCiudades) { // Recorre todas las ciudades ⇒ O(N).
            nodos.add(new NodoSuper(nombre)); // Inserción O(1) amortizado.
        }
        return nodos;
    }

    private static List<AristaMutable> construirAristas(List<GrafoCiudades.Arista> aristasOriginales, List<NodoSuper> nodos) {
        List<AristaMutable> aristas = new ArrayList<>(aristasOriginales.size());
        for (GrafoCiudades.Arista arista : aristasOriginales) { // Recorre todas las aristas ⇒ O(M).
            NodoSuper origen = nodos.get(arista.obtenerCiudad1()); // O(1).
            NodoSuper destino = nodos.get(arista.obtenerCiudad2()); // O(1).
            aristas.add(new AristaMutable(origen, destino)); // O(1) amortizado.
        }
        return aristas;
    }

    private static List<String> recopilarAristas(List<String> particionA,
                                                 List<String> particionB,
                                                 List<GrafoCiudades.Arista> aristasOriginales,
                                                 List<String> nombresCiudades) {
        List<String> aristas = new ArrayList<>(); // O(1) creación.
        var conjuntoA = new java.util.HashSet<>(particionA); // O(|ParticiónA|) ⊆ O(N).

        for (GrafoCiudades.Arista arista : aristasOriginales) { // Recorre M aristas ⇒ O(M).
            String ciudad1 = nombresCiudades.get(arista.obtenerCiudad1());
            String ciudad2 = nombresCiudades.get(arista.obtenerCiudad2());
            boolean enA1 = conjuntoA.contains(ciudad1); // O(1) promedio.
            boolean enA2 = conjuntoA.contains(ciudad2); // O(1) promedio.
            if (enA1 != enA2) {
                aristas.add(ciudad1 + " ↔ " + ciudad2);
            }
        }

        return aristas;
    }

    private static final class NodoSuper {
        private final List<String> ciudades = new ArrayList<>();

        NodoSuper(String nombreCiudad) {
            ciudades.add(nombreCiudad);
        }
    }

    private static final class AristaMutable {
        private NodoSuper origen;
        private NodoSuper destino;

        AristaMutable(NodoSuper origen, NodoSuper destino) {
            this.origen = origen;
            this.destino = destino;
        }
    }

    private record ResultadoIteracion(List<String> particionA,
                                      List<String> particionB,
                                      List<String> aristasCorte,
                                      int tamanoCorte) {
        static ResultadoIteracion invalido() {
            return new ResultadoIteracion(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Integer.MAX_VALUE);
        }

        boolean esValido() {
            return tamanoCorte != Integer.MAX_VALUE && !particionA.isEmpty() && !particionB.isEmpty();
        }
    }

    public static final class ResultadoMinimoCorte {
        private final List<String> particionA;
        private final List<String> particionB;
        private final List<String> aristasCorte;
        private final int tamanoCorte;
        private final int iteracionEncontrada;

        private ResultadoMinimoCorte(List<String> particionA,
                                     List<String> particionB,
                                     List<String> aristasCorte,
                                     int tamanoCorte,
                                     int iteracionEncontrada) {
            this.particionA = particionA;
            this.particionB = particionB;
            this.aristasCorte = aristasCorte;
            this.tamanoCorte = tamanoCorte;
            this.iteracionEncontrada = iteracionEncontrada;
        }

        public List<String> obtenerParticionA() {
            return Collections.unmodifiableList(particionA);
        }

        public List<String> obtenerParticionB() {
            return Collections.unmodifiableList(particionB);
        }

        public List<String> obtenerAristasCorte() {
            return Collections.unmodifiableList(aristasCorte);
        }

        public int obtenerTamanoCorte() {
            return tamanoCorte;
        }

        public int obtenerIteracionEncontrada() {
            return iteracionEncontrada;
        }
    }
}
