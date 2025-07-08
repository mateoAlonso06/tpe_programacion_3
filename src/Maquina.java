import java.util.Objects;

public class Maquina implements Comparable<Maquina> {
    private int capacidadDeProduccion;
    private String nombre;

    public Maquina(int capacidadDeProduccion, String nombre) {
        this.capacidadDeProduccion = capacidadDeProduccion;
        this.nombre = nombre;
    }

    public void setCapacidadDeProduccion(int capacidadDeProduccion) {
        this.capacidadDeProduccion = capacidadDeProduccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadDeProduccion() {
        return this.capacidadDeProduccion;
    }

    public String getNombre() {
        return this.nombre;
    }

    @Override
    public String toString() {
        return this.getNombre() + "(" + this.getCapacidadDeProduccion()+")";
    }

    @Override
    public int compareTo(Maquina o) {
        return Integer.compare(o.getCapacidadDeProduccion(), this.getCapacidadDeProduccion());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Maquina maquina = (Maquina) o;
        return capacidadDeProduccion == maquina.capacidadDeProduccion && Objects.equals(nombre, maquina.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacidadDeProduccion, nombre);
    }
}
