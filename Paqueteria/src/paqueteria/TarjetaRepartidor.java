/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

/**
 *
 * @author gabri
 */

class TarjetaRepartidor extends JPanel {

    private final JLabel titulo = new JLabel("", SwingConstants.CENTER);
    private final JLabel estado = new JLabel("", SwingConstants.CENTER);
    private final JProgressBar capacidad = new JProgressBar();
    private final JLabel entregados = new JLabel("", SwingConstants.CENTER);
    private final JLabel actividad = new JLabel("", SwingConstants.CENTER);

    TarjetaRepartidor() {
        super(new GridLayout(0, 1, 2, 3));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x90, 0x9C, 0xA8)),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 13f));
        estado.setFont(estado.getFont().deriveFont(Font.BOLD, 13f));
        capacidad.setStringPainted(true);
        capacidad.setMinimum(0);
        actividad.setFont(actividad.getFont().deriveFont(Font.ITALIC, 11f));

        add(titulo);
        add(estado);
        add(capacidad);
        add(entregados);
        add(actividad);
    }

}
