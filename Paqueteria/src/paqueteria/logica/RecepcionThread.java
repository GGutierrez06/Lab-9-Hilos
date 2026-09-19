/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

import paqueteria.base.*;

import java.util.Random;

/**
 *
 * @author gabri
 */

public class RecepcionThread extends HiloBase {

    private final GeneradorPaquetes generador = new GeneradorPaquetes();
    private final Random azar = new Random();

    public RecepcionThread(CentroLogistico centro) {
        super("Recepcion", centro);
    }

    @Override
    protected void ciclo() throws InterruptedException {
        int rango = Constantes.INTERVALO_MAX_MS - Constantes.INTERVALO_MIN_MS + 1;
        long espera = Constantes.INTERVALO_MIN_MS + azar.nextInt(rango);
        centro.control.dormir(espera);

        Paquete p = generador.generar();
        centro.estadisticas.registrarGenerado();

        if (centro.recepcion.estaLlena()) {
            centro.registro.registrar("Recepción llena: " + p.getCodigo() + " espera un hueco");
        }
        centro.recepcion.agregar(p);
        centro.registro.registrar(p.getCodigo() + " recibido [" + p.getPrioridad()
                + ", " + p.getCiudad() + ", " + p.getPeso() + " kg]");
    }
}
