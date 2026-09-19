/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria.base;

/**
 *
 * @author gabri
 */
public enum EstadoPaquete {
    RECIBIDO,
    ALMACENADO,
    CLASIFICANDO,
    CLASIFICADO,
    EMPAQUETANDO,
    EMPAQUETADO,
    EN_EXPEDICION,
    EN_REPARTO,
    ENTREGADO,
    NUEVO_INTENTO,
    DEVUELTO;

    public boolean puedePasarA(EstadoPaquete destino) {
        switch (this) {
            case RECIBIDO:       return destino == ALMACENADO;
            case ALMACENADO:     return destino == CLASIFICANDO;
            case CLASIFICANDO:   return destino == CLASIFICADO;
            case CLASIFICADO:    return destino == EMPAQUETANDO;
            case EMPAQUETANDO:   return destino == EMPAQUETADO;
            case EMPAQUETADO:    return destino == EN_EXPEDICION;
            case EN_EXPEDICION:  return destino == EN_REPARTO;
            case EN_REPARTO:     return destino == ENTREGADO || destino == NUEVO_INTENTO;
            case NUEVO_INTENTO:  return destino == EN_REPARTO || destino == DEVUELTO;
            default:             return false;
        }
    }
}