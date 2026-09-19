/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paqueteria;

import paqueteria.base.*;
import paqueteria.logica.Constantes;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author gabri
 */
public class VentanaPrincipal extends JFrame {

    private static final int MAX_LINEAS_LOG = 600;


    private final JButton btnIniciar   = new JButton("INICIAR");
    private final JButton btnPausar    = new JButton("PAUSAR");
    private final JButton btnReanudar  = new JButton("REANUDAR");
    private final JButton btnDetener   = new JButton("DETENER");
    private final JButton btnReiniciar = new JButton("REINICIAR");
    private final JLabel lblEstado     = new JLabel("Estado: -");

    private final PanelZona pRecepcion = new PanelZona("Recepción", Constantes.CAP_RECEPCION, 3, null);
    private final PanelZona pAlmacen = new PanelZona("Almacén", Constantes.CAP_ALMACEN, 5, null);
    private final JLabel[] lblClasificadores = new JLabel[Constantes.NUM_CLASIFICADORES];
    private final PanelZona pClasificacion;
    private final JLabel[] lblEmpaquetadores = new JLabel[Constantes.NUM_EMPAQUETADORES];
    private final PanelZona pEmpaquetado;
    private final PanelExpedicion pExpedicion = new PanelExpedicion();
    private final TarjetaRepartidor[] tarjetas =
        new TarjetaRepartidor[Constantes.CAPACIDADES_REPARTIDORES.length];
    private final JLabel lblEnReparto = new JLabel("", SwingConstants.CENTER);
    private final PanelEstadisticas pEstadisticas =
        new PanelEstadisticas(Constantes.CAPACIDADES_REPARTIDORES.length);
    private final JTextArea log = new JTextArea();


    public VentanaPrincipal() {
        super("Sistema de Paquetería - Centro Logístico");

        JPanel trabajadoresClas = new JPanel(new GridLayout(0, 1, 0, 1));
        for (int i = 0; i < lblClasificadores.length; i++) {
            lblClasificadores[i] = new JLabel();
            trabajadoresClas.add(lblClasificadores[i]);
        }
        pClasificacion = new PanelZona("Clasificación", Constantes.CAP_CLASIFICACION, 5, trabajadoresClas);

        JPanel trabajadoresEmp = new JPanel(new GridLayout(1, 0, 20, 0));
        for (int i = 0; i < lblEmpaquetadores.length; i++) {
            lblEmpaquetadores[i] = new JLabel("", SwingConstants.CENTER);
            trabajadoresEmp.add(lblEmpaquetadores[i]);
        }
        pEmpaquetado = new PanelZona("Empaquetado", Constantes.CAP_EMPAQUETADO, 8, trabajadoresEmp);

        for (int i = 0; i < tarjetas.length; i++) {
            tarjetas[i] = new TarjetaRepartidor();
        }

        construirInterfaz();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setExtendedState(MAXIMIZED_BOTH);
    }


    private void construirInterfaz() {
        setLayout(new BorderLayout(6, 6));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(6, 8, 8, 8));

        JLabel titulo = new JLabel("SISTEMA DE PAQUETERÍA - CENTRO LOGÍSTICO", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        for (JButton b : new JButton[]{btnIniciar, btnPausar, btnReanudar, btnDetener, btnReiniciar}) {
            b.setFont(b.getFont().deriveFont(Font.BOLD));
            b.setFocusPainted(false);
            controles.add(b);
        }
        controles.add(separador());
        lblEstado.setFont(lblEstado.getFont().deriveFont(Font.BOLD));
        controles.add(lblEstado);

        JPanel superior = new JPanel(new BorderLayout());
        superior.add(titulo, BorderLayout.NORTH);
        superior.add(controles, BorderLayout.CENTER);
        add(superior, BorderLayout.NORTH);

        JPanel zonas = new JPanel(new GridBagLayout());
        zonas.add(pRecepcion,     celda(0, 0, 1, 0.25, 0.5));
        zonas.add(pAlmacen,       celda(1, 0, 1, 0.35, 0.5));
        zonas.add(pClasificacion, celda(2, 0, 1, 0.40, 0.5));
        zonas.add(pEmpaquetado,   celda(0, 1, 3, 1.00, 0.0));
        zonas.add(pExpedicion,    celda(0, 2, 3, 1.00, 0.5));
        zonas.add(construirPanelReparto(), celda(0, 3, 3, 1.00, 0.0));
        add(zonas, BorderLayout.CENTER);

        add(pEstadisticas, BorderLayout.EAST);

        log.setEditable(false);
        log.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollLog = new JScrollPane(log);
        scrollLog.setBorder(BorderFactory.createTitledBorder("REGISTRO DEL SISTEMA"));
        scrollLog.setPreferredSize(new Dimension(100, 120));
        add(scrollLog, BorderLayout.SOUTH);
    }

    private JPanel construirPanelReparto() {
        JPanel panel = new JPanel(new BorderLayout(4, 4));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0x90, 0x9C, 0xA8)),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        JLabel titulo = new JLabel("REPARTO", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
        titulo.setBorder(BorderFactory.createEmptyBorder(4, 0, 2, 0));
        JPanel norte = new JPanel();
        norte.setLayout(new BoxLayout(norte, BoxLayout.Y_AXIS));
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        lblEnReparto.setAlignmentX(CENTER_ALIGNMENT);
        norte.add(titulo);
        norte.add(lblEnReparto);
        panel.add(norte, BorderLayout.NORTH);

        JPanel fila = new JPanel(new GridLayout(1, tarjetas.length, 8, 0));
        for (TarjetaRepartidor t : tarjetas) {
            fila.add(t);
        }
        panel.add(fila, BorderLayout.CENTER);
        return panel;
    }

    private static JLabel separador() {
        return new JLabel("   |   ");
    }

    private static GridBagConstraints celda(int x, int y, int ancho, double wx, double wy) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = ancho;
        c.weightx = wx;
        c.weighty = wy;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3, 3, 3, 3);
        return c;
    }

}

