package sistema;

import interfaz.Nacionalidad;
import org.junit.jupiter.api.Assertions;

import java.util.Locale;

public class AuxTestPasajeroNoUsar {
    private final Nacionalidad nacionalidad;
    private final int numeroId;

    private final String nombre;
    private final int edad;

    private final int profundidadEsperada;

    public AuxTestPasajeroNoUsar(Nacionalidad nacionalidad, int numeroId, String nombre, int edad) {
        this.nacionalidad = nacionalidad;
        this.numeroId = numeroId;
        this.nombre = nombre;
        this.edad = edad;
        this.profundidadEsperada = -1;
    }

    public AuxTestPasajeroNoUsar(Nacionalidad nacionalidad, int numeroId, String nombre, int edad, int profundidadEsperada) {
        this.nacionalidad = nacionalidad;
        this.numeroId = numeroId;
        this.nombre = nombre;
        this.edad = edad;
        this.profundidadEsperada = profundidadEsperada;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public int getNumeroId() {
        return numeroId;
    }

    public String getNombre() {
        return new String(nombre);
    }

    public int getEdad() {
        return edad;
    }

    public int getProfundidadEsperada() {
        return profundidadEsperada;
    }

    public String getIdentificador() {
        return getIdentificador(nacionalidad, numeroId);
    }

    public static String getIdentificador(Nacionalidad nacionalidad, int numeroId) {

        return String.format(Locale.GERMAN, "%s%,d#%d", nacionalidad.getCodigo(), numeroId / 10, numeroId % 10);
    }

    public static String getIdentificadorValido(Nacionalidad nacionalidad, int numeroId) {
        Assertions.assertTrue(numeroId >= 100_000_0 && numeroId <= 9_999_999_9);
        return String.format(Locale.GERMAN, "%s%,d#%d", nacionalidad.getCodigo(), numeroId / 10, numeroId % 10);
    }

    public static AuxTestPasajeroNoUsar pasajero(Nacionalidad nacionalidad, int numeroId, String nombre, int edad, int profundidadEsperada) {
        return new AuxTestPasajeroNoUsar(nacionalidad, numeroId, nombre, edad, profundidadEsperada);
    }

    public String toString() {
        return String.format("%s;%s;%s;%s", getIdentificador(), getNombre(), getEdad(), getNacionalidad().getCodigo());
    }
}
