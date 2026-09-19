/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

/**
 *
 * @author gabri
 */

public abstract class HiloBase extends Thread {

    protected final CentroLogistico centro;

    protected HiloBase(String nombre, CentroLogistico centro) {
        super(nombre);
        this.centro = centro;
        setDaemon(true); 
    }

    protected abstract void ciclo() throws InterruptedException;

    @Override
    public void run() {
        try {
            while (true) {
                centro.control.puntoDePausa();
                ciclo();
            }
        } catch (InterruptedException e) {
        } catch (RuntimeException e) {
            centro.registro.registrar("ERROR en " + getName() + ": " + e.getMessage());
        }
    }
}
