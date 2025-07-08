import java.util.*;

public class SolucionGreedy {
    private List<Maquina> maquinasDisponibles;
    private int cantidadPiezas;

    public SolucionGreedy(List<Maquina> maquinasDisponibles, int cantidadPiezas) {
        this.maquinasDisponibles = maquinasDisponibles;
        this.cantidadPiezas = cantidadPiezas;
    }
/*
    Estrategia Greedy para producción exacta de piezas:*
    Candidatos:
            · Todas las máquinas disponibles, cada una con una capacidad fija de producción.*
    Estrategia de selección:
            Ordenar la lista de máquinas de mayor a menor capacidad.

    Recorrerla desde la de mayor capacidad a la de menor:
            • Si la máquina cabe en la suma de piezas fabricadas (suma_actual + capacidad ≤ objetivo),
              la añadimos a la solución y volvemos a intentar con la misma máquina
              (permitiendo reutilización ilimitada).
            • Si no cabe, avanzamos al siguiente candidato de menor capacidad.*

    Criterio de parada:
            · Se detiene al alcanzar exactamente la cantidad de piezas deseada.
            · O al agotar los candidatos sin poder llegar al objetivo.*

    Consideraciones:
            · Complejidad: O(n log n) por la ordenación + O(n) en el bucle de selección.
            · No garantiza encontrar la solución con menor número de máquinas
              salvo que las capacidades formen un estado solución completamente óptimo.
              teniendo en cuenta que, el algoritmo decide de manera local, esperando encontrar una solución globalmente óptima.
            · Devuelve null si no se encuentra combinación exacta.

*/
    public Solucion getSolucion() {
        // TODO: ¿HACER IF DE COMPROBACION Y DEVOLVER?
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

    public int getCantidadMaquinasUsadas() {
        return secuenciaMaquinas.size();
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
