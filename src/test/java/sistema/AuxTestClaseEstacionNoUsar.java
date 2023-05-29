package sistema;

public class AuxTestClaseEstacionNoUsar {
    private String codigo;
    private String nombre;

    public AuxTestClaseEstacionNoUsar(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return String.format("%s;%s", codigo, nombre);
    }

    public static AuxTestClaseEstacionNoUsar estacion(String codigo, String nombre) {
        return new AuxTestClaseEstacionNoUsar(codigo, nombre);
    }
}
