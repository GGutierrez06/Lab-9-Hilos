/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

/**
 *
 * @author gabri
 */

import paqueteria.base.*;

import java.util.Random;

public class ClasificadorThread extends HiloBase {

    private final int numero;
    private final Random azar = new Random();
    private volatile Paquete actual;

    public ClasificadorThread(int numero, CentroLogistico centro) {
        super("Clasificador-" + numero, centro);
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public Paquete getActual() {
        return actual;
    }

    @Override
    protected void ciclo() throws InterruptedException {
        Paquete p = centro.almacen.tomar();
        actual = p;
        p.cambiarEstado(EstadoPaquete.CLASIFICANDO);
        centro.registro.registrar(p.getCodigo() + " tomado por " + getName());

        int rango = Constantes.TIEMPO_CLASIFICAR_MAX_MS - Constantes.TIEMPO_CLASIFICAR_MIN_MS + 1;
        centro.control.dormir(Constantes.TIEMPO_CLASIFICAR_MIN_MS + azar.nextInt(rango));

        int ruta = Rutas.rutaDe(p.getCiudad());
        p.asignarRuta(ruta);
        p.cambiarEstado(EstadoPaquete.CLASIFICADO);
        centro.registro.registrar(p.getCodigo() + " clasificado -> Ruta " + ruta
                + " (" + p.getCiudad() + ")");

        centro.clasificacion.agregar(p);
        actual = null;
    }
}
