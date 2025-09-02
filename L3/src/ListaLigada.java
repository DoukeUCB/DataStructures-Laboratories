public class ListaLigada {
    private Nodo primero;
    private Nodo ultimo;
    
    // Constructor
    public ListaLigada() {
        this.primero = null;
        this.ultimo = null;
    }
    
    // Eliminar material de todas las asignaturas del curso
    public boolean eliminarMaterial(String material) {
        if (primero == null) {
            return false;
        } else {
            Nodo punt = primero;
            boolean encontrado = false;
            while (punt != null) {
                if (punt.getListaMateriales().buscarYEliminar(material)) {
                    encontrado = true;
                }
                punt = punt.getSiguiente();
            }
            return encontrado;
        }
    }
    
    // Buscar asignatura por nombre
    public Nodo buscarAsignatura(String asignatura) {
        Nodo punt = primero;
        while (punt != null) {
            if (punt.getNombre().equals(asignatura)) {
                return punt;
            }
            punt = punt.getSiguiente();
        }
        return null;
    }
    
    // Insertar material en asignatura
    public void insertarAsignatura(String asignatura, String material) {
        if (primero == null) {
            // Primera asignatura del curso
            primero = new Nodo(null, asignatura, null);
            primero.getListaMateriales().insertarFinal(material);
            ultimo = primero;
        } else {
            // Buscar si la asignatura ya existe
            Nodo punt = buscarAsignatura(asignatura);
            if (punt != null) {
                // La asignatura existe, agregar material
                punt.getListaMateriales().insertarFinal(material);
            } else {
                // Crear nueva asignatura
                Nodo nuevo = new Nodo(ultimo, asignatura, null);
                if (ultimo != null) {
                    ultimo.setSiguiente(nuevo);
                }
                nuevo.setAnterior(ultimo);
                nuevo.getListaMateriales().insertarFinal(material);
                ultimo = nuevo;
            }
        }
    }
    
    // Mostrar todas las asignaturas y sus materiales
    public void mostrarAsignaturas() {
        Nodo punt = primero;
        while (punt != null) {
            System.out.println("    Asignatura: " + punt.getNombre());
            System.out.println("    Materiales:");
            punt.getListaMateriales().mostrarLista();
            System.out.println("    " + "-".repeat(25));
            punt = punt.getSiguiente();
        }
    }
    
    // Buscar materiales de una asignatura específica
    public boolean buscarMaterialesDeAsignatura(String asignatura) {
        Nodo punt = buscarAsignatura(asignatura);
        if (punt != null) {
            System.out.println("    Materiales de " + asignatura + ":");
            punt.getListaMateriales().mostrarLista();
            return true;
        } else {
            System.out.println("    Asignatura " + asignatura + " no encontrada");
            return false;
        }
    }
    
    // Verificar si la lista está vacía
    public boolean estaVacia() {
        return primero == null;
    }
}
