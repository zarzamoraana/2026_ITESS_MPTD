
package pkg2026_ti201_mptd;

import java.util.Scanner;


public class DiagramaPert {
    public static void main (String args []){
        Scanner scanner  = new Scanner (System.in);
        
        System.out.println("DIAGRAMA DE PERT :D");
           
        //Se le pide al usuario que especifique si se trabajara con dias, semanas o meses los valores OMP 
        System.out.print("¿Trabajaremos con dias, semanas o meses?");
        String unidadTiempo = scanner.nextLine(); // captura si se trabajara con dias, semanas o meses.
        
        //El usuario debe de ingresar el numero total de actividades
        System.out.print("¿Cuantas actividades tenemos? ");
        int n = scanner.nextInt();
        scanner.nextLine();
        
        //arreglos para las columnas definidas + arreglo para la columna de tiempo esperado 
        String[] actividades = new String[n];
        String[] descripciones = new String[n]; 
        String[] predecesoras = new String[n];
        int[] to = new int[n]; //TIEMPO OPTIMISTA
        int[] tm = new int[n]; // TIEMPO PROBABLE
        int[] tp = new int[n]; // TIEMPO PESIMISTA 
        int[] te = new int[n]; // TIEMPO ESPERADO (EL QUE SE VA A CALCULAR)
        
        //CAPTURA DE DATOS
        for (int i = 0; i < n; i++) { // Bucle que se repetira dependiendo de cuantas actividades ingrese el usuario 
        System.out.println("\n--- Actividad " + (i + 1) + " ---");
        
        //Asignacion de letras a las actividades
        System.out.print("Identificador (ej. A, B, c): ");
            actividades[i] = scanner.nextLine();
            
        System.out.print("Describe brevemente de que tratan las actividades: ");
            descripciones[i] = scanner.nextLine();
            
        System.out.print("Predecesoras ( escribelas con , o ninguna (para la primera actividad) :v ): ");
            predecesoras[i] = scanner.nextLine();
            
            // Captura los 3 tiempos
            
            System.out.println("Ingresa los valores de los tiempos :D");
            
            System.out.print("Tiempo optimista:");
            to[i] = scanner.nextInt();

            System.out.print("Tiempo más probable:");
            tm[i] = scanner.nextInt();

            System.out.print("Tiempo pesimista:");
            tp[i] = scanner.nextInt();
            scanner.nextLine();
            
            //formula para calcular el tiempo esperado
            te[i] = (int) ((to[i] + (4 * tm[i]) + tp[i]) / 6.0);
        }
        
        // Mostrar resumen incluyendo la descripción
        System.out.println("\n============================================================");
        System.out.println("RESUMEN DE TIEMPOS (Unidad: " + unidadTiempo + ")");
        System.out.println("============================================================");
        System.out.println("ID\tDescripción\t\tPredecesoras\tTe");
        for (int i = 0; i < n; i++) {
            System.out.println(actividades[i] + "\t" + descripciones[i] + "\t\t" + predecesoras[i] + "\t\t" + String.format("%d", te[i]));
        }

        scanner.close();
                      
    }
    
}

