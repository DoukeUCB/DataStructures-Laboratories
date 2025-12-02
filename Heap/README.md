# Heap Tree (Max-Heap)

## 📖 ¿Qué es un Heap?

Un **Heap** es un árbol binario completo que cumple la **propiedad de heap**:
- **Max-Heap**: El padre siempre es MAYOR o igual a sus hijos
- **Min-Heap**: El padre siempre es MENOR o igual a sus hijos

Esta implementación es un **Max-Heap**.

## 🏗️ Estructura de la Implementación

### NODO.java
```java
class NODO<T extends Comparable<T>> {
    private T key;           // Valor del nodo
    private boolean presente; // Indica si el nodo está activo
}
```

### Heap.java
```java
class Heap<T extends Comparable<T>> {
    private NODO<T>[] vec;   // Array de nodos (tamaño 1024)
    private int ultimo;      // Índice del último elemento
}
```

**Característica especial**: El heap se almacena en un **array** (no con punteros), comenzando desde el índice 1.

## 📐 Representación en Array

Para un nodo en la posición `i`:
- **Padre**: `i / 2`
- **Hijo Izquierdo**: `2 * i`
- **Hijo Derecho**: `2 * i + 1`

### Ejemplo Visual:

```
Árbol:              Array (índice 1-based):
      50            [_, 50, 30, 40, 10, 20, 15, 25]
     /  \                1   2   3   4   5   6   7
   30    40
   / \   / \
  10 20 15 25

Relaciones:
- 50 (índice 1): padre de 30 (2) y 40 (3)
- 30 (índice 2): padre de 10 (4) y 20 (5)
- 40 (índice 3): padre de 15 (6) y 25 (7)
```

## 🔧 Operaciones Principales

### 1. **Insertar** - O(log n)
```
1. Agregar elemento al final (ultimo + 1)
2. Heap Up: Comparar con padre y subir si es mayor
3. Repetir hasta que esté en posición correcta
```

**Ejemplo**:
```
Insertar 60 en: [_, 50, 30, 40, 10, 20]

Paso 1: Agregar al final
[_, 50, 30, 40, 10, 20, 60]
                           ↑ último = 6

Paso 2: Heap Up
60 > 40 (padre en 6/2 = 3) → Intercambiar
[_, 50, 30, 60, 10, 20, 40]
            ↑

60 > 50 (padre en 3/2 = 1) → Intercambiar
[_, 60, 30, 50, 10, 20, 40]  ✅
    ↑
Raíz actualizada!
```

### 2. **Eliminar Raíz** - O(log n)
```
1. Guardar raíz (elemento máximo)
2. Mover último elemento a la raíz
3. Heap Down: Comparar con hijos y bajar al mayor
4. Repetir hasta que esté en posición correcta
```

**Ejemplo**:
```
Eliminar raíz de: [_, 60, 30, 50, 10, 20, 40]

Paso 1: Guardar raíz
raíz = 60

Paso 2: Mover último (40) a la raíz
[_, 40, 30, 50, 10, 20]
    ↑

Paso 3: Heap Down
40 < 50 (hijo mayor en posición 3) → Intercambiar
[_, 50, 30, 40, 10, 20]  ✅
    ↑
Nueva raíz = 50
```

### 3. **Heap Sort** - O(n log n)
```
1. Eliminar raíz repetidamente (extrae máximo)
2. Almacenar en array ordenado (de mayor a menor)
3. Resultado: array ordenado de menor a mayor
```

**Complejidad**:
- n elementos × O(log n) por eliminación = **O(n log n)**
- Espacio: O(n) para array auxiliar

## 📋 Menú del Programa

```
=== MENU ===
1. Insertar al HEAP          → Agrega un número
2. Eliminar RAÍZ             → Extrae el máximo
3. Mostrar ARRAY             → Visualiza heap + estructura
4. Leer archivo              → Inserta 10,000 números
5. Heap Sort                 → Ordena y restaura
0. Salir
```

## 🚀 Cómo Usar

### Compilar:
```bash
cd Heap/src
javac *.java
```

### Ejecutar:
```bash
java Main
```

### Ejemplo de Sesión:

```
=== MENU ===
1. Insertar al HEAP
Opción: 1
Número a insertar: 50
✅ Insertado: 50
📌 Raíz actual: 50

Opción: 1
Número a insertar: 30
✅ Insertado: 30
📌 Raíz actual: 50

Opción: 1
Número a insertar: 60
✅ Insertado: 60
📌 Raíz actual: 60  ← Nueva raíz!

Opción: 3
📊 Array del Heap (índice 1 a 3):
[ 60, 30, 50 ]

🌳 Estructura del Heap:
Nivel 0: 60 
Nivel 1: 30 50 

Opción: 2
✅ Raíz eliminada: 60
📌 Nueva raíz: 50
```

### Probar con archivo de 10,000 números:

```
Opción: 4
Ruta del archivo: numeros.txt

📂 Leyendo archivo: numeros.txt
⏳ Insertando números al heap...

✅ Inserción completada!
📊 Estadísticas:
   - Números insertados: 10000
   - Tamaño del heap: 10000
   - Raíz (máximo): 9998
   - Tiempo total: 45.23 ms
   - Promedio: 0.0045 ms/número
```

### Heap Sort:

```
Opción: 5
🔄 Ejecutando Heap Sort...
✅ Array ordenado (menor a mayor):
[ 1, 2, 3, 5, 7, ..., 9995, 9998 ]

🔄 Restaurando heap...
✅ Heap restaurado.
```

## 🎯 Propiedades del Max-Heap

### ✅ Invariantes:
1. **Árbol binario completo**: Todos los niveles llenos excepto el último
2. **Propiedad Max-Heap**: `padre ≥ hijos` para todo nodo
3. **Raíz = Máximo**: El elemento mayor siempre está en la raíz

### ✅ Complejidad Temporal:

| Operación | Complejidad | Explicación |
|-----------|-------------|-------------|
| **Insertar** | O(log n) | Heap Up en altura del árbol |
| **Eliminar Raíz** | O(log n) | Heap Down en altura del árbol |
| **Ver Raíz** | O(1) | Acceso directo a vec[1] |
| **Heap Sort** | O(n log n) | n eliminaciones × log n |
| **Construir Heap** | O(n log n) | n inserciones × log n |

### ✅ Complejidad Espacial:
- Array: O(n) donde n ≤ 1024
- Operaciones: O(1) (sin recursión adicional en esta implementación)

## 🔍 Verificación del Heap

### Cómo verificar si es un Max-Heap válido:

Para cada nodo en posición `i` (donde `1 ≤ i ≤ ultimo`):
```
vec[i] ≥ vec[2*i]      (si 2*i ≤ ultimo)
vec[i] ≥ vec[2*i + 1]  (si 2*i + 1 ≤ ultimo)
```

### Ejemplo de Heap VÁLIDO:
```
Array: [_, 100, 80, 90, 60, 70, 50, 85]

Verificación:
100 ≥ 80 ✅  100 ≥ 90 ✅
80 ≥ 60 ✅   80 ≥ 70 ✅
90 ≥ 50 ✅   90 ≥ 85 ✅
```

### Ejemplo de Heap INVÁLIDO:
```
Array: [_, 100, 80, 90, 60, 120, 50, 85]
                            ↑
                        120 > 80 ❌
                        Viola la propiedad!
```

## 📊 Aplicaciones del Heap

1. **Priority Queue**: Elementos con prioridad (mayor prioridad = raíz)
2. **Heap Sort**: Algoritmo de ordenamiento eficiente
3. **Algoritmo de Dijkstra**: Caminos más cortos en grafos
4. **Mediana en stream**: Mantener mediana de datos en tiempo real
5. **Top K elementos**: Encontrar los K elementos mayores/menores

## 🆚 Comparación con otros Árboles

| Característica | Heap | AVL | BST |
|----------------|------|-----|-----|
| **Estructura** | Array | Nodos + punteros | Nodos + punteros |
| **Ordenamiento** | Parcial (raíz max/min) | Total (InOrder) | Total (InOrder) |
| **Inserción** | O(log n) | O(log n) | O(n) worst |
| **Búsqueda** | O(n) | O(log n) | O(n) worst |
| **Extracción Max** | O(log n) | O(log n) | O(log n) |
| **Espacio** | Compacto (array) | Más punteros | Más punteros |

**Ventaja Heap**: Compacto en memoria, ideal para Priority Queue  
**Desventaja Heap**: Búsqueda lenta de elementos arbitrarios

## 🧮 Construcción Eficiente (Heapify)

Existe una forma más eficiente de construir un heap desde un array existente:

```java
// O(n) en lugar de O(n log n)
private void heapify() {
    for (int i = ultimo / 2; i >= 1; i--) {
        heapDown(i);
    }
}
```

Esto construye el heap en **O(n)** porque solo necesita hacer Heap Down desde los nodos internos.

## 📝 Notas Adicionales

### Array 1-based vs 0-based:
Esta implementación usa **1-based** (índice 1 es la raíz) porque:
- Fórmulas más simples: padre = i/2, hijos = 2*i y 2*i+1
- Evita casos especiales con índice 0

Si usáramos **0-based**:
- Padre: (i-1)/2
- Hijo Izq: 2*i + 1
- Hijo Der: 2*i + 2

### Min-Heap vs Max-Heap:
Para convertir a **Min-Heap**, solo cambia las comparaciones:
```java
// Max-Heap: vec[padre] > vec[hijo]
// Min-Heap: vec[padre] < vec[hijo]
```

## 👨‍💻 Autor

Implementación educativa para el curso de Estructura de Datos  
Universidad Católica Boliviana - 2025

---

¡Explora el poder de los heaps para ordenamiento y colas de prioridad! 🚀
