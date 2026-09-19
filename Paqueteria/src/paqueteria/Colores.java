/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria;

import paqueteria.base.*;

import java.awt.Color;

/**
 *
 * @author gabri
 */

final class Colores {

    private Colores() { }

    static Color prioridad(Prioridad p) {
        switch (p) {
            case URGENTE: return Color.RED; 
            case ALTA:    return Color.ORANGE; // naranja
            case NORMAL:  return Color.YELLOW; // amarillo
            default:      return Color.GREEN; // verde
        }
    }

    static Color textoSobre(Prioridad p) {
        return p == Prioridad.URGENTE ? Color.WHITE : Color.BLACK;
    }

    static Color estado(EstadoRepartidor e) {
        switch (e) {
            case DISPONIBLE:        return Color.GREEN;
            case CARGANDO:          return Color.BLUE;
            case EN_RUTA:           return Color.ORANGE;
            case ENTREGANDO:        return Color.MAGENTA;
            case REGRESANDO:        return Color.CYAN;
            default:                return Color.RED; 
        }
    }

    static Color ocupacion(int valor, int maximo) {
        double f = maximo == 0 ? 0 : (double) valor / maximo;
        if (f < 0.6) return Color.GREEN;
        if (f < 0.9) return Color.YELLOW;
        return Color.RED;
    }
}
