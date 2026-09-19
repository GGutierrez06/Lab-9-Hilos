package paqueteria.logica;

public final class Constantes {

    private Constantes() { }

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

    public static final int[] CAPACIDADES_REPARTIDORES = {5, 4, 6, 5};
    public static final String[] NOMBRES_REPARTIDORES = {"Marta", "Luis", "Ana", "Jordi"};

    public static final int MAX_INTENTOS = 3;
    public static final double PROB_CLIENTE_AUSENTE = 0.25;
    public static final double PROB_AVERIA = 0.08;

    public static int capacidadTotalVehiculos() {
        int total = 0;
        for (int c : CAPACIDADES_REPARTIDORES) {
            total += c;
        }
        return total;
    }
}
