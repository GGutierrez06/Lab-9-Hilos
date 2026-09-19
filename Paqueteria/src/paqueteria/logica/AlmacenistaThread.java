/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

import paqueteria.base.EstadoPaquete;
import paqueteria.base.Paquete;

/**
 *
 * @author gabri
 */

public class AlmacenistaThread extends HiloBase {

    public AlmacenistaThread(int numero, CentroLogistico centro) {
        super("Almacenista-" + numero, centro);
    }

    @Override
    protected void ciclo() throws InterruptedException {
        Paquete p = centro.recepcion.tomar();
        centro.control.dormir(Constantes.TIEMPO_ALMACENAR_MS);
        p.cambiarEstado(EstadoPaquete.ALMACENADO);
        centro.almacen.agregar(p);
        centro.registro.registrar(p.getCodigo() + " almacenado");
    }
}
