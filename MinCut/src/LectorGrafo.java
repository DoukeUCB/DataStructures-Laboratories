import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class LectorGrafo {

    public GrafoCiudades leerGrafo(InputStream flujoEntrada) throws IOException {
        GrafoCiudades grafo = new GrafoCiudades();
        
        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(flujoEntrada, StandardCharsets.UTF_8))) {
            
            String lineaActual;
            
            while ((lineaActual = lector.readLine()) != null) {
                lineaActual = lineaActual.trim();
                
                if (lineaActual.isEmpty()) {
                    continue;
                }
                
                String[] palabras = lineaActual.split("\\s+");
                
                if (palabras.length < 2) {
                    grafo.registrarCiudad(palabras[0]);
                    continue;
                }
                
                String ciudadOrigen = palabras[0];
                grafo.registrarCiudad(ciudadOrigen);
                
                for (int i = 1; i < palabras.length; i++) {
                    String ciudadVecina = palabras[i];
                    
                    if ("-1".equals(ciudadVecina)) {
                        break;
                    }
                    
                    grafo.agregarArista(ciudadOrigen, ciudadVecina);
                }
            }
        }
        
        return grafo;
    }
}
