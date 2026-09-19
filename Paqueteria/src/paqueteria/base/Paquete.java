/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.base;

/**
 *
 * @author gabri
 */
public class Paquete {

    private final String codigo;
    private final String cliente;
    private final String direccion;
    private final String ciudad;
    private final double peso;
    private final Prioridad prioridad;
    private final long creadoEn;

    private EstadoPaquete estado = EstadoPaquete.RECIBIDO;
    private int ruta = 0;    
    private int intentos = 0;
    private long finalizadoEn = 0; 

    public Paquete(String codigo, String cliente, String direccion, String ciudad, double peso, Prioridad prioridad) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.peso = peso;
        this.prioridad = prioridad;
        this.creadoEn = System.currentTimeMillis();
    }

    public synchronized void cambiarEstado(EstadoPaquete nuevo) {
        if (!estado.puedePasarA(nuevo)) {
            throw new IllegalStateException(
                "Transición inválida en " + codigo + ": " + estado + " -> " + nuevo);
        }
        estado = nuevo;
        if (nuevo == EstadoPaquete.ENTREGADO || nuevo == EstadoPaquete.DEVUELTO) {
            finalizadoEn = System.currentTimeMillis();
        }
    }

    public synchronized void asignarRuta(int ruta) {
        this.ruta = ruta;
    }

    public synchronized int registrarIntentoFallido() {
        return ++intentos;
    }

    public String getCodigo(){
        return codigo;
    }
    public String getCliente(){
        return cliente; 
    }
    public String getDireccion(){
        return direccion;
    }
    public String getCiudad(){
        return ciudad;
    }
    public double getPeso(){
        return peso;
    }
    public Prioridad getPrioridad(){
        return prioridad;
    }
    public long getCreadoEn(){ 
        return creadoEn; 
    }

    public synchronized EstadoPaquete getEstado(){
        return estado; 
    }
    public synchronized int getRuta(){ 
        return ruta;
    }
    public synchronized int getIntentos(){ 
        return intentos; 
    }
    public synchronized long getFinalizadoEn(){
        return finalizadoEn; 
    }

    public synchronized String getRutaTexto() {
        if(ruta == 0){
            return "-";
        } else {
            return String.format("R%02d", ruta);
        }
    }

    @Override
    public synchronized String toString() {
        return "Código:    " + codigo + "\n"
             + "Cliente:   " + cliente + "\n"
             + "Dirección: " + direccion + "\n"
             + "Ciudad:    " + ciudad + "\n"
             + "Peso:      " + peso + " kg\n"
             + "Prioridad: " + prioridad + "\n"
             + "Estado:    " + estado + "\n"
             + "Ruta:      " + getRutaTexto() + "\n"
             + "Intentos:  " + intentos;
    }
}