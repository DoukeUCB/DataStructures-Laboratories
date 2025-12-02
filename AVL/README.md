# AVL Tree - Árbol Binario Auto-Balanceado

## 📖 Índice
1. [¿Qué es un AVL?](#qué-es-un-avl)
2. [Estructura de la Implementación](#estructura-de-la-implementación)
3. [Factor de Balance](#factor-de-balance)
4. [Rotaciones](#rotaciones)
5. [Ejemplos Paso a Paso](#ejemplos-paso-a-paso)
6. [Complejidad Temporal](#complejidad-temporal)
7. [Cómo Usar](#cómo-usar)

---

## ¿Qué es un AVL?

Un **AVL Tree** es un árbol binario de búsqueda **auto-balanceado** inventado por Adelson-Velsky y Landis en 1962. 

### Características principales:
- ✅ **Auto-balanceo**: Se ajusta automáticamente después de inserciones/eliminaciones
- ✅ **Factor de Balance**: Cada nodo mantiene la diferencia de alturas entre sus subárboles
- ✅ **Garantía O(log n)**: Búsqueda, inserción y eliminación siempre en tiempo logarítmico
- ✅ **Regla AVL**: `|Factor de Balance| ≤ 1` para todos los nodos

### Ventaja sobre BST normal:
```
BST degenerado (O(n)):          AVL balanceado (O(log n)):
    1                                  4
     \                                / \
      2                              2   6
       \                            / \ / \
        3                          1  3 5  7
         \
          4
           \
            5
```

---

## Estructura de la Implementación

### 1. **NOD_AVL.java** - Nodo del AVL
```java
public class NOD_AVL<T extends Comparable<T>> {
    private T el;           // Elemento almacenado
    private NOD_AVL<T> L;   // Hijo izquierdo
    private NOD_AVL<T> R;   // Hijo derecho
    private byte fact;      // Factor de balance
}
```

**Campo clave**: `byte fact` almacena el factor de balance (rango: -2 a +2)

### 2. **AVL.java** - Árbol AVL
```java
public class AVL<T extends Comparable<T>> {
    private NOD_AVL<T> raiz;  // Raíz del árbol
    
    // Métodos principales
    public void insertar(T el)      // Inserción con auto-balanceo
    public boolean eliminar(T el)   // Eliminación con re-balanceo
    
    // Rotaciones
    private NOD_AVL<T> rotarIzq(NOD_AVL<T> nodo)
    private NOD_AVL<T> rotarDer(NOD_AVL<T> nodo)
    private NOD_AVL<T> rotarIzqDer(NOD_AVL<T> nodo)
    private NOD_AVL<T> rotarDerIzq(NOD_AVL<T> nodo)
}
```

---

## Factor de Balance

### Fórmula:
```
Factor de Balance (FB) = Altura(Subárbol Derecho) - Altura(Subárbol Izquierdo)
```

### Interpretación:
| Factor | Significado | Estado |
|--------|-------------|--------|
| **-2** | Desbalanceado a la izquierda | ❌ Requiere rotación |
| **-1** | Más alto a la izquierda | ✅ Balanceado |
| **0** | Alturas iguales | ✅ Perfectamente balanceado |
| **+1** | Más alto a la derecha | ✅ Balanceado |
| **+2** | Desbalanceado a la derecha | ❌ Requiere rotación |

### Ejemplo Visual:
```
       10[0]              10[-1]            10[+1]
       /   \              /                      \
     5[0]  15[0]        5[0]                    15[0]
     
  Balanceado        Balanceado            Balanceado
  (FB = 0)          (FB = -1)             (FB = +1)


       10[+2]  ❌ DESBALANCEADO (requiere rotación)
            \
            15[+1]
              \
              20[0]
```

---

## 🎯 Control de Alturas: Detalle del Factor de Balance

### ¿Cómo se Calcula la Altura?

La **altura** de un nodo es la distancia máxima desde ese nodo hasta cualquier hoja.

```java
private int altura(NOD_AVL<T> nodo) {
    if (nodo == null) return -1;  // Árbol vacío tiene altura -1
    return 1 + Math.max(altura(nodo.getL()), altura(nodo.getR()));
}
```

**Ejemplos de altura**:
```
    A          Altura de A = 0 (nodo hoja)
    
    B          Altura de B = 1
   /           Altura de A = 0
  A
  
    C          Altura de C = 2
   /           Altura de B = 1
  B            Altura de A = 0
 /
A

      D        Altura de D = 2
     / \       Altura de B = 1
    B   E      Altura de E = 0
   /           Altura de A = 0
  A
```

### Actualización del Factor de Balance

Después de cada inserción o eliminación, **actualizamos el factor** de cada nodo afectado:

```java
private void actualizarFact(NOD_AVL<T> nodo) {
    int alturaIzq = altura(nodo.getL());  // Altura subárbol izquierdo
    int alturaDer = altura(nodo.getR());  // Altura subárbol derecho
    nodo.setFact((byte)(alturaDer - alturaIzq));  // FB = Der - Izq
}
```

### 📊 Casos Detallados del Factor de Balance

#### **Caso 1: Factor = 0 (Perfectamente Balanceado)**
```
       50[0]           ← FB = 1 - 1 = 0
      /    \
    25[0]  75[0]       Altura izq = 1, Altura der = 1
    
✅ Ambos subárboles tienen la MISMA altura
✅ Es el estado más balanceado posible
✅ NO requiere ninguna acción
```

#### **Caso 2: Factor = -1 (Balanceado hacia Izquierda)**
```
       50[-1]          ← FB = 0 - 1 = -1
      /    \
    25[0]  75           Altura izq = 1, Altura der = 0
   /
  10[0]
  
✅ Subárbol izquierdo es 1 nivel MÁS alto que el derecho
✅ Aún está balanceado (diferencia = 1)
✅ NO requiere rotación
```

#### **Caso 3: Factor = +1 (Balanceado hacia Derecha)**
```
       50[+1]          ← FB = 1 - 0 = +1
      /    \
    25      75[0]      Altura izq = 0, Altura der = 1
             \
             90[0]
             
✅ Subárbol derecho es 1 nivel MÁS alto que el izquierdo
✅ Aún está balanceado (diferencia = 1)
✅ NO requiere rotación
```

#### **Caso 4: Factor = -2 (DESBALANCEADO a la Izquierda)**
```
       50[-2]  ❌      ← FB = 0 - 2 = -2
      /
    25[-1]             Altura izq = 2, Altura der = 0
   /
  10[0]
  
❌ Subárbol izquierdo es 2 niveles MÁS alto
❌ VIOLA la propiedad AVL (|FB| ≤ 1)
🔄 REQUIERE rotación a la DERECHA en nodo 50
```

**Después de rotar**:
```
     25[0]  ✅         ← FB = 1 - 1 = 0
    /    \
  10[0]  50[0]        Altura izq = 1, Altura der = 1

✅ Árbol balanceado nuevamente
```

#### **Caso 5: Factor = +2 (DESBALANCEADO a la Derecha)**
```
       50[+2]  ❌     ← FB = 2 - 0 = +2
            \
            75[+1]    Altura izq = 0, Altura der = 2
              \
              90[0]
              
❌ Subárbol derecho es 2 niveles MÁS alto
❌ VIOLA la propiedad AVL (|FB| ≤ 1)
🔄 REQUIERE rotación a la IZQUIERDA en nodo 50
```

**Después de rotar**:
```
       75[0]  ✅      ← FB = 1 - 1 = 0
      /    \
    50[0]  90[0]     Altura izq = 1, Altura der = 1

✅ Árbol balanceado nuevamente
```

### 🔍 Ejemplo Completo: Evolución del Factor de Balance

Insertemos **10, 20, 30, 25** y observemos los factores:

#### **Paso 1: Insertar 10**
```
   10[0]              FB = altura(null) - altura(null) = -1 - (-1) = 0
   
✅ Balanceado
```

#### **Paso 2: Insertar 20**
```
   10[+1]             FB = altura(20) - altura(null) = 0 - (-1) = +1
    \
    20[0]             FB = altura(null) - altura(null) = 0
    
✅ Balanceado (FB = +1 es aceptable)
```

#### **Paso 3: Insertar 30** (¡Aquí se desbalancea!)
```
ANTES de actualizar factores:
   10
    \
    20
      \
      30
      
ACTUALIZANDO factores de abajo hacia arriba:
- 30: FB = -1 - (-1) = 0  ✅
- 20: FB = altura(30) - altura(null) = 0 - (-1) = +1  ✅
- 10: FB = altura(20) - altura(null) = 1 - (-1) = +2  ❌ DESBALANCEADO!

   10[+2]  ❌         Altura izq = -1 (null), Altura der = 1
    \
    20[+1]            Altura izq = -1 (null), Altura der = 0
      \
      30[0]
```

**Detectamos**: FB = +2 en nodo 10, y FB = +1 en hijo derecho (20)  
**Solución**: Rotación simple IZQUIERDA en nodo 10

```
DESPUÉS de rotar:
     20[0]  ✅        FB = altura(30) - altura(10) = 0 - 0 = 0
    /    \
  10[0]  30[0]       Todos los factores actualizados = 0

✅ Árbol perfectamente balanceado
```

#### **Paso 4: Insertar 25** (Rotación doble)
```
ANTES:
     20[+1]           FB = altura(30) - altura(10) = 1 - 0 = +1  ✅
    /    \
  10[0]  30[-1]       FB = altura(25) - altura(null) = 0 - (-1) = -1  ✅
         /
       25[0]

✅ Todos los factores ≤ 1, árbol balanceado
```

Pero si insertáramos 27:
```
     20[+2]  ❌       FB = altura(30) - altura(10) = 2 - 0 = +2
    /    \
  10[0]  30[-1]       FB = altura(27) - altura(null) = 1 - (-1) = -1
         /
       25[+1]         FB = altura(27) - altura(null) = 0 - (-1) = +1
         \
         27[0]

❌ FB = +2 en nodo 20
❌ FB = -1 en hijo derecho (30) ← signo opuesto!
🔄 Rotación DOBLE: Derecha en 30, luego Izquierda en 20
```

### 📐 Regla de Decisión para Rotaciones

Cuando detectamos **|FB| = 2**, miramos el factor del **hijo** en el lado pesado:

| FB Padre | FB Hijo | Rotación Necesaria | Caso |
|----------|---------|-------------------|------|
| **-2** | **-1** o **0** | Simple DERECHA | Izquierda-Izquierda |
| **-2** | **+1** | Doble IZQUIERDA-DERECHA | Izquierda-Derecha |
| **+2** | **+1** o **0** | Simple IZQUIERDA | Derecha-Derecha |
| **+2** | **-1** | Doble DERECHA-IZQUIERDA | Derecha-Izquierda |

### 🧮 Implementación del Balanceo

```java
private NOD_AVL<T> balancear(NOD_AVL<T> nodo) {
    actualizarFact(nodo);  // Calcula FB = alturaDer - alturaIzq
    
    // Caso 1: Desbalance a la IZQUIERDA (FB = -2)
    if (nodo.getFact() == -2) {
        if (nodo.getL().getFact() <= 0) {
            // Izquierda-Izquierda: Rotación simple DERECHA
            return rotarDer(nodo);
        } else {
            // Izquierda-Derecha: Rotación doble
            return rotarIzqDer(nodo);
        }
    }
    
    // Caso 2: Desbalance a la DERECHA (FB = +2)
    if (nodo.getFact() == 2) {
        if (nodo.getR().getFact() >= 0) {
            // Derecha-Derecha: Rotación simple IZQUIERDA
            return rotarIzq(nodo);
        } else {
            // Derecha-Izquierda: Rotación doble
            return rotarDerIzq(nodo);
        }
    }
    
    // Caso 3: Balanceado (FB ∈ {-1, 0, +1})
    return nodo;  // No requiere rotación
}
```

### ✅ Resumen: Control de Alturas

1. **Altura se calcula recursivamente**: `altura = 1 + max(alturaIzq, alturaDer)`
2. **Factor de Balance**: `FB = alturaDer - alturaIzq`
3. **Rango válido**: `FB ∈ {-1, 0, +1}` ✅
4. **Rango inválido**: `FB ∈ {-2, +2}` ❌ → Requiere rotación
5. **Actualización**: Después de CADA inserción/eliminación
6. **Propagación**: Los cambios de altura se propagan hacia arriba (desde hoja a raíz)
7. **Eficiencia**: Actualización en O(log n), rotación en O(1)

**Propiedad Clave**: Un AVL **NUNCA** permite que `|FB| > 1`, garantizando altura O(log n) 🌳

---

## Rotaciones

### 1. **Rotación Simple a la Izquierda** (Left Rotation)
**Cuándo**: FB = +2 y FB(hijo derecho) = +1

```
ANTES:                  DESPUÉS:
   A[+2]                   B[0]
    \                     / \
     B[+1]      →→→      A   C
      \
       C

Código:
B = A.R
A.R = B.L
B.L = A
return B
```

### 2. **Rotación Simple a la Derecha** (Right Rotation)
**Cuándo**: FB = -2 y FB(hijo izquierdo) = -1

```
ANTES:                  DESPUÉS:
     C[-2]                 B[0]
    /                     / \
   B[-1]      →→→        A   C
  /
 A

Código:
B = C.L
C.L = B.R
B.R = C
return B
```

### 3. **Rotación Doble Izquierda-Derecha** (Left-Right Rotation)
**Cuándo**: FB = -2 y FB(hijo izquierdo) = +1

```
ANTES:           PASO 1:          PASO 2:
   C[-2]           C[-2]             B[0]
  /               /                 / \
 A[+1]    →→→    B[-1]     →→→     A   C
  \             /
   B           A

Primero: Rotación izquierda en A
Después: Rotación derecha en C
```

### 4. **Rotación Doble Derecha-Izquierda** (Right-Left Rotation)
**Cuándo**: FB = +2 y FB(hijo derecho) = -1

```
ANTES:           PASO 1:          PASO 2:
 A[+2]             A[+2]             B[0]
  \                 \               / \
   C[-1]    →→→     B[+1]   →→→   A   C
  /                  \
 B                    C

Primero: Rotación derecha en C
Después: Rotación izquierda en A
```

---

## Ejemplos Paso a Paso

### 📌 Ejemplo 1: Inserción Secuencial (Degenera sin AVL)

**Secuencia**: Insertar `10, 20, 30`

#### Paso 1: Insertar 10
```
   10[0]
   
✅ Balanceado (FB = 0)
```

#### Paso 2: Insertar 20
```
   10[+1]
    \
    20[0]
    
✅ Balanceado (FB = +1)
```

#### Paso 3: Insertar 30
```
ANTES (desbalanceado):       DESPUÉS (rotación izq):
   10[+2]  ❌                     20[0]  ✅
    \                            / \
    20[+1]         →→→         10   30
      \
      30[0]

🔄 Rotación simple IZQUIERDA en nodo 10
```

---

### 📌 Ejemplo 2: Inserción con Rotación Doble

**Secuencia**: Insertar `10, 30, 20`

#### Paso 1 y 2: Insertar 10, 30
```
   10[+1]
    \
    30[0]
    
✅ Balanceado
```

#### Paso 3: Insertar 20
```
ANTES (desbalanceado):       PASO 1 (rot der en 30):    PASO 2 (rot izq en 10):
   10[+2]  ❌                   10[+2]                      20[0]  ✅
    \                            \                         / \
    30[-1]         →→→           20[+1]       →→→        10   30
    /                             \
   20[0]                          30[0]

🔄 Rotación doble DERECHA-IZQUIERDA
```

---

### 📌 Ejemplo 3: Construcción Completa

**Secuencia**: Insertar `50, 25, 75, 10, 30, 60, 80, 5, 15`

#### Inserción Paso a Paso:

```
1. Insertar 50:
   50[0]

2. Insertar 25:
     50[-1]
    /
   25[0]

3. Insertar 75:
     50[0]
    /   \
   25    75

4. Insertar 10:
     50[-1]
    /   \
   25[-1] 75
  /
 10

5. Insertar 30:
     50[0]
    /   \
   25[0] 75
  / \
 10  30

6. Insertar 60:
     50[+1]
    /   \
   25[0] 75[-1]
  / \   /
 10  30 60

7. Insertar 80:
     50[0]
    /   \
   25[0] 75[0]
  / \   / \
 10  30 60 80

8. Insertar 5:
       50[-1]
      /     \
   25[-1]   75[0]
   / \      / \
  10[-1] 30 60 80
  /
 5

9. Insertar 15 (requiere rotación):
ANTES:                    DESPUÉS (rot izq-der en 25):
       50[-2]  ❌                50[-1]  ✅
      /     \                   /     \
   25[+1]   75               15[0]    75
   / \      / \              / \      / \
  10[+1] 30 60 80           10  25   60 80
    \                       /     \
    15                     5      30

🔄 Rotación IZQUIERDA-DERECHA en subárbol de 25
```

**Árbol Final Balanceado**:
```
           50[-1]
         /        \
      15[0]        75[0]
     /    \        /   \
   10[-1] 25[+1] 60   80
   /         \
  5          30
```

---

### 📌 Ejemplo 4: Eliminación con Re-balanceo

**Árbol Inicial**:
```
     20[0]
    /   \
  10[0] 30[-1]
       /   \
      25   35
```

#### Eliminar 10:
```
ANTES:                    DESPUÉS:
     20[+1]                   30[0]
        \                    /   \
        30[-1]   →→→        20    35
       /   \               /
      25   35             25

✅ Aún balanceado (FB = +1)
```

#### Ahora eliminar 25:
```
ANTES:                    DESPUÉS (rot izq en 20):
     30[-2]  ❌                30[0]  ✅
    /   \                     /   \
  20[-1] 35       →→→       25    35
  /
 25

🔄 Rotación simple DERECHA para re-balancear
```

---

## Complejidad Temporal

| Operación | Complejidad | Explicación |
|-----------|-------------|-------------|
| **Búsqueda** | O(log n) | Altura garantizada ≤ 1.44 log n |
| **Inserción** | O(log n) | Inserción + actualizar FB + max 2 rotaciones |
| **Eliminación** | O(log n) | Eliminación + re-balanceo en camino a raíz |
| **Rotación** | O(1) | Solo reasigna 3-4 punteros |

### Comparación con otros árboles:
| Estructura | Búsqueda | Inserción | Eliminación | Balanceado |
|------------|----------|-----------|-------------|------------|
| **BST** | O(n) worst | O(n) worst | O(n) worst | ❌ No |
| **AVL** | O(log n) | O(log n) | O(log n) | ✅ Estricto |
| **Red-Black** | O(log n) | O(log n) | O(log n) | ✅ Relajado |

**Ventaja AVL**: Búsquedas más rápidas (más balanceado)  
**Desventaja AVL**: Más rotaciones en inserción/eliminación que Red-Black

---

## Cómo Usar

### Compilar:
```bash
cd AVL/src
javac *.java
```

### Ejecutar:
```bash
java Main
```

### Ejemplo de Sesión:
```
=== MENU ===
1. Insertar (con rotación)
2. Eliminar elemento
3. Contar nodos
4. Calcular altura
5. Mostrar InOrder
6. Buscar menor
7. Buscar mayor
8. Eliminar TODO
9. Insertar mediante archivo
0. Salir
Opcion: 1
Elemento: hola
Insertando: hola
Insertado correctamente.

Opcion: 1
Elemento: mundo
Insertando: mundo
Insertado correctamente.

Opcion: 5
InOrder [elemento[factor]]:
hola[+1] mundo[0]

Opcion: 4
Altura: 1
```

### Inserción desde Archivo:
La opción **9** permite insertar palabras desde un archivo de texto:
```
Opcion: 9
Ruta del archivo: soledad.txt

📂 Leyendo archivo: soledad.txt
⏳ Insertando palabras en el AVL...

✅ Inserción completada!
📊 Estadísticas:
   - Palabras insertadas: 85423
   - Nodos únicos en AVL: 9876
   - Altura del árbol: 18
   - Tiempo total: 245.67 ms
   - Promedio: 0.0029 ms/palabra
```

**Características**:
- ✅ **Medición de tiempo**: Usa `System.nanoTime()` para precisión en nanosegundos
- ✅ **Codificación**: Soporta ISO-8859-1 para caracteres españoles (á, é, í, ó, ú, ñ)
- ✅ **Normalización**: Convierte a minúsculas y elimina puntuación
- ✅ **Estadísticas**: Muestra palabras totales, nodos únicos, altura y tiempos
- ✅ **Archivo de prueba**: `soledad.txt` - "Cien años de soledad" (Gabriel García Márquez)

### Prueba de Rotaciones:
Para ver las rotaciones en acción, inserta esta secuencia:
```
10 → 20 → 30  (Verás: "Rotando a la IZQUIERDA nodo 10")
```

O esta:
```
30 → 10 → 20  (Verás: "Rotación IZQUIERDA-DERECHA en nodo 30")
```

---

## 🎯 Casos de Uso Ideales

✅ **Úsalo cuando**:
- Necesitas búsquedas ultra-rápidas
- Los datos se consultan más que se modifican
- Requieres garantías estrictas de O(log n)
- Trabajas con diccionarios, índices, bases de datos

❌ **Evítalo cuando**:
- Inserciones/eliminaciones muy frecuentes
- Espacio extra por factor de balance es crítico
- Red-Black Tree sería suficiente (menos rotaciones)

---

## 🔍 Verificación de Correctitud

### Propiedades que debe cumplir un AVL válido:
1. ✅ Es un BST válido (izq < nodo < der)
2. ✅ `|FB| ≤ 1` para TODOS los nodos
3. ✅ FB = altura(der) - altura(izq)
4. ✅ Altura ≤ 1.44 log₂(n)

### Debug en el código:
- `mostrarInOrder()` muestra `[fact]` para verificar factores
- Los métodos de rotación imprimen qué rotación se aplica
- Puedes validar manualmente con la opción 5 (InOrder)

---

## 📚 Referencias

- **Paper Original**: G. M. Adelson-Velsky and E. M. Landis (1962)
- **Libro**: "Introduction to Algorithms" (CLRS), Chapter 13
- **Visualización**: [VisuAlgo - AVL Tree](https://visualgo.net/en/bst)

---

## 👨‍💻 Autor

Implementación educativa para el curso de Estructura de Datos  
Universidad Católica Boliviana - 2025

---

## 📝 Notas Adicionales

### Diferencia con Red-Black Tree:
- **AVL**: FB ∈ {-1, 0, +1} → más estricto → menos altura → búsquedas MÁS rápidas
- **RBT**: Propiedades de color → más relajado → menos rotaciones → inserciones MÁS rápidas

### Factor de Balance en `byte`:
Usamos `byte` (1 byte) en lugar de `int` (4 bytes) porque:
- Rango suficiente: -128 a +127 (solo necesitamos -2 a +2)
- Ahorra memoria en árboles grandes
- Mismo rendimiento en operaciones aritméticas

### Altura vs. Número de Nodos:
```
Altura 0: 1 nodo máximo
Altura 1: 3 nodos máximo
Altura 2: 7 nodos máximo
Altura h: 2^(h+1) - 1 nodos máximo

Fórmula inversa: altura ≤ log₂(n+1) - 1
```

¡Explora, experimenta y comprende el poder del auto-balanceo! 🌳
