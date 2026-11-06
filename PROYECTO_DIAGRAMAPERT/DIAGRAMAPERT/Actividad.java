
 
public class Actividad {
    String id;
    String descripcion;
    String predecesoras;
    int to;
    int tm; 
    int tp;
    double te;

    public void calcularTiempoEsperado() {
        te = (to + (4.0 * tm) + tp) / 6.0;
    }
}

