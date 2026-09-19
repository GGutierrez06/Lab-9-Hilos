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

public class EmpaquetadorThread extends HiloBase {

    private final int numero;
    private volatile Paquete actual;

    public EmpaquetadorThread(int numero, CentroLogistico centro) {
        super("Empaquetador-" + numero, centro);
        this.numero = numero;
    }

    public int getNumero(){ 
        return numero; 
    }
    
    public Paquete getActual()  {
        return actual; 
    }

    @Override
    protected void ciclo() throws InterruptedException {
        Paquete p = centro.clasificacion.tomar();
        actual = p;
        p.cambiarEstado(EstadoPaquete.EMPAQUETANDO);
        centro.registro.registrar(p.getCodigo() + " empaquetando (" + p.getPeso()
                + " kg) por " + getName());

        centro.control.dormir(Constantes.tiempoEmpaquetadoMs(p.getPeso()));

        p.cambiarEstado(EstadoPaquete.EMPAQUETADO);
        centro.empaquetado.agregar(p);
        centro.registro.registrar(p.getCodigo() + " empaquetado");
        actual = null;
    }
}
