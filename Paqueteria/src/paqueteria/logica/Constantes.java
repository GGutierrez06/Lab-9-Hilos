package paqueteria.logica;

public final class Constantes {

    public static final int CAP_RECEPCION     = 10;
    public static final int CAP_ALMACEN       = 20;
    public static final int CAP_CLASIFICACION = 10;
    public static final int CAP_EMPAQUETADO   = 8;
    public static final int CAP_EXPEDICION    = 15;

    public static final int NUM_ALMACENISTAS  = 1;
    public static final int NUM_CLASIFICADORES = 3;
    public static final int NUM_EMPAQUETADORES = 2;
    public static final int NUM_DESPACHADORES  = 1;

    public static final int INTERVALO_MIN_MS = 500;
    public static final int INTERVALO_MAX_MS = 1200;

    public static final int TIEMPO_ALMACENAR_MS = 400;
    public static final int TIEMPO_CLASIFICAR_MIN_MS = 800;
    public static final int TIEMPO_CLASIFICAR_MAX_MS = 1600;
    public static final int TIEMPO_DESPACHAR_MS = 300;

    public static final int[] CAPACIDADES_REPARTIDORES = {5, 4, 6, 5};
    public static final String[] NOMBRES_REPARTIDORES = {"Ruben", "Manuel", "Jorge", "Carlos"};

    public static final int TIEMPO_VIAJE_MS = 2500;
    public static final int TIEMPO_ENTREGA_MS = 1000;
    public static final int TIEMPO_AVERIA_MS = 5000;

    public static final int MAX_INTENTOS = 3;
    public static final double PROB_CLIENTE_AUSENTE = 0.25;
    public static final double PROB_AVERIA = 0.08;

    public static int tiempoEmpaquetadoMs(double peso) {
        if (peso <= 2.0) return 1000;
        if (peso <= 5.0) return 2000;
        return 3000;
    }

    public static int capacidadTotalVehiculos() {
        int total = 0;
        for (int c : CAPACIDADES_REPARTIDORES) {
            total += c;
        }
        return total;
    }
}
