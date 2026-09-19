/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.base;

import java.util.Random;

/**
 *
 * @author gabri
 */
public class GeneradorPaquetes {

    private static final String[] NOMBRES = {
        "Gabriel", "Valeria", "Alejandra", "Angel", "Angie", "David",
        "Sergio", "Ana", "Ian", "Daniel", "Ruben", "Elena"
    };
    private static final String[] APELLIDOS = {
        "Gutierrez", "Zavala", "Meza", "Garcia", "Hernandez", "Coello", "Leiva", "Santos", "Lopez", "Perez"
    };
    private static final String[] CALLES = {
        "Circunvalacion", "Blv Morazan", "Blv Mackay", "6 Avenida NO", "9 Calle S",
        "Blv Mario Catarino", "7 Calle", "1 Avenida", "3 Calle", "13 Avenida"
    };

    private int contador = 0;
    private final Random azar = new Random();

    public Paquete generar() {
        contador++;
        String codigo = String.format("PKG-%04d", contador);
        String cliente = NOMBRES[azar.nextInt(NOMBRES.length)] + " "
                       + APELLIDOS[azar.nextInt(APELLIDOS.length)];
        String direccion = CALLES[azar.nextInt(CALLES.length)] + ", " + (1 + azar.nextInt(200));
        String ciudad = Rutas.CIUDADES[azar.nextInt(Rutas.CIUDADES.length)];
        double peso = Math.round((0.3 + azar.nextDouble() * 7.7) * 10) / 10.0;
        return new Paquete(codigo, cliente, direccion, ciudad, peso, prioridadAleatoria());
    }

    private Prioridad prioridadAleatoria() {
        double r = azar.nextDouble();
        if (r < 0.10) return Prioridad.URGENTE;
        if (r < 0.30) return Prioridad.ALTA;
        if (r < 0.80) return Prioridad.NORMAL;
        return Prioridad.BAJA;
    }
}
