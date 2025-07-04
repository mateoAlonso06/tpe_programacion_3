import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String rutaFichero = "maquinas.txt";

        ArchivoMaquinasReader reader;
        try {
            reader = ArchivoMaquinasReader.fromFile(rutaFichero);
        } catch (IOException ex) {
            System.err.println("No se pudo leer el fichero: " + ex.getMessage());
            return;
        }

        int cantidadPiezasObjetivo = reader.getCantidadPiezasObjetivo();
        List<Maquina> maquinas = reader.getMaquinas();

        // Prueba con backtracking
        SolucionBacktracking sb = new SolucionBacktracking(maquinas, cantidadPiezasObjetivo);
        System.out.println("Backtracking");
        System.out.println("Secuencia de máquinas: " + sb.getSecuenciaMaquinas());
        System.out.println("Piezas producidas: " + sb.getCantidadPiezasProducidas()
                           + "   Puestas en marcha: " + sb.getCantidadMaquinasUsadas()
                           + "   Estados generados: " + sb.getEstadosGenerados());
        
        System.out.println();

        // Prueba con greedy
        SolucionGreedy sg = new SolucionGreedy(maquinas, cantidadPiezasObjetivo);
        Solucion sol = sg.getSolucion();

        System.out.println("Greedy");
        if (sol != null) {
            System.out.println("Secuencia de máquinas: " + sol.getSecuenciaMaquinas());
            System.out.println("Piezas producidas: " + sol.getCantidadPiezasProducidas()
                    + "   Puestas en marcha: " + sol.getCantidadMaquinasUsadas()
                    + "   Candidatos considerados: " + sg.getCantidadCandidatos());
        } else {
            System.out.println("No existe solucion Greedy");
        }
    }
}
