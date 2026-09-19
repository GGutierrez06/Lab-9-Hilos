/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.logica;

import paqueteria.util.ListaEnlazada;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class RegistroEventos {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final ListaEnlazada<String> pendientes = new ListaEnlazada<>();

    public synchronized void registrar(String mensaje) {
        String hora = LocalTime.now().format(FORMATO);
        pendientes.agregar(hora + " | " + mensaje);
    }

    public synchronized String siguienteLinea() {
        return pendientes.eliminarPrimero();
    }
}
