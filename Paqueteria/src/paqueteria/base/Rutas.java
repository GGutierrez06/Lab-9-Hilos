/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.base;

/**
 *
 * @author gabri
 */
public final class Rutas {

    public static final int NUM_RUTAS = 4;

    public static final String[] CIUDADES = {
        "San Pedro Sula", "Villanueva", "Choloma", "El Progreso", "Puerto Cortes"
    };

    private Rutas() { }

    public static int rutaDe(String ciudad) {
        switch (ciudad) {
            case "San Pedro Sula":
            case "Villanueva":
                return 1;
            case "Choloma":
                return 2;
            case "El Progreso":
                return 3;
            case "Puerto Cortes":
                return 4;
            default:
                return 1;
        }
    }
}
