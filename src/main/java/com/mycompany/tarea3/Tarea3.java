package com.mycompany.tarea3;

import java.util.Scanner;

public class Tarea3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el grado maximo de  grado del árbol B:\n Teniendo en cuenta que el minimo es 1 ");
        int gradoMaximo = scanner.nextInt();
        int gradoMinimo = (int) Math.ceil(gradoMaximo / 2);
        System.out.println("m (valor minimo)= (grado/2) - 1 -> Ceil:");
        System.out.println("El grado del arbol es:" + gradoMaximo);
        System.out.println("La cantidad de claves permitidas es:" + (gradoMaximo - 1));
        System.out.println("Minimo de nodos:" + gradoMinimo);
        ArbolB arbolB = new ArbolB(gradoMinimo);
        String opcion = "";
        if (gradoMaximo == 1) {
            System.out.println("Debe haber mas de 1 Grado para el arbol");
        }
        while (!opcion.equalsIgnoreCase("d")) {
            opcion = scanner.nextLine();
            switch (opcion) {
                case "a":
                    System.out.println("Ingrese la clave a insertar:");
                    int claveInsertar = scanner.nextInt();
                    arbolB.insertar(claveInsertar);
                    arbolB.mostrarArbol();
                    break;
                case "b":
                    System.out.println("Ingrese la clave a eliminar:");
                    int claveEliminar = scanner.nextInt();
                    arbolB.eliminar(claveEliminar);
                    System.out.println("Clave eliminada.");
                    arbolB.mostrarArbol();
                    break;
                case "c":
                    System.out.println("Ingrese la clave a buscar:");
                    int claveBuscar = scanner.nextInt();
                    boolean encontrado = arbolB.buscar(claveBuscar);
                    if (encontrado) {
                        System.out.println("La clave " + claveBuscar + " está en el árbol.");
                    } else {
                        System.out.println("La clave " + claveBuscar + " no está en el árbol.");
                    }
                    break;
                default:
                    System.out.println("\nMenú:");
                    System.out.println("a. Insertar clave");
                    System.out.println("b. Eliminar clave");
                    System.out.println("c. Buscar clave");
                    System.out.println("d. Salir");
                    System.out.println("Ingrese su opción:");
            }
        }
    }
}
