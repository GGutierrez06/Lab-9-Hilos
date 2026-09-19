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

public class CentroLogistico {

    public final ControlSimulacion control = new ControlSimulacion();
    public final RegistroEventos registro = new RegistroEventos();
    public final Estadisticas estadisticas = new Estadisticas();

    public final ZonaLogistica recepcion;
    public final ZonaLogistica almacen;
    public final ZonaLogistica clasificacion;
    public final ZonaLogistica empaquetado;
    public final ZonaLogistica expedicion;
    public final ZonaLogistica reparto;
    public final ZonaLogistica entregados;
    public final ZonaLogistica devueltos;

    private final ListaEnlazada<HiloBase> todosLosHilos = new ListaEnlazada<>();
    private final ListaEnlazada<ClasificadorThread> clasificadores = new ListaEnlazada<>();
    private final ListaEnlazada<EmpaquetadorThread> empaquetadores = new ListaEnlazada<>();
    private final ListaEnlazada<RepartidorThread> repartidores = new ListaEnlazada<>();

    public CentroLogistico() {
        recepcion     = new ZonaLogistica("Recepción",Constantes.CAP_RECEPCION, control);
        almacen       = new ZonaLogistica("Almacén",Constantes.CAP_ALMACEN, control);
        clasificacion = new ZonaLogistica("Clasificación",Constantes.CAP_CLASIFICACION, control);
        empaquetado   = new ZonaLogistica("Empaquetado",Constantes.CAP_EMPAQUETADO, control);
        expedicion    = new ZonaLogistica("Expedición",Constantes.CAP_EXPEDICION, control);
        reparto       = new ZonaLogistica("Reparto",Constantes.capacidadTotalVehiculos(), control);
        entregados    = new ZonaLogistica("Entregados",Integer.MAX_VALUE, control);
        devueltos     = new ZonaLogistica("Devueltos",Integer.MAX_VALUE, control);

        crearHilos();
    }

    private void crearHilos() {
        todosLosHilos.agregar(new RecepcionThread(this));

        for (int i = 1; i <= Constantes.NUM_ALMACENISTAS; i++) {
            todosLosHilos.agregar(new AlmacenistaThread(i, this));
        }
        for (int i = 1; i <= Constantes.NUM_CLASIFICADORES; i++) {
            ClasificadorThread c = new ClasificadorThread(i, this);
            clasificadores.agregar(c);
            todosLosHilos.agregar(c);
        }
        for (int i = 1; i <= Constantes.NUM_EMPAQUETADORES; i++) {
            EmpaquetadorThread e = new EmpaquetadorThread(i, this);
            empaquetadores.agregar(e);
            todosLosHilos.agregar(e);
        }
        for (int i = 1; i <= Constantes.NUM_DESPACHADORES; i++) {
            todosLosHilos.agregar(new DespachadorThread(i, this));
        }
        for (int i = 0; i < Constantes.CAPACIDADES_REPARTIDORES.length; i++) {
            RepartidorThread r = new RepartidorThread(
                i + 1,
                Constantes.NOMBRES_REPARTIDORES[i],
                Constantes.CAPACIDADES_REPARTIDORES[i],
                i + 1,
                this);
            repartidores.agregar(r);
            todosLosHilos.agregar(r);
        }
    }

    public void iniciar() {
        if (control.getEstado() != ControlSimulacion.Estado.LISTA) {
            return;
        }
        control.iniciar();
        registro.registrar("=== Simulación iniciada ===");
        for (int i = 0; i < todosLosHilos.tamanio(); i++) {
            todosLosHilos.obtener(i).start();
        }
    }

    public void pausar() {
        control.pausar();
        registro.registrar("=== Simulación pausada ===");
    }

    public void reanudar() {
        control.reanudar();
        registro.registrar("=== Simulación reanudada ===");
    }

    public void detener() {
        if (control.getEstado() == ControlSimulacion.Estado.DETENIDA) {
            return;
        }
        control.detener();
        for (int i = 0; i < todosLosHilos.tamanio(); i++) {
            todosLosHilos.obtener(i).interrupt();
        }
        registro.registrar("=== Simulación detenida ===");
    }

    public ListaEnlazada<ClasificadorThread> getClasificadores() { 
        return clasificadores;
    }
    public ListaEnlazada<EmpaquetadorThread> getEmpaquetadores() {
        return empaquetadores;
    }
    public ListaEnlazada<RepartidorThread> getRepartidores(){
        return repartidores; 
    }
}
