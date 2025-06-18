import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1) Archivo en la raíz del proyecto
        String rutaFichero = "maquinas.txt";

        // 2) Estructuras donde guardar la info
        int cantidadPiezasObjetivo = 0;
        List<Maquina> maquinas = new ArrayList<>();

        // 3) Lectura y parsing
        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            // 3.1 – Primera línea: objetivo de piezas
            String linea = br.readLine();
            if (linea != null) {
                cantidadPiezasObjetivo = Integer.parseInt(linea.trim());
            } else {
                System.err.println("El fichero está vacío");
                return;
            }

            // 3.2 – Líneas restantes: "Nombre,Capacidad"
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    System.err.println("Línea inválida: " + linea);
                    continue;
                }
                String nombre = partes[0].trim();
                int capacidad = Integer.parseInt(partes[1].trim());

                maquinas.add(new Maquina(capacidad, nombre));
            }
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Error leyendo/parsing fichero: " + ex.getMessage());
            return;
        }

        SolucionBacktracking solucionBacktracking = new SolucionBacktracking(maquinas, cantidadPiezasObjetivo);
        System.out.println("Backtracking");
        System.out.println("Secuencia de maquinas: " + solucionBacktracking.getSecuenciaMaquinas());
        System.out.print("Cantidad de piezas producidas: " + solucionBacktracking.getCantidadPiezasProducidas() + "\t"); // preguntar si esto hacerlo de forma local en cada solucion
        System.out.println("Cantidad de maquinas usadas: " + solucionBacktracking.getCantidadMaquinasUsadas());
        System.out.println("Estados generados: " + solucionBacktracking.getEstadosGenerados());

        System.out.println();

        SolucionGreedy resolucionGreedy = new SolucionGreedy(maquinas, cantidadPiezasObjetivo);
        Solucion solucion = resolucionGreedy.getSolucion();
        System.out.println("Greedy");
        System.out.println("Secuencia de maquinas: " + solucion.getSecuenciaMaquinas());
        System.out.print("Cantidad de piezas producidas: " + solucion.getCantidadPiezasProducidas() + "\t");
        System.out.println("Cantidad de maquinas usadas: " + solucion.getCantidadMaquinasUsadas());
        System.out.println("Cantidad de candidatos considerados: " + resolucionGreedy.getCantidadCandidatos());
    }
}
