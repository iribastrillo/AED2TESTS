package interfaz;

import java.util.Arrays;
import java.util.Objects;

public enum Nacionalidad {

    Francia(0, "FR"),
    Alemania(1, "DE"),
    ReinoUnido(2, "UK"),
    Espania(3, "ES"),
    Otro(4, "OT");

    private final int indice;
    private final String codigo;

    Nacionalidad(int indice, String codigo) {
        this.indice = indice;
        this.codigo = codigo;
    }

    public int getIndice() {
        return indice;
    }

    public String getCodigo() {
        return codigo;
    }

    public static Nacionalidad fromCodigo(String codigo) {
        return Arrays.stream(Nacionalidad.values())
                .filter(a -> Objects.equals(a.codigo, codigo))
                .findFirst()
                .orElse(null);
    }
}
