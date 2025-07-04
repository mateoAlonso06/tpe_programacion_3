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
     * Al comienzo tenemos n maquinas y se comienza poniendo en funcionamiento la primera disponible y vamos probando todos los posibles estados:
     * guardando asi la cantidad de estados generados y la cantidad de piezas producidas hasta ese momento y permitiendonos encontrar una poda en los casos en los que la cantidad de piezas
     * generadas hasta ese momento más la que pueda producir la maquina seleccionada se pasa de la cantidad de piezas totales a producir (12 en este caso)
     * y evitando asi generar estadoos innecesarios. Esto en el arbol se ve graficado con una x en los casos de poda.
     *
     * Ademas a la hora de armar el árbol, el algoritmo, una vez selecciona y pone en funcionamiento una maquina, prueba todos los estados posibles con ella, haciendo que
     * luego, no se formen estados/permutaciones redundantes (Como por ejemplo, la secuencia: [M1, M3, M4] es la misma que la secuencia: [M3,M4,M1]).
     *
     * A la hora de encontrar una posible solución (cantidad de piezas generadas = 12), en el caso de ser la primer opción, esta quedara guardada como la mejor, pero en el
     * caso de encontrar mas posibles estados solucion, el algoritmo compara la cantidad de piezas puestas en funcionamiento, guardando la que menor cantidad de maquinas
     * en funcionamiento tenga. En caso de tener una solución con mayor o igual cantidad de maquinas en funcionamiento, quedara la solucion ya guardada.
     *
     * Posibles estados solución:
     * [M1, M3, M4]
     * [M3, M3, M3]
     * [M1, M2, M4,M4]
     * [M1, M1, M1, M1, M1, M1, M1, M1, M1, M1, M1, M1,]
     *
     * Estados podados:
     * [M1, M1]
     * [M1,M2, M3]
     * [M1, M3,M3]

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
        return maquinasEnFuncionamiento.size();
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
        }
        else {
            for (int i = comienzo; i < this.maquinasDisponibles.size(); i++) {
                Maquina m = maquinasDisponibles.get(i);

                int actuales = e.getCantidadPiezasProducidas();
                int sumaConEstaMaquina = actuales + m.getCapacidadDeProduccion();
                int usadasHastaAhora = e.cantidadMaquinasEnFuncionamiento();

                boolean puedoMejorarEnNumero = maquinasEnFuncionamiento.isEmpty()
                        || (usadasHastaAhora + 1 < maquinasEnFuncionamiento.size());

                if (sumaConEstaMaquina <= cantidadPiezas && puedoMejorarEnNumero) {
                    e.ponerEnFuncionamiento(m);
                    backtracking(e, i);
                    e.quitarDeFuncionamiento(m);
                }
            }
        }
    }

    public int getEstadosGenerados() {
        return this.estadosGenerados;
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
