import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GrafoCiudades {

    private final List<String> nombresCiudades = new ArrayList<>();
    private final List<Arista> listaDeAristas = new ArrayList<>();

    public int registrarCiudad(String nombreCiudad) {
        int indiceExistente = buscarIndice(nombreCiudad);
        if (indiceExistente >= 0) {
            return indiceExistente;
        }

        nombresCiudades.add(nombreCiudad);
        return nombresCiudades.size() - 1;
    }

    public void agregarArista(String ciudadOrigen, String ciudadDestino) {
        int indiceOrigen = registrarCiudad(ciudadOrigen);
        int indiceDestino = registrarCiudad(ciudadDestino);

        if (indiceOrigen == indiceDestino) {
            return;
        }

        if (existeArista(indiceOrigen, indiceDestino)) {
            return;
        }

        listaDeAristas.add(new Arista(indiceOrigen, indiceDestino));
    }

    public int cantidadDeCiudades() {
        return nombresCiudades.size();
    }

    public int cantidadDeAristas() {
        return listaDeAristas.size();
    }

    public List<String> obtenerNombresCiudades() {
        return Collections.unmodifiableList(nombresCiudades);
    }

    public List<Arista> obtenerAristas() {
        return Collections.unmodifiableList(listaDeAristas);
    }

    public String obtenerNombrePorIndice(int indice) {
        if (indice < 0 || indice >= nombresCiudades.size()) {
            throw new IndexOutOfBoundsException("Índice de ciudad inválido: " + indice);
        }
        return nombresCiudades.get(indice);
    }

    private int buscarIndice(String nombreCiudad) {
        for (int i = 0; i < nombresCiudades.size(); i++) {
            if (nombresCiudades.get(i).equals(nombreCiudad)) {
                return i;
            }
        }
        return -1;
    }

    private boolean existeArista(int indiceA, int indiceB) {
        for (Arista arista : listaDeAristas) {
            boolean mismosExtremos =
                (arista.indiceCiudad1 == indiceA && arista.indiceCiudad2 == indiceB) ||
                (arista.indiceCiudad1 == indiceB && arista.indiceCiudad2 == indiceA);
            if (mismosExtremos) {
                return true;
            }
        }
        return false;
    }

    public static final class Arista {
        private final int indiceCiudad1;
        private final int indiceCiudad2;

        public Arista(int indiceCiudad1, int indiceCiudad2) {
            this.indiceCiudad1 = indiceCiudad1;
            this.indiceCiudad2 = indiceCiudad2;
        }

        public int obtenerCiudad1() {
            return indiceCiudad1;
        }

        public int obtenerCiudad2() {
            return indiceCiudad2;
        }

        @Override
        public String toString() {
            return "(" + indiceCiudad1 + " <-> " + indiceCiudad2 + ")";
        }
    }
}
