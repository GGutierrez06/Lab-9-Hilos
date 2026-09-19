/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

import paqueteria.base.Paquete;

/**
 *
 * @author gabri
 */

public class Estadisticas {

    private int generados;
    private int entregados;
    private int devueltos;
    private long sumaTiemposMs;

    public synchronized void registrarGenerado() {
        generados++;
    }

    public synchronized void registrarEntregado(Paquete p) {
        entregados++;
        sumaTiemposMs += p.getFinalizadoEn() - p.getCreadoEn();
    }

    public synchronized void registrarDevuelto() {
        devueltos++;
    }

    public synchronized int getGenerados()  { return generados; }
    public synchronized int getEntregados() { return entregados; }
    public synchronized int getDevueltos()  { return devueltos; }

    public synchronized double getTiempoPromedioSegundos() {
        return entregados == 0 ? 0.0 : sumaTiemposMs / 1000.0 / entregados;
    }
}
