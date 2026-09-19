/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

import paqueteria.util.ListaEnlazada;
import paqueteria.base.*;

import java.util.Random;

/**
 *
 * @author gabri
 */

public class RepartidorThread extends HiloBase {

    private final int id;
    private final String nombre;
    private final int capacidad;
    private final int ruta;
    private final Random azar = new Random();

    private volatile EstadoRepartidor estado = EstadoRepartidor.DISPONIBLE;
    private volatile int cargados = 0;   
    private volatile int entregados = 0; 
    private volatile String actividad = "";

    private final ListaEnlazada<Paquete> cargamento = new ListaEnlazada<>();

    public RepartidorThread(int id, String nombre, int capacidad, int ruta, CentroLogistico centro) {
        super("Repartidor-" + id, centro);
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ruta = ruta;
    }

    public int getIdRepartidor(){
        return id; 
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getCapacidad(){
        return capacidad;
    }
    
    public int getRuta(){
        return ruta; 
    }
    
    public EstadoRepartidor getEstado(){
        return estado; 
    }
    
    public int getCargados(){
        return cargados; 
    }
    
    public int getEntregados(){
        return entregados; 
    }
    
    public String getActividad(){
        return actividad;
    }

    @Override
    protected void ciclo() throws InterruptedException {
        cargarVehiculo();
        salirARuta();
        repartir();
        regresar();
    }

    private void cargarVehiculo() throws InterruptedException {
        estado = EstadoRepartidor.DISPONIBLE;
        actividad = "Esperando paquetes de la Ruta " + ruta;

        while (cargados < capacidad) {
            Paquete p = centro.expedicion.tomarDeRuta(ruta);
            estado = EstadoRepartidor.CARGANDO;
            cargamento.agregar(p);
            cargados++;
            actividad = "Cargando " + p.getCodigo();
            centro.registro.registrar(p.getCodigo() + " asignado a " + getName()
                    + " (" + cargados + "/" + capacidad + ")");
            centro.control.dormir(250);
        }

        actividad = "LLENO";
        centro.registro.registrar(getName() + " LLENO (" + cargados + "/" + capacidad + ")");
    }

    private void salirARuta() throws InterruptedException {
        estado = EstadoRepartidor.EN_RUTA;
        actividad = "En ruta con " + cargados + " paquetes (Ruta " + ruta + ")";
        centro.registro.registrar(getName() + " inicia Ruta " + ruta + " con " + cargados + " paquetes");
        for (int i = 0; i < cargamento.tamanio(); i++) {
            Paquete p = cargamento.obtener(i);
            p.cambiarEstado(EstadoPaquete.EN_REPARTO);
            centro.reparto.agregar(p);
        }
        centro.control.dormir(Constantes.TIEMPO_VIAJE_MS);
    }

    private void repartir() throws InterruptedException {
        while (!cargamento.estaVacia()) {
            int enEstaRonda = cargamento.tamanio();
            for (int i = 0; i < enEstaRonda; i++) {
                Paquete p = cargamento.eliminarPrimero();
                boolean reintentar = intentarEntrega(p);
                if (reintentar) {
                    cargamento.agregar(p);
                }
            }
            if (!cargamento.estaVacia()) {
                actividad = "Reintentando " + cargamento.tamanio() + " entrega(s)";
                centro.control.dormir(500);
            }
        }
    }

    private boolean intentarEntrega(Paquete p) throws InterruptedException {
        estado = EstadoRepartidor.ENTREGANDO;
        actividad = "Entregando " + p.getCodigo() + " (intento " + (p.getIntentos() + 1) + ")";
        centro.control.dormir(Constantes.TIEMPO_ENTREGA_MS);

        boolean ausente = azar.nextDouble() < Constantes.PROB_CLIENTE_AUSENTE;
        if (!ausente) {
            p.cambiarEstado(EstadoPaquete.ENTREGADO);
            centro.reparto.quitar(p);
            centro.entregados.agregar(p);
            centro.estadisticas.registrarEntregado(p);
            entregados++;
            cargados--;
            centro.registro.registrar(p.getCodigo() + " entregado por " + getName());
            return false;
        }

        int fallidos = p.registrarIntentoFallido();
        p.cambiarEstado(EstadoPaquete.NUEVO_INTENTO);
        centro.registro.registrar(p.getCodigo() + " intento " + fallidos + " -> cliente ausente");

        if (fallidos >= Constantes.MAX_INTENTOS) {
            p.cambiarEstado(EstadoPaquete.DEVUELTO);
            centro.reparto.quitar(p);
            centro.devueltos.agregar(p);
            centro.estadisticas.registrarDevuelto();
            cargados--;
            centro.registro.registrar(p.getCodigo() + " -> DEVUELTO (" + fallidos + " intentos fallidos)");
            return false;
        }
        p.cambiarEstado(EstadoPaquete.EN_REPARTO);
        return true;
    }

    private void regresar() throws InterruptedException {
        estado = EstadoRepartidor.REGRESANDO;
        actividad = "Regresando al centro";
        centro.control.dormir(Constantes.TIEMPO_VIAJE_MS);

        if (azar.nextDouble() < Constantes.PROB_AVERIA) {
            estado = EstadoRepartidor.FUERA_DE_SERVICIO;
            actividad = "Avería del camion";
            centro.registro.registrar(getName() + " FUERA DE SERVICIO (averiado)");
            centro.control.dormir(Constantes.TIEMPO_AVERIA_MS);
            centro.registro.registrar(getName() + " reparado, vuelve a estar disponible");
        }
    }
}
