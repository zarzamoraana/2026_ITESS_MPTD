
//  javac DiagramaPert.java
//  java DiagramaPert.java


import java.util.Scanner;

public class DiagramaPert {

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("DIAGRAMA DE PERT");

        System.out.print("¿Trabajaremos con dias, semanas o meses? ");
        String unidadTiempo = scanner.nextLine();

        System.out.print("¿Cuantas actividades tenemos? ");
        int n = scanner.nextInt();
        scanner.nextLine();

        String[] actividades = new String[n];
        String[] descripciones = new String[n];
        String[] predecesoras = new String[n];

        int[] to = new int[n];
        int[] tm = new int[n];
        int[] tp = new int[n];

        double[] te = new double[n];
        double[] it = new double[n];
        double[] tt = new double[n];
        double[] ft = new double[n];
        double[] itArdios = new double[n];
        double[] holgura = new double[n];
        double[] varianza = new double[n];

        boolean[] calculada = new boolean[n];

        // REGISTRO DE ACTIVIDADES

        System.out.println("\nREGISTRO DE ACTIVIDADES");

        for (int i = 0; i < n; i++) {

            System.out.println("\n--- Actividad " + (i + 1) + " ---");

            System.out.print("Identificador (ej. A, B, C): ");
            actividades[i] = scanner.nextLine().trim();

            System.out.print("Describe brevemente de que tratan las actividades: ");
            descripciones[i] = scanner.nextLine();

            System.out.print("Predecesoras (escribelas con , o ninguna): ");
            predecesoras[i] = scanner.nextLine();
        }

        // REGISTRO DE TIEMPOS OMP

        System.out.println("\nREGISTRO DE TIEMPOS OMP");
        System.out.println("Por favor, ingresa los valores de los 3 tiempos");

        for (int i = 0; i < n; i++) {

            System.out.println("\nTiempos para la actividad ["
                    + actividades[i] + "] - " + descripciones[i] + ":");

            System.out.print("Tiempo optimista (O): ");
            to[i] = scanner.nextInt();

            System.out.print("Tiempo mas probable (M): ");
            tm[i] = scanner.nextInt();

            System.out.print("Tiempo pesimista (P): ");
            tp[i] = scanner.nextInt();

            scanner.nextLine();

            // CALCULO DE TE Y VARIANZA
            te[i] = (to[i] + (4.0 * tm[i]) + tp[i]) / 6.0;
            double desviacion = (tp[i] - to[i]) / 6.0;
            varianza[i] = desviacion * desviacion;
        }

        // Primera tabla actividades

        System.out.println("\n============================================================");
        System.out.println("RESUMEN DE TIEMPOS (Unidad: " + unidadTiempo + ")");
        System.out.println("============================================================");

        System.out.printf("%-12s %-35s %-20s %-8s%n",
                "ID", "Descripcion", "Predecesoras", "Te");

        for (int i = 0; i < n; i++) {

            System.out.printf("%-12s %-35s %-20s %-8.2f%n",
                    actividades[i],
                    descripciones[i],
                    predecesoras[i],
                    te[i]);
        }

        System.out.println("============================================================");

        // Segunda tabla:TE

        System.out.println("\nREGISTRO DE ACTIVIDADES");
        System.out.println("------------------------------------------------");

        System.out.printf("%-10s %-20s %-10s%n",
                "ACT", "Registro de actividad", "TE");

        for (int i = 0; i < n; i++) {

            System.out.printf("%-10d %-20s %-10.2f%n",
                    i + 1,
                    actividades[i],
                    te[i]);
        }

        System.out.println("------------------------------------------------");

        // Calculo IT

        int procesadas = 0;
        boolean huboProgreso = true;
        boolean errorPredecesora = false;

        while (procesadas < n && huboProgreso && !errorPredecesora) {

            huboProgreso = false;

            for (int i = 0; i < n; i++) {

                if (calculada[i]) {
                    continue;
                }

                String texto = predecesoras[i].trim();

                // actividades sin prodesesoras

                if (texto.isEmpty() || texto.equalsIgnoreCase("ninguna")) {

                    it[i] = 0;
                    tt[i] = it[i] + te[i];

                    calculada[i] = true;
                    procesadas++;
                    huboProgreso = true;

                } else {

                    String[] anteriores = texto.split(",");

                    boolean listas = true;
                    double mayorTT = 0;

                    // Buscar todas las prodesesoras 

                    for (String anterior : anteriores) {

                        String id = anterior.trim();
                        int indice = -1;

                        for (int j = 0; j < n; j++) {

                            if (actividades[j].trim().equalsIgnoreCase(id)) {
                                indice = j;
                                break;
                            }
                        }

                        // si existe la actividad como por ejemplo el usuario al prisipio definio 3 
                        // actividades y sus prodesesoras acompletan dos tiene que cumplir con esas dos

                        if (indice == -1) {

                            System.out.println("\nError: no existe la actividad " + id);

                            errorPredecesora = true;
                            break;
                        }

                        // Verificar si calculo TT osea la suma

                        if (!calculada[indice]) {

                            listas = false;

                        } else {

                            mayorTT = Math.max(mayorTT, tt[indice]);
                        }
                    }

                    if (errorPredecesora) {
                        break;
                    }

                    // Calcular que las predesesaoras esten listas en este caso todas deben de llevar 
                    // su orden al sumar en ese caso casi no importa por que es igual ala suma de los numeros

                    if (listas) {

                        it[i] = mayorTT;
                        tt[i] = it[i] + te[i];

                        calculada[i] = true;
                        procesadas++;
                        huboProgreso = true;
                    }
                }
            }
            if (errorPredecesora || procesadas < n) {
            System.out.println("\nError en las predecesoras o ciclo detectado. No se puede continuar.");
            scanner.close();
            return;
        }
           double duracionProyecto = 0;
        for (int i = 0; i < n; i++) {
            if (tt[i] > duracionProyecto) {
                duracionProyecto = tt[i];
            }
        }
        for (int i = 0; i < n; i++) {
            ft[i] = duracionProyecto;
        }
        for (int i = n - 1; i >= 0; i--) {
            // Buscamos si la actividad i es predecesora de otras
            double minFT = Double.MAX_VALUE;
            boolean tieneSucesores = false;

            for (int j = 0; j < n; j++) {
                String texto = predecesoras[j].trim();
                if (!texto.isEmpty() && !texto.equalsIgnoreCase("ninguna")) {
                    String[] preds = texto.split(",");
                    for (String p : preds) {
                        if (p.trim().equalsIgnoreCase(actividades[i])) {
                            tieneSucesores = true;
                            // El FT de la actividad actual es el mínimo de los IT_ardios de sus sucesoras
                            minFT = Math.min(minFT, itArdios[j]);
                        }
                    }
                }
            }

            if (tieneSucesores) {
                ft[i] = minFT;
            }
            
            itArdios[i] = ft[i] - te[i];
            holgura[i] = itArdios[i] - it[i];
            
            // Corrección por errores decimales de precisión en ceros flotantes
            if (Math.abs(holgura[i]) < 0.0001) {
                holgura[i] = 0;
            }
        }

        // Tercer tabla: IT TT(solo es la suma entre las predesesoras)

        if (procesadas == n && !errorPredecesora) {

            System.out.println("\n================================================");
            System.out.println("CALCULO DE IT Y TT");
            System.out.println("================================================");
            System.out.printf("%-6s %-8s %-8s %-8s %-8s %-8s %-10s %-15s%n",
                "ACT", "Te", "IT (ES)", "TT (EF)", "FT (LF)", "LS (IT)", "Holgura", "Crítica?");
            double varianzaRutaCritica = 0;

        for (int i = 0; i < n; i++) {
            boolean esCritica = (holgura[i] == 0);
            if (esCritica) {
                varianzaRutaCritica += varianza[i];
            }

            System.out.printf("%-6s %-8.2f %-8.2f %-8.2f %-8.2f %-8.2f %-10.2f %-15s%n",
                    actividades[i],
                    te[i],
                    it[i],
                    tt[i],
                    ft[i],
                    itArdios[i],
                    holgura[i],
                    esCritica ? "SÍ (Crítica)" : "No");
        }
System.out.println("=========================================================================================");
        System.out.printf("Duración estimada total del proyecto: %.2f %s\n", duracionProyecto, unidadTiempo);
        System.out.printf("Varianza total de la ruta crítica: %.4f\n", varianzaRutaCritica);
        double desviacionEstandarProyecto = Math.sqrt(varianzaRutaCritica);
        System.out.printf("Desviación estándar del proyecto (σ): %.4f\n", desviacionEstandarProyecto);

        // CÁLCULO DE PROBABILIDAD (Z)
        System.out.println("\n--- CÁLCULO DE PROBABILIDAD DE PLAZO (Z) ---");
        System.out.print("¿Deseas calcular la probabilidad para un tiempo de entrega específico? (s/n): ");
        char respuesta = scanner.next().charAt(0);

        if (respuesta == 's' || respuesta == 'S') {
            System.out.print("Ingresa el tiempo objetivo (plazo límite deseado): ");
            double tiempoObjetivo = scanner.nextDouble();

            // Fórmula de Z = (X - Media) / Desviación Estándar
            double z = (tiempoObjetivo - duracionProyecto) / desviacionEstandarProyecto;
            System.out.printf("Valor Z calculado: %.4f\n", z);
            System.out.println("Nota: Busca este valor Z en tu tabla de distribución normal estándar (tabla Z) para obtener el porcentaje de probabilidad acumulada.");
        }

        scanner.close();
    }
}}}