/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria;

import paqueteria.base.Prioridad;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

/**
 *
 * @author gabri
 */

class PanelEstadisticas extends JPanel {

    private final JLabel generados = valor();
    private final JLabel entregados = valor();
    private final JLabel devueltos = valor();
    private final JLabel enProceso = valor();
    private final JLabel pendientes = valor();
    private final JLabel promedio = valor();
    private final JLabel[] porRepartidor;

    PanelEstadisticas(int numRepartidores) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x90, 0x9C, 0xA8)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        JLabel titulo = new JLabel("ESTADÍSTICAS", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
        titulo.setBorder(BorderFactory.createEmptyBorder(4, 0, 2, 0));
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        add(titulo);
        add(Box.createVerticalStrut(8));

        JPanel filas = new JPanel(new GridLayout(0, 2, 6, 6));
        filas.setAlignmentX(CENTER_ALIGNMENT);
        agregar(filas, "Generados:", generados);
        agregar(filas, "Entregados:", entregados);
        agregar(filas, "Devueltos:", devueltos);
        agregar(filas, "En proceso:", enProceso);
        agregar(filas, "Pendientes:", pendientes);
        agregar(filas, "Tiempo prom.:", promedio);
        add(filas);

        add(Box.createVerticalStrut(12));
        JLabel sub = new JLabel("Entregas por repartidor", SwingConstants.CENTER);
        sub.setFont(sub.getFont().deriveFont(Font.BOLD));
        sub.setAlignmentX(CENTER_ALIGNMENT);
        add(sub);
        add(Box.createVerticalStrut(6));

        porRepartidor = new JLabel[numRepartidores];
        JPanel filasRep = new JPanel(new GridLayout(0, 2, 6, 6));
        filasRep.setAlignmentX(CENTER_ALIGNMENT);
        for (int i = 0; i < numRepartidores; i++) {
            porRepartidor[i] = valor();
            agregar(filasRep, "Repartidor " + (i + 1) + ":", porRepartidor[i]);
        }
        add(filasRep);

        add(Box.createVerticalStrut(14));
        JLabel leyenda = new JLabel("Prioridades", SwingConstants.CENTER);
        leyenda.setFont(leyenda.getFont().deriveFont(Font.BOLD));
        leyenda.setAlignmentX(CENTER_ALIGNMENT);
        add(leyenda);
        add(Box.createVerticalStrut(6));
        JPanel fichas = new JPanel(new GridLayout(0, 2, 4, 4));
        fichas.setAlignmentX(CENTER_ALIGNMENT);
        for (Prioridad p : Prioridad.values()) {
            JLabel l = new JLabel(p.name(), SwingConstants.CENTER);
            l.setOpaque(true);
            l.setBackground(Colores.prioridad(p));
            l.setForeground(Colores.textoSobre(p));
            l.setFont(l.getFont().deriveFont(Font.BOLD, 11f));
            l.setBorder(BorderFactory.createEmptyBorder(3, 4, 3, 4));
            fichas.add(l);
        }
        add(fichas);
        add(Box.createVerticalGlue());

        setPreferredSize(new Dimension(270, 400));
    }

    private static JLabel valor() {
        JLabel l = new JLabel("0", SwingConstants.RIGHT);
        l.setFont(l.getFont().deriveFont(Font.BOLD, 13f));
        return l;
    }

    private static void agregar(JPanel panel, String texto, JLabel valor) {
        panel.add(new JLabel(texto));
        panel.add(valor);
    }

}
