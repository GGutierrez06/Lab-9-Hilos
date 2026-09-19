/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

import paqueteria.util.ListaEnlazada;

/**
 *
 * @author gabri
 */

public class ControlSimulacion {

    public enum Estado { LISTA, EJECUTANDO, PAUSADA, DETENIDA }

    private volatile Estado estado = Estado.LISTA;
    private final ListaEnlazada<ZonaLogistica> zonas = new ListaEnlazada<>();

    void registrarZona(ZonaLogistica zona) {
        zonas.agregar(zona);
    }

    public Estado getEstado() {
        return estado;
    }

    public boolean estaPausada() {
        return estado == Estado.PAUSADA;
    }

    public boolean estaDetenida() {
        return estado == Estado.DETENIDA;
    }

    public void iniciar() {
        estado = Estado.EJECUTANDO;
    }

    public void pausar() {
        if (estado == Estado.EJECUTANDO) {
            estado = Estado.PAUSADA;
        }
    }

    public void reanudar() {
        if (estado == Estado.PAUSADA) {
            estado = Estado.EJECUTANDO;
            despertarTodos();
        }
    }

    public void detener() {
        if (estado != Estado.DETENIDA) {
            estado = Estado.DETENIDA;
            despertarTodos();
        }
    }

    private void despertarTodos() {
        synchronized (this) {
            notifyAll();
        }
        for (int i = 0; i < zonas.tamanio(); i++) {
            zonas.obtener(i).despertarTodos();
        }
    }

    public void verificarDetenida() throws InterruptedException {
        if (estado == Estado.DETENIDA) {
            throw new InterruptedException("Simulación detenida");
        }
    }

    public void puntoDePausa() throws InterruptedException {
        synchronized (this) {
            while (estado == Estado.PAUSADA) {
                wait();
            }
        }
        verificarDetenida();
    }

    public void dormir(long ms) throws InterruptedException {
        long restante = ms;
        while (restante > 0) {
            puntoDePausa();
            long tramo = Math.min(restante, 50);
            Thread.sleep(tramo);
            restante = restante - tramo;
        }
        puntoDePausa();
    }
}
