/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

import paqueteria.base.*;

/**
 *
 * @author gabri
 */

public class DespachadorThread extends HiloBase {

    public DespachadorThread(int numero, CentroLogistico centro) {
        super("Despachador-" + numero, centro);
    }

    @Override
    protected void ciclo() throws InterruptedException {
        Paquete p = centro.empaquetado.tomar();
        centro.control.dormir(Constantes.TIEMPO_DESPACHAR_MS);
        p.cambiarEstado(EstadoPaquete.EN_EXPEDICION);
        centro.expedicion.agregar(p);
        centro.registro.registrar(p.getCodigo() + " en expedicion (Ruta " + p.getRuta() + ")");
    }
}
