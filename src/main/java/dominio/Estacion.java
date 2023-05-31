package dominio;

public class Estacion implements Comparable<Estacion> {
    private String codigo;
    private String nombre;
    public Estacion (String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }
    public Estacion (String codigo) {
        this.codigo = codigo;
    }
    @Override
    public int compareTo(Estacion o) {
        return 0;
    }

    @Override
    public String toString() {
        return codigo + ";" + nombre;
    }

    @Override
    public boolean equals(Object obj) {
        Estacion otro = (Estacion) obj;
        return codigo.equals(otro.codigo);
    }

    public boolean equals(String otroCodigo) {
        return codigo.equals(otroCodigo);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
