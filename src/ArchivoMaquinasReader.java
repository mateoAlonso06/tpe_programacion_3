import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArchivoMaquinasReader {
    private final int cantidadPiezasObjetivo;
    private final List<Maquina> maquinas;

    private ArchivoMaquinasReader(int cantidadPiezasObjetivo, List<Maquina> maquinas) {
        this.cantidadPiezasObjetivo = cantidadPiezasObjetivo;
        this.maquinas = maquinas;
    }

    public static ArchivoMaquinasReader fromFile(String rutaFichero) throws IOException {
        int cantidadPiezas = 0;
        List<Maquina> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            String linea = br.readLine();
            if (linea == null) {
                throw new IOException("El fichero está vacío");
            }
            cantidadPiezas = Integer.parseInt(linea.trim());

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    System.err.println("Línea inválida, se omite: " + linea);
                    continue;
                }
                String nombre = partes[0].trim();
                int capacidad = Integer.parseInt(partes[1].trim());
                lista.add(new Maquina(capacidad, nombre));
            }
        } catch (NumberFormatException e) {
            throw new IOException("Error al convertir número: " + e.getMessage(), e);
        }

        return new ArchivoMaquinasReader(cantidadPiezas, lista);
    }

    public int getCantidadPiezasObjetivo() {
        return cantidadPiezasObjetivo;
    }

    public List<Maquina> getMaquinas() {
        return maquinas;
    }
}
