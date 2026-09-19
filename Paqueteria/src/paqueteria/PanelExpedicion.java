/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria;

import paqueteria.base.Paquete;
import paqueteria.base.Rutas;
import paqueteria.util.ListaEnlazada;
import paqueteria.logica.*;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

/**
 *
 * @author gabri
 */

class PanelExpedicion extends JPanel {

    private static final int CASILLAS_POR_RUTA = 12;
    private static final int COLUMNAS_POR_RUTA = 3;

    private final JProgressBar barra = new JProgressBar();
    private final PanelFichas[] columnas = new PanelFichas[Rutas.NUM_RUTAS];
    private final JLabel[] titulos = new JLabel[Rutas.NUM_RUTAS];

    PanelExpedicion() {
        super(new BorderLayout(4, 4));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x90, 0x9C, 0xA8)),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        JLabel lblTitulo = new JLabel("EXPEDICIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 14f));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(4, 0, 2, 0));
        barra.setStringPainted(true);
        barra.setMinimum(0);

        JPanel norte = new JPanel(new BorderLayout(0, 4));
        norte.add(lblTitulo, BorderLayout.NORTH);
        norte.add(barra, BorderLayout.CENTER);
        add(norte, BorderLayout.NORTH);

        JPanel rutas = new JPanel(new GridLayout(1, Rutas.NUM_RUTAS, 10, 0));
        for (int i = 0; i < Rutas.NUM_RUTAS; i++) {
            titulos[i] = new JLabel("Ruta " + (i + 1), SwingConstants.CENTER);
            titulos[i].setFont(titulos[i].getFont().deriveFont(Font.BOLD, 13f));
            columnas[i] = new PanelFichas(CASILLAS_POR_RUTA, COLUMNAS_POR_RUTA);

            JPanel contenido = new JPanel(new BorderLayout());
            contenido.add(columnas[i], BorderLayout.NORTH);

            JPanel columna = new JPanel(new BorderLayout(0, 4));
            columna.add(titulos[i], BorderLayout.NORTH);
            columna.add(contenido, BorderLayout.CENTER);
            rutas.add(columna);
        }
        add(rutas, BorderLayout.CENTER);
    }

    void actualizar(ZonaLogistica zona) {
        ListaEnlazada<Paquete> foto = zona.instantanea();
        int n = foto.tamanio();
        int cap = zona.getCapacidad();
        barra.setMaximum(cap);
        barra.setValue(n);
        barra.setString(n + " / " + cap + " paquetes");
        barra.setForeground(Colores.ocupacion(n, cap));

        for (int i = 0; i < Rutas.NUM_RUTAS; i++) {
            int ruta = i + 1;
            int cuenta = 0;
            for (int j = 0; j < foto.tamanio(); j++) {
                if (foto.obtener(j).getRuta() == ruta) {
                    cuenta++;
                }
            }
            titulos[i].setText("Ruta " + ruta + "  (" + cuenta + ")");
            columnas[i].mostrar(foto, ruta);
        }
    }
}
