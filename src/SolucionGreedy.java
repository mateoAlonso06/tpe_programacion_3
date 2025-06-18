import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolucionGreedy {
    private List<Maquina> maquinasDisponibles;
    private int cantidadPiezas;

    public SolucionGreedy(List<Maquina> maquinasDisponibles, int cantidadPiezas) {
        this.maquinasDisponibles = maquinasDisponibles;
        this.cantidadPiezas = cantidadPiezas;
    }

    // TODO : poner estrategia y comentarios solicitados

    public Solucion getSolucion() {
        return greedy();
    }

    public int getCantidadCandidatos() {
        return this.maquinasDisponibles.size();
    }

    private Solucion greedy() {
        Solucion solucion = new Solucion();

        // Ordena por capacidad de produccion de forma descendente
        List<Maquina> maquinas = new ArrayList<>(maquinasDisponibles);
        Collections.sort(maquinas);

        int indice = 0;
        while (indice < maquinas.size() && !esSolucion(solucion)) {
            Maquina m = maquinas.get(indice);

            int nuevaSuma = solucion.getCantidadPiezasProducidas() + m.getCapacidadDeProduccion();

            if (nuevaSuma <= cantidadPiezas) {
                solucion.addMaquina(m);
            } else {
                indice++;
            }
        }
        if (esSolucion(solucion)) {
            return solucion;
        } else {
            return null;
        }
    }

    private boolean esSolucion(Solucion solucion) {
        return solucion.getCantidadPiezasProducidas() == cantidadPiezas;
    }
}

class Solucion {
    private List<Maquina> secuenciaMaquinas;
    private int cantidadPiezasProducidas;

    public Solucion() {
        this.secuenciaMaquinas = new ArrayList<>();
        this.cantidadPiezasProducidas = 0;
    }

    public List<Maquina> getSecuenciaMaquinas() {
        return new ArrayList<>(secuenciaMaquinas);
    }

    public void addMaquina(Maquina m) {
        this.secuenciaMaquinas.add(m);
        this.cantidadPiezasProducidas+= m.getCapacidadDeProduccion();
    }

    public int getCantidadPiezasProducidas() {
        return this.cantidadPiezasProducidas;
    }
}
