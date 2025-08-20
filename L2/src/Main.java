public class Main {
    public static void main(String[] args) {
        Lista2<Integer> lista = new Lista2<>();
        Lista2<Integer> lista2 = new Lista2<>();
        Lista2<Integer> lista3 = new Lista2<>();


        /*//Insertar al principio
        System.out.println("\n--- Insertando al principio ---");
        lista.insertarPrincipio(4);
        lista.mostrarLista();
        lista.insertarPrincipio(3);
        lista.mostrarLista();
        lista.insertarPrincipio(2);
        lista.mostrarLista();
        lista.insertarPrincipio(1);
        lista.mostrarLista();

        //Insertar al final
        System.out.println("\n--- Insertando al final ---");
        lista.insertarFinal(5);
        lista.mostrarLista();
        lista.insertarFinal(6);
        if (!lista.insertarFinal(6)) {
            System.out.println("No se pudo insertar 6 - Lista llena");
        }
        lista.mostrarLista();
        
        // Mostrar vector completo
        System.out.println("\n--- Vector completo ---");
        lista.recorrerVector();
        
        // Pruebas de eliminación
        System.out.println("\n--- Eliminando del principio ---");
        lista.eliminarPrincipio();
        lista.mostrarLista();
        lista.recorrerVector();

        System.out.println("\n--- Eliminando del final ---");
        lista.eliminarFinal();
        lista.mostrarLista();
        lista.recorrerVector();

        
        System.out.println("\n--- Vector completo ---");
        lista.recorrerVector();

        lista.mostrarLista();
        lista.recorrerVector();
        
        // Vaciar lista
        System.out.println("\n--- Vaciando lista ---");
        while (!lista.estaVacia()) {
            lista.eliminarPrincipio();
            lista.mostrarLista();
        }*/

        System.out.println("\n--- Llenando la lista ---");
        for (int i = 1; i <= 4; i++) {
            boolean insertado = lista2.insertarFinal(i);
            if (!insertado) {
                System.out.println("No se pudo insertar " + i + " - Lista llena");
                break;
            }
        }

        System.out.println("\n--- Llenando la lista ---");
        for (int i = 0; i <= 2; i++) {
            int y = 0;
            boolean insertado = lista3.insertarFinal(y);
            if (!insertado) {
                System.out.println("No se pudo insertar " + i + " - Lista llena");
                break;
            }
        }

        lista2.mostrarLista();
        lista2.recorrerVector();
        lista3.mostrarLista();
        lista3.recorrerVector();

        Lista2<Integer> listaUnida = lista2.unir(lista3);
        System.out.println("\n--- Lista unida ---");
        listaUnida.mostrarLista();
        listaUnida.recorrerVector();
    }
}
