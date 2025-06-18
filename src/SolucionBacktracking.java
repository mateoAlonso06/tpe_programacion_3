import java.util.*;

public class SolucionBacktracking {
    private final List<Maquina> maquinasDisponibles;
    private final List<Maquina> maquinasEnFuncionamiento;
    private final int cantidadPiezas;
    private int estadosGenerados;
    private int cantidadPiezasProducidas;

    public SolucionBacktracking(List<Maquina> maquinasDisponibles, int cantidadPiezas) {
        this.maquinasDisponibles = maquinasDisponibles;
        this.cantidadPiezas = cantidadPiezas;
        this.maquinasEnFuncionamiento = new ArrayList<>();
        this.estadosGenerados = 0;
        this.cantidadPiezasProducidas = 0;
    }

    /*
     * Estrategia Backtracking:
     * Al comienzo tenemos n maquinas y se comienza poniendo en funcionamiento la primera disponible y vamos probando todos los posibles estados
     * guardando asi la cantidad de piezas producidas hasta ese momento y permitiendonos encontrar una poda en los casos en los que la cantidad de piezas
     * generadas hasta ese momento más la que pueda producir la maquina seleccionada se pasa de la cantidad de piezas totales a producir (12 en este caso)
     * y evitando asi generar estadoos innecesarios
     *
     *
     *
     * Estrategia Backtracking para producción exacta de piezas:
     *
     *  - Generación del árbol de búsqueda:
     *     · A la hora de  probar añadir una máquina de una lista de maquinas disponibles a partir del índice ‘start’, lo que evita permutaciones redundantes.
     *
     * - Estado:
     *     · Secuencia de máquinas en funcionamiento.
     *     · Suma actual de piezas producidas.
     *
     * - Caso solución:
     *     · La suma de piezas producidas == objetivo (p. ej. 12 piezas).
     *
     * - Podas:
     *     1) Exceso de piezas: si suma_actual + capacidad_máquina > objetivo, se descarta
     *        esa rama (continue).
     *     2) Profundidad no óptima: si número_de_máquinas_usadas + 1 ≥ mejor_solución_conocida,
     *        se omite la rama para no empeorar la mejor solución.
     *
     * - Optimización adicional:
     *     · Se almacena en todo momento la mejor solución (la que usa menos máquinas) y
     *       se compara al encontrar cada solución candidata.
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *  - Estado: (secuencia de máquinas, suma_actual)
     *  - Genero hijos probando cada máquina M:
     *      si suma_actual + M.piezas <= total:
     *         agrego M a la secuencia y llamo recursivo
     *  - Estado solución: suma_actual == total
     *  - Podas:
     *      * exceso de piezas
     *      * profundidad >= mejor_solución_existente (para optimizar)
     */

    public List<Maquina> getSecuenciaMaquinas() {
        Estado e = new Estado();
        backtracking(e, 0);
        return this.maquinasEnFuncionamiento;
    }

    public int getCantidadMaquinasUsadas() {
        Set<Maquina> maquinas = new HashSet<>(getSecuenciaMaquinas());
        return maquinas.size();
    }

    public int getCantidadPiezasProducidas() {
        return this.cantidadPiezasProducidas;
    }

    private void backtracking(Estado e, int comienzo) {
        estadosGenerados++;
        if (e.getCantidadPiezasProducidas() == this.cantidadPiezas) {
            if (esSolucion(e)) {
                this.maquinasEnFuncionamiento.clear();
                this.maquinasEnFuncionamiento.addAll(e.getMaquinasEnFuncionamientoActual());
                this.cantidadPiezasProducidas = e.getCantidadPiezasProducidas();
            }
            return;
        }

        for (int i = comienzo; i < this.maquinasDisponibles.size(); i++) {
            Maquina m = maquinasDisponibles.get(i);
            if (e.getCantidadPiezasProducidas() + m.getCapacidadDeProduccion() > cantidadPiezas) {
                continue;
            }

            e.ponerEnFuncionamiento(m);
            backtracking(e, i);
            e.quitarDeFuncionamiento(m);
        }
    }

    public int getEstadosGenerados() {
        return estadosGenerados;
    }

    private boolean esSolucion(Estado e) {
        return maquinasEnFuncionamiento.isEmpty() || e.cantidadMaquinasEnFuncionamiento() < maquinasEnFuncionamiento.size();
    }
}

class Estado {
    private List<Maquina> maquinasEnFuncionamientoActual;
    private int piezasProducidas;

    public Estado() {
        this.maquinasEnFuncionamientoActual = new ArrayList<>();
        this.piezasProducidas = 0;
    }

    public int cantidadMaquinasEnFuncionamiento() {
        return maquinasEnFuncionamientoActual.size();
    }

    public void ponerEnFuncionamiento(Maquina m) {
        maquinasEnFuncionamientoActual.add(m);
        piezasProducidas+= m.getCapacidadDeProduccion();
    }

    public void quitarDeFuncionamiento(Maquina m) {
        maquinasEnFuncionamientoActual.remove(m);
        piezasProducidas-= m.getCapacidadDeProduccion();
    }

    public List<Maquina> getMaquinasEnFuncionamientoActual() {
        return new ArrayList<>(maquinasEnFuncionamientoActual);
    }

    public int getCantidadPiezasProducidas() {
        return this.piezasProducidas;
    }
}
