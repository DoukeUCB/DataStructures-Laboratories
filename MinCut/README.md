# Algoritmo de Karger - Corte Mínimo

## 📋 Descripción General

Este proyecto implementa el **Algoritmo de Karger** para encontrar el corte mínimo en un grafo no dirigido. El corte mínimo es el conjunto más pequeño de aristas cuya eliminación divide el grafo en dos componentes desconectados.

---

## 🏗️ Estructura de Datos Utilizada

### **Lista de Ciudades + Lista de Aristas**

```
nombresCiudades: ["Tokio", "París", "Lima", ...]     (List<String>)
listaDeAristas:  [(0,1), (0,2), (1,2), ...]          (List<Arista>)
```

#### ¿Por qué esta estructura?

1. **Simplicidad**: No necesitamos estructuras complejas como HashMap o union-find porque priorizamos la claridad del algoritmo de Karger.

2. **Preservación de aristas paralelas**: Durante la contracción, pueden aparecer múltiples aristas entre los mismos nodos. Las listas mantienen naturalmente estos duplicados, lo cual es esencial para el algoritmo.

3. **Acceso directo por índice**: Usamos índices enteros (0, 1, 2...) para representar ciudades, lo que permite acceso O(1) a los nombres y facilita las operaciones de contracción.

4. **Mutabilidad controlada**: Durante la contracción, necesitamos modificar las referencias de las aristas. Con listas de objetos mutables (`AristaMutable`), podemos cambiar `origen` y `destino` in-place.

#### Respuesta a tu pregunta 1: ¿Es una lista de listas?

**No exactamente**. Es:
- Una **lista de Strings** (nombres de ciudades)
- Una **lista de Aristas** (cada arista tiene dos índices que apuntan a posiciones en la lista de ciudades)

Es más parecido a una **lista de adyacencia implícita** donde las aristas se almacenan como pares de índices.

---

## 🎲 Partes del Algoritmo

### 1️⃣ **AZAR (Selección Aleatoria)**

**Ubicación**: `AlgoritmoKarger.ejecutarIteracion()` - línea con `random.nextInt()`

```java
AristaMutable seleccionada = aristas.get(random.nextInt(aristas.size()));
```

**¿Qué hace?**: Selecciona una arista aleatoria uniformemente de la lista actual.

**Big O**: **O(1)** - Acceso directo por índice en ArrayList.

**Algorítmica**: Generación de números pseudoaleatorios con `ThreadLocalRandom`.

---

### 2️⃣ **KARGER (Iteraciones Múltiples)**

**Ubicación**: `AlgoritmoKarger.encontrarCorteMinimo()` - bucle principal

```java
for (int i = 1; i <= iteraciones; i++) {
    ResultadoIteracion resultadoActual = ejecutarIteracion(...);
    // Guardar el mejor resultado encontrado
}
```

**¿Qué hace?**: Ejecuta el algoritmo de contracción múltiples veces (n²·log n iteraciones) y guarda el mejor corte encontrado.

**Big O**: **O(iteraciones × N × M)** = **O(N²·log N × N × M)** = **O(N³·M·log N)**

**Algorítmica**: 
- **Algoritmo de Monte Carlo**: Usa aleatoriedad para aproximar la solución óptima.
- **Probabilidad de éxito**: Con n²·log n iteraciones, la probabilidad de encontrar el corte mínimo es > 1 - 1/n.

---

### 3️⃣ **CONTRACCIÓN (Fusión de Nodos)**

**Ubicación**: `AlgoritmoKarger.contraer()`

```java
private void contraer(NodoSuper principal, NodoSuper absorbida, ...) {
    principal.ciudades.addAll(absorbida.ciudades);        // Fusionar ciudades
    
    for (AristaMutable arista : aristas) {                // Redirigir aristas
        if (arista.origen == absorbida) 
            arista.origen = principal;
        if (arista.destino == absorbida)
            arista.destino = principal;
    }
    
    aristas.removeIf(arista -> arista.origen == arista.destino);  // Eliminar lazos
    nodos.remove(absorbida);                              // Eliminar nodo absorbido
}
```

**¿Qué hace?**: 
1. Fusiona dos nodos en uno solo (el principal absorbe al otro)
2. Redirige todas las aristas que apuntaban al nodo absorbido
3. Elimina los lazos (aristas que ahora conectan un nodo consigo mismo)

**Big O por contracción**: **O(M)** - Recorre todas las aristas para redirigir referencias.

**Por iteración completa**: **O(N × M)** - Se hacen N-2 contracciones.

**Algorítmica**: 
- **Contracción de aristas**: Técnica fundamental de Karger.
- **Sin union-find**: No usamos estructura de conjuntos disjuntos porque necesitamos mantener las aristas paralelas explícitamente.

---

## 📊 Complejidad Total

| Operación | Complejidad | Justificación |
|-----------|-------------|---------------|
| **Construcción inicial** | O(N + M) | Crear nodos superpuestos y copiar aristas |
| **Selección aleatoria** | O(1) | Acceso directo por índice |
| **Una contracción** | O(M) | Recorrer todas las aristas para redirigir |
| **Una iteración completa** | O(N × M) | N-2 contracciones × O(M) cada una |
| **Algoritmo completo** | O(N³·M·log N) | (N²·log N) iteraciones × O(N×M) cada una |

### Espacio: O(N + M)
Mantenemos copias de los nodos y aristas para cada iteración.

---

## 🔍 Respuesta a tu Pregunta 2: Mejora de Eficiencia

### Problema Actual

```java
private int buscarIndice(String nombreCiudad) {
    for (int i = 0; i < nombresCiudades.size(); i++) {  // O(N)
        if (nombresCiudades.get(i).equals(nombreCiudad)) {
            return i;
        }
    }
    return -1;
}
```

**Complejidad**: O(N) por cada búsqueda.

**Impacto**: Al cargar un grafo con M aristas, hacemos ~2M búsquedas → **O(N × M)** total para construir el grafo.

### ✅ Solución Creativa: Híbrida con Cache

**Idea**: Mantener ambas estructuras en paralelo:
1. **Lista** para acceso por índice O(1)
2. **HashMap** para búsqueda por nombre O(1)

```java
private final List<String> nombresCiudades = new ArrayList<>();
private final Map<String, Integer> nombreAIndice = new HashMap<>();  // ← CACHE

public int registrarCiudad(String nombreCiudad) {
    // Buscar en el HashMap: O(1)
    Integer indice = nombreAIndice.get(nombreCiudad);
    if (indice != null) {
        return indice;
    }

    // Si no existe, agregar a ambas estructuras
    int nuevoIndice = nombresCiudades.size();
    nombresCiudades.add(nombreCiudad);      // O(1) amortizado
    nombreAIndice.put(nombreCiudad, nuevoIndice);  // O(1) promedio
    return nuevoIndice;
}
```

**Ventajas**:
- ✅ Búsqueda por nombre: O(N) → **O(1)**
- ✅ Acceso por índice: Sigue siendo **O(1)**
- ✅ Construcción del grafo: O(N×M) → **O(M)**
- ✅ Espacio extra: Solo O(N) para el HashMap

**Desventajas**:
- Memoria adicional: ~8 bytes × N (referencias en HashMap)
- Pequeño overhead de sincronización entre estructuras

### Comparación

| Operación | Antes | Después |
|-----------|-------|---------|
| `buscarIndice(nombre)` | O(N) | O(1) |
| `obtenerNombrePorIndice(i)` | O(1) | O(1) |
| Cargar grafo con M aristas | O(N×M) | O(M) |
| Espacio | O(N) | O(N) |

---

## 🎯 ¿Por Qué Elegimos Esta Implementación?

1. **Didáctica**: El código es claro y sigue fielmente el algoritmo de Karger original.

2. **Corrección sobre eficiencia**: Priorizamos que el algoritmo sea correcto (mantener aristas paralelas) sobre optimizaciones prematuras.

3. **Fase de construcción vs ejecución**: 
   - La carga del grafo es **una sola vez**: O(N×M) con estructura actual.
   - El algoritmo de Karger se ejecuta **miles de veces**: O(N³·M·log N).
   - La mejora de búsqueda ayuda en la carga inicial, pero el cuello de botella está en las iteraciones.

4. **Simplicidad de contracción**: Sin union-find podemos contraer nodos simplemente cambiando referencias de punteros, manteniendo todas las aristas (incluyendo paralelas).

---

## 🚀 Cómo Ejecutar

```bash
cd MinCut/src
javac *.java
java ProgramaPrincipal
```

Opciones:
1. Cargar grafo desde archivo (formato: `Ciudad1 Ciudad2 Ciudad3 ... -1`)
2. Configurar iteraciones (recomendado: n²·log n)
3. Buscar corte mínimo
4. Ver resultado con particiones, aristas del corte y análisis de complejidad

---

## 📚 Referencias

- **Karger, D. R.** (1993). "Global min-cuts in RNC, and other ramifications of a simple min-cut algorithm"
- **Análisis probabilístico**: La probabilidad de NO encontrar el corte mínimo en una iteración es ≤ (1 - 2/n²)
- Con k = n²·log n iteraciones: P(fallo) ≤ (1 - 2/n²)^k ≈ e^(-2·log n) = 1/n²

---

## 💡 Mejora Propuesta

Si deseas implementar la optimización del HashMap, consulta la sección de **Solución Creativa** arriba. El cambio es mínimo y mejora significativamente la fase de carga del grafo.
