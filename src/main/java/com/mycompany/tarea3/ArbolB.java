/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea3;

/**
 *
 * @author victo
 */
import java.util.*;

public class ArbolB {

    private NodoArbol raiz;
    private int grado;

    public ArbolB(int grado) {
        this.grado = grado;
        raiz = new NodoArbol(grado, true);
    }


    public void insertar(int clave) {
        NodoArbol r = raiz;
        if (r.cantidadClaves == ((2 * grado) - 1)) {
            NodoArbol nuevaRaiz = new NodoArbol(grado, false);
            raiz = nuevaRaiz;
            nuevaRaiz.cantidadClaves = 0;
            nuevaRaiz.hijos[0] = r;
            dividirHijo(nuevaRaiz, 0, r);
            raiz = nuevaRaiz;
            insertarNoLleno(nuevaRaiz,clave);
        } else {
            insertarNoLleno(raiz, clave);
        }
    }

    private void insertarNoLleno(NodoArbol nodo, int clave) {
        if (nodo.hoja) {
            int i = nodo.cantidadClaves;
            while (i >= 1 && clave < nodo.claves[i-1]) {
                nodo.claves[i ] = nodo.claves[i-1];
                i--;
            }
            nodo.claves[i] = clave;
            nodo.cantidadClaves++;
        } else {
            int j=0;
            while (j < nodo.cantidadClaves && clave > nodo.claves[j]) {
                j++;
            }
            if (nodo.hijos[j].cantidadClaves == ((2 * grado) - 1)) {
                dividirHijo(nodo, j,nodo.hijos[j]);
                if (clave > nodo.claves[j]) {
                    j++;
                }
            }
            insertarNoLleno(nodo.hijos[j], clave);
        }
    }

private void dividirHijo(NodoArbol x, int i, NodoArbol y) {
    NodoArbol z = new NodoArbol(grado,false);
    z.hoja = y.hoja;
    z.cantidadClaves = (grado - 1);

    for (int j = 0; j < (grado - 1); j++) {
        z.claves[j] = y.claves[(j + grado)];
    }

    if (!y.hoja) {
        for (int k = 0; k < grado; k++) {
            z.hijos[k] = y.hijos[(k + grado)];
        }
    }

    y.cantidadClaves = (grado - 1);

    for (int j = x.cantidadClaves; j > i; j--) {
        x.hijos[(j + 1)] = x.hijos[j];
    }

    x.hijos[(i + 1)] = z;

    for (int j = x.cantidadClaves; j > i; j--) {
        x.hijos[(j + 1)] = x.hijos[j];
    }

    x.claves[i] = y.claves[(grado - 1)];
    x.cantidadClaves++;
}


    public boolean buscar(int clave) {
        return buscar(raiz, clave);
    }

    private boolean buscar(NodoArbol nodo, int clave) {
        int i = 0;
        while (i < nodo.cantidadClaves && clave > nodo.claves[i]) {
            i++;
        }
        if (i < nodo.cantidadClaves && clave == nodo.claves[i]) {
            return true;
        }
        if (nodo.hoja) {
            return false;
        }
        return buscar(nodo.hijos[i], clave);
    }

    public void eliminar(int clave) {
        eliminar(raiz, clave);
    }

    private void eliminar(NodoArbol nodo, int clave) {
        int indice = encontrarIndiceClave(nodo, clave);
        if (indice < nodo.cantidadClaves && nodo.claves[indice] == clave) {
            if (nodo.hoja) {
                eliminarDeHoja(nodo, indice);
            } else {
                eliminarDeNoHoja(nodo, indice);
            }
        } else {
            if (nodo.hoja) {
                System.out.println("La clave " + clave + " no se encuentra en el árbol.");
                return;
            }
            boolean flag = (indice == nodo.cantidadClaves);
            if (nodo.hijos[indice].cantidadClaves < grado) {
                llenar(nodo, indice);
            }
            if (flag && indice > nodo.cantidadClaves) {
                eliminar(nodo.hijos[indice - 1], clave);
            } else {
                eliminar(nodo.hijos[indice], clave);
            }
        }
    }

    private int encontrarIndiceClave(NodoArbol nodo, int clave) {
        int indice = 0;
        while (indice < nodo.cantidadClaves && nodo.claves[indice] < clave) {
            indice++;
        }
        return indice;
    }

    private void eliminarDeHoja(NodoArbol nodo, int indice) {
        for (int i = indice + 1; i < nodo.cantidadClaves; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }
        nodo.cantidadClaves--;
    }

    private void eliminarDeNoHoja(NodoArbol nodo, int indice) {
        int clave = nodo.claves[indice];
        if (nodo.hijos[indice].cantidadClaves >= grado) {
            int predecesor = obtenerPredecesor(nodo, indice);
            nodo.claves[indice] = predecesor;
            eliminar(nodo.hijos[indice], predecesor);
        } else if (nodo.hijos[indice + 1].cantidadClaves >= grado) {
            int sucesor = obtenerSucesor(nodo, indice);
            nodo.claves[indice] = sucesor;
            eliminar(nodo.hijos[indice + 1], sucesor);
        } else {
            fusionar(nodo, indice);
            eliminar(nodo.hijos[indice], clave);
        }
    }

    private int obtenerPredecesor(NodoArbol nodo, int indice) {
        NodoArbol actual = nodo.hijos[indice];
        while (!actual.hoja) {
            actual = actual.hijos[actual.cantidadClaves];
        }
        return actual.claves[actual.cantidadClaves - 1];
    }

    private int obtenerSucesor(NodoArbol nodo, int indice) {
        NodoArbol actual = nodo.hijos[indice + 1];
        while (!actual.hoja) {
            actual = actual.hijos[0];
        }
        return actual.claves[0];
    }

    private void llenar(NodoArbol nodo, int indice) {
        if (indice != 0 && nodo.hijos[indice - 1].cantidadClaves >= grado) {
            pedirPrestadoDelAnterior(nodo, indice);
        } else if (indice != nodo.cantidadClaves && nodo.hijos[indice + 1].cantidadClaves >= grado) {
            pedirPrestadoDelSiguiente(nodo, indice);
        } else {
            if (indice != nodo.cantidadClaves) {
                fusionar(nodo, indice);
            } else {
                fusionar(nodo, indice - 1);
            }
        }
    }

    private void pedirPrestadoDelAnterior(NodoArbol nodo, int indice) {
        NodoArbol hijo = nodo.hijos[indice];
        NodoArbol hermano = nodo.hijos[indice - 1];
        for (int i = hijo.cantidadClaves - 1; i >= 0; i--) {
            hijo.claves[i + 1] = hijo.claves[i];
        }
        if (!hijo.hoja) {
            for (int i = hijo.cantidadClaves; i >= 0; i--) {
                hijo.hijos[i + 1] = hijo.hijos[i];
            }
        }
        hijo.claves[0] = nodo.claves[indice - 1];
        if (!nodo.hoja) {
            hijo.hijos[0] = hermano.hijos[hermano.cantidadClaves];
        }
        nodo.claves[indice - 1] = hermano.claves[hermano.cantidadClaves - 1];
        hijo.cantidadClaves++;
        hermano.cantidadClaves--;
    }

    private void pedirPrestadoDelSiguiente(NodoArbol nodo, int indice) {
        NodoArbol hijo = nodo.hijos[indice];
        NodoArbol hermano = nodo.hijos[indice + 1];
        hijo.claves[hijo.cantidadClaves] = nodo.claves[indice];
        if (!hijo.hoja) {
            hijo.hijos[hijo.cantidadClaves + 1] = hermano.hijos[0];
        }
        nodo.claves[indice] = hermano.claves[0];
        for (int i = 1; i < hermano.cantidadClaves; i++) {
            hermano.claves[i - 1] = hermano.claves[i];
        }
        if (!hermano.hoja) {
            for (int i = 1; i <= hermano.cantidadClaves; i++) {
                hermano.hijos[i - 1] = hermano.hijos[i];
            }
        }
        hijo.cantidadClaves++;
        hermano.cantidadClaves--;
    }

    private void fusionar(NodoArbol nodo, int indice) {
        NodoArbol hijo = nodo.hijos[indice];
        NodoArbol hermano = nodo.hijos[indice + 1];
        hijo.claves[grado - 1] = nodo.claves[indice];
        for (int i = 0; i < hermano.cantidadClaves; i++) {
            hijo.claves[i + grado] = hermano.claves[i];
        }
        if (!hijo.hoja) {
            for (int i = 0; i <= hermano.cantidadClaves; i++) {
                hijo.hijos[i + grado] = hermano.hijos[i];
            }
        }
        for (int i = indice + 1; i < nodo.cantidadClaves; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }
        for (int i = indice + 2; i <= nodo.cantidadClaves; i++) {
            nodo.hijos[i - 1] = nodo.hijos[i];
        }
        hijo.cantidadClaves += hermano.cantidadClaves + 1;
        nodo.cantidadClaves--;
    }

    public void mostrarArbol() {
        imprimir(raiz);
    }

    private void imprimir(NodoArbol n) {
        n.imprimir();

        //Si no es hoja
        if (!n.hoja) {
            //recorre los nodos para saber si tiene hijos
            for (int j = 0; j <= n.cantidadClaves; j++) {
                if (n.hijos[j] != null) {
                    System.out.println();
                    imprimir(n.hijos[j]);
                }
            }
        }
    }
}
