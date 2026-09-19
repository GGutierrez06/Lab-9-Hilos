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
    private int length;
    
    public ListaEnlazada(){
        this.cabeza = null;
        length = 0;
    }
    
    public Nodo<T> getCabeza(){
        return cabeza;
    }
    
    public void insertarInicio(T dato){
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
        length++;
    }
    
    public void insertarFinal(T dato){
        Nodo<T> nuevo = new Nodo<>(dato);
        if(cabeza == null){
            cabeza = nuevo;
        } else {
            Nodo<T> puntero = cabeza;
            while(puntero.getSiguiente() != null){
                puntero = puntero.getSiguiente();
            }
            puntero.setSiguiente(nuevo);
        }
        length++;
    }
    
    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)){
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    public int buscar(T dato) {
        Nodo<T> actual = cabeza;
        int pos = 0;
        while (actual != null) {
            if (actual.getDato().equals(dato)){
                return pos;
            }
            actual = actual.getSiguiente();
            pos +=1;
        }
        return -1;
    }
    
    public boolean eliminar(T dato) {
        if (cabeza == null){
            return false;
        }
 
        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            length--;
            return true;
        }
 
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                length--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    public T obtenerEn(int indice) {
        if (indice < 0 || indice >= length) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }
 
    public int length() {
        return length;
    }
 
    public boolean estaVacia() {
        return length == 0;
    }
}
