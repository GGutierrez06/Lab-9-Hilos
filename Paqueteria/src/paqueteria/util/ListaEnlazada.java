/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.util;

/**
 *
 * @author gabri
 */
public class ListaEnlazada<T> {    
    
    private Nodo<T> cabeza;
    private int tamanio;

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public boolean eliminar(T dato) {
        Nodo<T> anterior = null;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                if (anterior == null) {
                    cabeza = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                tamanio--;
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public T eliminarPrimero() {
        if (cabeza == null) {
            return null;
        }
        T dato = cabeza.getDato();
        cabeza = cabeza.getSiguiente();
        tamanio--;
        return dato;
    }

    public int buscar(T dato) {
        int posicion = 0;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return posicion;
            }
            posicion++;
            actual = actual.getSiguiente();
        }
        return -1;
    }

    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice inválido: " + indice);
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public int tamanio() {
        return tamanio;
    }

    public boolean estaVacia() {
        return tamanio == 0;
    }

    public void limpiar() {
        cabeza = null;
        tamanio = 0;
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> copia = new ListaEnlazada<>();
        Nodo<T> actual = cabeza;
        while (actual != null) {
            copia.agregar(actual.getDato());
            actual = actual.getSiguiente();
        }
        return copia;
    }

    @Override
    public String toString() {
        String texto = "[";
        Nodo<T> actual = cabeza;
        while (actual != null) {
            texto = texto + actual.getDato();
            if (actual.getSiguiente() != null) {
                texto = texto + ", ";
            }
            actual = actual.getSiguiente();
        }
        return texto + "]";
    }
}

