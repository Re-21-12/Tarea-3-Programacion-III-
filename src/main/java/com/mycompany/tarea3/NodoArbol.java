/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea3;

import java.util.*;

/**
 *
 * @author victo
 */

    //grado es = numero de claves + 1, ees el numero maximo de hijos que puede tener un nodo
    //clave es = numero de grado - 1, es el contenido
    // m = grado
    // Minimo de grados = (m/2) - 1 aproximamos usando .ceil
    //la raiz no cumple con el minimo de grados si se puede
    //private int Grado; -> Seria mejor tenerlo en el arbol
    
public class NodoArbol {
    int[] claves;
    int grado;
    NodoArbol[] hijos;
    boolean hoja;
    int cantidadClaves;

    public NodoArbol(int grado, boolean hoja) {
        this.grado = grado;
        this.hoja = hoja;
        claves = new int[2 * grado - 1];
        hijos = new NodoArbol[2 * grado];
        cantidadClaves = 0;
    }
        public void imprimir() {
        System.out.print("||");
        for (int i = 0; i < cantidadClaves; i++) {
            if (i < cantidadClaves - 1) {
                System.out.print(claves[i] + " || ");
            } else {
                System.out.print(claves[i]);
            }
        }
        System.out.print("||");
    }

    public int buscar(int k) {
        for (int i = 0; i < cantidadClaves; i++) {
            if (claves[i] == k) {
                return i;
            }
        }
        return -1;
    }
}
