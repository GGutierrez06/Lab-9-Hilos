/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria;

import paqueteria.util.ListaEnlazada;
import paqueteria.base.Paquete;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author gabri
 */

class PanelZona extends JPanel {

    private final JProgressBar barra = new JProgressBar();
    private final PanelFichas fichas;

    
    PanelZona(String titulo, int capacidad, int columnas, JComponent extra) {
        super(new BorderLayout(4, 4));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x90, 0x9C, 0xA8)),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        JLabel lblTitulo = new JLabel(titulo.toUpperCase(), SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 14f));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(4, 0, 2, 0));

        barra.setStringPainted(true);
        barra.setMinimum(0);

        JPanel norte = new JPanel(new BorderLayout(0, 4));
        norte.add(lblTitulo, BorderLayout.NORTH);
        norte.add(barra, BorderLayout.CENTER);
        if (extra != null) {
            norte.add(extra, BorderLayout.SOUTH);
        }
        add(norte, BorderLayout.NORTH);

        fichas = new PanelFichas(capacidad, columnas);
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(fichas, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
    }

}
