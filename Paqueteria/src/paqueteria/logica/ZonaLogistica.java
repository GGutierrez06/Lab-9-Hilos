/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

import paqueteria.util.ListaEnlazada;
import paqueteria.base.Paquete;

public class ZonaLogistica {

    private final String nombre;
    private final int capacidad;
    private final ControlSimulacion control;
    private final ListaEnlazada<Paquete> paquetes = new ListaEnlazada<>();

    public ZonaLogistica(String nombre, int capacidad, ControlSimulacion control) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.control = control;
        control.registrarZona(this);
    }

    public synchronized void agregar(Paquete paquete) throws InterruptedException {
        while (paquetes.tamanio() >= capacidad || control.estaPausada()) {
            control.verificarDetenida();
            wait();
        }
        control.verificarDetenida();
        paquetes.agregar(paquete);
        notifyAll();
    }

    public synchronized Paquete tomar() throws InterruptedException {
        return tomarDeRuta(0);
    }

    public synchronized Paquete tomarDeRuta(int ruta) throws InterruptedException {
        int posicion = posicionMejor(ruta);
        while (posicion == -1 || control.estaPausada()) {
            control.verificarDetenida();
            wait();
            posicion = posicionMejor(ruta);
        }
        control.verificarDetenida();

        Paquete elegido = paquetes.obtener(posicion);
        paquetes.eliminar(elegido);
        notifyAll(); 
        return elegido;
    }

    private int posicionMejor(int ruta) {
        int mejor = -1;
        for (int i = 0; i < paquetes.tamanio(); i++) {
            Paquete p = paquetes.obtener(i);
            if (ruta == 0 || p.getRuta() == ruta) {
                if (mejor == -1
                        || p.getPrioridad().compareTo(paquetes.obtener(mejor).getPrioridad()) < 0) {
                    mejor = i;
                }
            }
        }
        return mejor;
    }

    public synchronized boolean quitar(Paquete paquete) {
        boolean quitado = paquetes.eliminar(paquete);
        if (quitado) {
            notifyAll();
        }
        return quitado;
    }

    public synchronized void despertarTodos() {
        notifyAll();
    }

    public synchronized int tamanio() {
        return paquetes.tamanio();
    }

    public synchronized boolean estaLlena() {
        return paquetes.tamanio() >= capacidad;
    }

    public synchronized ListaEnlazada<Paquete> instantanea() {
        return paquetes.copiar();
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }
}
