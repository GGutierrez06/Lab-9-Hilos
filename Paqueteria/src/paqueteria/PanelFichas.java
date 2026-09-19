/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria;

import paqueteria.util.ListaEnlazada;
import paqueteria.base.Paquete;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author gabri
 */

class PanelFichas extends JPanel {

    private static final Color COLOR_VACIO = new Color(0xEC, 0xEF, 0xF1);
    private static final Color COLOR_BORDE = new Color(0x90, 0x9C, 0xA8);

    private final JLabel[] fichas;
    private final Paquete[] mostrados; 
    
    PanelFichas(int cantidad, int columnas) {
        int filas = (cantidad + columnas - 1) / columnas;
        setLayout(new GridLayout(filas, columnas, 4, 4));

        fichas = new JLabel[cantidad];
        mostrados = new Paquete[cantidad];

        for (int i = 0; i < cantidad; i++) {
            final int posicion = i;
            JLabel ficha = new JLabel("", SwingConstants.CENTER);
            ficha.setOpaque(true);
            ficha.setFont(ficha.getFont().deriveFont(Font.BOLD, 13f));
            ficha.setPreferredSize(new Dimension(62, 28));
            ficha.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
            ficha.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    verDetalle(posicion);
                }
            });
            fichas[i] = ficha;
            add(ficha);
            vaciar(i);
        }
    }

    void mostrar(ListaEnlazada<Paquete> lista, int ruta) {
        int total = 0;
        for (int i = 0; i < lista.tamanio(); i++) {
            Paquete p = lista.obtener(i);
            if (ruta == 0 || p.getRuta() == ruta) {
                total++;
            }
        }

        int casillas = fichas.length;
        if (total > casillas) {
            casillas = fichas.length - 1;
        }

        int usadas = 0;
        for (int i = 0; i < lista.tamanio(); i++) {
            Paquete p = lista.obtener(i);
            if (ruta == 0 || p.getRuta() == ruta) {
                if (usadas < casillas) {
                    poner(usadas, p);
                }
                usadas++;
            }
        }
        for (int i = usadas; i < casillas; i++) {
            vaciar(i);
        }
        if (total > fichas.length) {
            int ultima = fichas.length - 1;
            vaciar(ultima);
            fichas[ultima].setText("+" + (total - casillas));
        }
    }

    private void poner(int posicion, Paquete p) {
        JLabel ficha = fichas[posicion];
        mostrados[posicion] = p;
        ficha.setText("P" + p.getCodigo().substring(4));
        ficha.setBackground(Colores.prioridad(p.getPrioridad()));
        ficha.setForeground(Colores.textoSobre(p.getPrioridad()));
        ficha.setToolTipText(p.getCodigo() + " - " + p.getCliente() + " - "
                + p.getCiudad() + " - " + p.getPeso() + " kg - " + p.getPrioridad());
    }

    private void vaciar(int posicion) {
        mostrados[posicion] = null;
        fichas[posicion].setText("");
        fichas[posicion].setBackground(COLOR_VACIO);
        fichas[posicion].setToolTipText(null);
    }

    private void verDetalle(int posicion) {
        Paquete p = mostrados[posicion];
        if (p != null) {
            JOptionPane.showMessageDialog(this, p.toString(), "Paquete " + p.getCodigo(),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
