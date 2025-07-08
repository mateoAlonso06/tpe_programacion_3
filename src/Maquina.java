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
<<<<<<< HEAD
        return this.getNombre() + "(" + this.getCapacidadDeProduccion()+")";
=======
        return this.getNombre() + ", " + this.getCapacidadDeProduccion();
>>>>>>> 74759ed1da6e4e0f6aad093e57889d92148ff932
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
