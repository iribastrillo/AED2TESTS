package sistema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static sistema.AuxAsserciones.checkearError;
import static sistema.AuxAsserciones.checkearOk;
import  interfaz.*;

/**********************************************************
 ************************ ERRORES COMUNES *****************
 * Cosas que pueden estarles pasando y que haga que los tests no funcionen
 * Orden lexicografico en vez de númerico
 * Usar '==' en vez de equals
 * Usar attributos de clase para las agregaciones, que funcionen los test no quita que esto este mal. Lo mejor es usar attributos o objetos creados para esa llamada como por ejemplo un StringBuilder.
 * Mal manejo de los pipes, es mas facil hacer un prepend del pipe (agregarlo al principio) que un append la mayoría de las veces.
 * No tener bien hecha la recursión
 **********************************************************/
public class TestFiltroPasajeros {

    private static final AuxTestPasajeroNoUsar ALEMANIA_22_JORGEN = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.Alemania, 12_000_002, "Jorgen", 22, -1);
    private static final AuxTestPasajeroNoUsar ALEMANIA_15_JORGEN = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.Alemania, 1_800_002, "Jorgen", 15, -1);
    private static final AuxTestPasajeroNoUsar ALEMANIA_60_ISOLDE = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.Alemania, 91_000_002, "Isolde", 60, -1);
    private static final AuxTestPasajeroNoUsar ALEMANIA_80_TRISTAN = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.Alemania, 1_200_002, "Tristan", 80, -1);
    private static final AuxTestPasajeroNoUsar FRANCIA_26_MARIE = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.Francia, 11_005_002, "Marie", 26, -1);
    private static final AuxTestPasajeroNoUsar ALEMANIA_26_MARIE = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.Alemania, 21_105_002, "Marie", 26, -1);
    private static final AuxTestPasajeroNoUsar UK_31_SOPHIE = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.ReinoUnido, 41_215_002, "Sophie", 26, -1);
    private static final AuxTestPasajeroNoUsar UK_80_HARRY = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.ReinoUnido, 51_225_022, "Harry", 26, -1);
    private static final AuxTestPasajeroNoUsar UK_10_HARRY = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.ReinoUnido, 61_225_222, "Harry", 10, -1);
    private static final AuxTestPasajeroNoUsar ES_55_ROBERTO = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.Espania, 11_225_222, "Roberto", 55, -1);
    private static final AuxTestPasajeroNoUsar ES_82_ALBERTA = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.Espania, 14_225_222, "Alberta", 82, -1);

    @Test
    public void filtroPasajerosOk() {
        Sistema sistema = tengoUnSistemaValido();
        Consulta consulta = Consulta.fromString("[[nombre='Jorgen' AND edad > 15] OR [nombre='Marie' AND edad>20]] AND [nacionalidad='DE']");
        //El arbol de la consulta lo pueden ver en:
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AR%20%5Blabel%3D%22And%22%5D%3B%0AR_L%20%5Blabel%3D%22Or%22%5D%3B%0AR%20-%3E%20R_L%3B%0AR_L_L%20%5Blabel%3D%22And%22%5D%3B%0AR_L%20-%3E%20R_L_L%3B%0AR_L_L_L%20%5Blabel%3D%22NombreIgual%20%5BJorgen%5D%22%5D%3B%0AR_L_L%20-%3E%20R_L_L_L%3B%0AR_L_L_R%20%5Blabel%3D%22EdadMayor%20%5B15%5D%22%5D%3B%0AR_L_L%20-%3E%20R_L_L_R%3B%0AR_L_R%20%5Blabel%3D%22And%22%5D%3B%0AR_L%20-%3E%20R_L_R%3B%0AR_L_R_L%20%5Blabel%3D%22NombreIgual%20%5BMarie%5D%22%5D%3B%0AR_L_R%20-%3E%20R_L_R_L%3B%0AR_L_R_R%20%5Blabel%3D%22EdadMayor%20%5B20%5D%22%5D%3B%0AR_L_R%20-%3E%20R_L_R_R%3B%0AR_R%20%5Blabel%3D%22Nacionalidad%20%5BAlemania%5D%22%5D%3B%0AR%20-%3E%20R_R%3B%0A%7D
        verificarFiltroPasajeros(sistema, consulta);
        registrarTodosLosPasajeros(sistema);
        verificarFiltroPasajeros(sistema, consulta, ALEMANIA_22_JORGEN, ALEMANIA_26_MARIE);
        sistema.inicializarSistema(20);//reinicio el sistema
        registrarTodosLosPasajeros(sistema);
        //el resultado tiene que ser el mismo
        verificarFiltroPasajeros(sistema, consulta, ALEMANIA_22_JORGEN, ALEMANIA_26_MARIE);
        verificarFiltroPasajeros(sistema, Consulta.fromString("[nacionalidad='DE'] OR [edad>50]"),
                ALEMANIA_15_JORGEN, ALEMANIA_60_ISOLDE, ALEMANIA_80_TRISTAN,
                ALEMANIA_22_JORGEN, ALEMANIA_26_MARIE, ES_55_ROBERTO, ES_82_ALBERTA);
        verificarFiltroPasajeros(sistema, Consulta.fromString("nacionalidad='ES'"),
                ES_82_ALBERTA, ES_55_ROBERTO);
    }

    @Test
    public void filtroPasajerosError1() {
        Sistema sis = tengoUnSistemaValido();
        checkearError(Retorno.Resultado.ERROR_1, sis.filtrarPasajeros(null), "Cuando es vacio tiene que devolver error 1");
    }

    @Test
    public void filtroPasajerosOkConDatosRandom() {

        //NO traten de depurar esto porque agrega una cantidad inmanejable. Tienen que funcionar los otros test y sino buscar donde esta el error.
        Sistema sistema = tengoUnSistemaValido();
        Consulta consulta = Consulta.fromString("[[nombre='Jorgen' AND edad > 15] OR [nombre='Marie' AND edad>20]] AND [nacionalidad='DE']");
        Random r = new Random(230_222);
        int cantidadAgregadosCorrectamente = 0;
        String[] nombresASeleccionar = new String[]{"Sofia", "Aquiles", "Pablo", "Federico", "Pia", "Paula", "Alvaro"};
        String[] apellidos = new String[]{"Perez", "Rodriguez", "Fernandez", "Muller", "Murphy", "Prost"};

        int[][] nombresApellidosCorrectos = new int[nombresASeleccionar.length][apellidos.length];

        AuxTestPasajeroNoUsar[] pasajerosAgregados = new AuxTestPasajeroNoUsar[10_000];
        AuxTestPasajeroNoUsar[] pasajerosConNombreAquilesProst = new AuxTestPasajeroNoUsar[10_000];
        int idx2 = 0;
        for (int i = 0; i < 10_000; i++) {
            int idxNom = r.nextInt(nombresASeleccionar.length);
            int idxApellido = r.nextInt(apellidos.length);
            int edad = r.nextInt(40) + 10;
            String nombreCompleto = nombresASeleccionar[idxNom] + " " + apellidos[idxApellido];
            AuxTestPasajeroNoUsar pasajero = AuxTestPasajeroNoUsar.pasajero(Nacionalidad.Otro, r.nextInt(10_000) + 1_000_000,
                    nombreCompleto, edad, -1);
            //System.out.println(sistema.registrarPasajero(pasajero.getIdentificador(), pasajero.getNombre(), pasajero.getEdad()).getResultado());
            if (sistema.registrarPasajero(pasajero.getIdentificador(), pasajero.getNombre(), pasajero.getEdad()).isOk()) {
                pasajerosAgregados[cantidadAgregadosCorrectamente] = pasajero;
                cantidadAgregadosCorrectamente++;
                nombresApellidosCorrectos[idxNom][idxApellido]++;
                if (pasajero.getNombre().equals("Aquiles Prost")) {
                    pasajerosConNombreAquilesProst[idx2++] = pasajero;
                }

            }
        }
        Logger.getLogger(TestFiltroPasajeros.class.getName()).info("Agregamos correctamente " + cantidadAgregadosCorrectamente + " pasajeros");
        //Todo lo que agregamos no debería afectar a las consultas anteriores
        //El arbol de la consulta lo pueden ver en:
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AR%20%5Blabel%3D%22And%22%5D%3B%0AR_L%20%5Blabel%3D%22Or%22%5D%3B%0AR%20-%3E%20R_L%3B%0AR_L_L%20%5Blabel%3D%22And%22%5D%3B%0AR_L%20-%3E%20R_L_L%3B%0AR_L_L_L%20%5Blabel%3D%22NombreIgual%20%5BJorgen%5D%22%5D%3B%0AR_L_L%20-%3E%20R_L_L_L%3B%0AR_L_L_R%20%5Blabel%3D%22EdadMayor%20%5B15%5D%22%5D%3B%0AR_L_L%20-%3E%20R_L_L_R%3B%0AR_L_R%20%5Blabel%3D%22And%22%5D%3B%0AR_L%20-%3E%20R_L_R%3B%0AR_L_R_L%20%5Blabel%3D%22NombreIgual%20%5BMarie%5D%22%5D%3B%0AR_L_R%20-%3E%20R_L_R_L%3B%0AR_L_R_R%20%5Blabel%3D%22EdadMayor%20%5B20%5D%22%5D%3B%0AR_L_R%20-%3E%20R_L_R_R%3B%0AR_R%20%5Blabel%3D%22Nacionalidad%20%5BAlemania%5D%22%5D%3B%0AR%20-%3E%20R_R%3B%0A%7D
        verificarFiltroPasajeros(sistema, consulta);
        registrarTodosLosPasajeros(sistema);
        verificarFiltroPasajeros(sistema, consulta, ALEMANIA_22_JORGEN, ALEMANIA_26_MARIE);
        verificarFiltroPasajeros(sistema, Consulta.fromString("[nacionalidad='DE'] OR [edad>50]"),
                ALEMANIA_15_JORGEN, ALEMANIA_60_ISOLDE, ALEMANIA_80_TRISTAN,
                ALEMANIA_22_JORGEN, ALEMANIA_26_MARIE, ES_55_ROBERTO, ES_82_ALBERTA);
        verificarFiltroPasajeros(sistema, Consulta.fromString("nacionalidad='ES'"),
                ES_82_ALBERTA, ES_55_ROBERTO);
        verificarFiltroPasajeros(sistema, Consulta.fromString("nacionalidad='OT'"),
                pasajerosAgregados);
        verificarFiltroPasajeros(sistema, Consulta.fromString("nacionalidad='OT' AND nombre='Aquiles Prost'"),
                pasajerosConNombreAquilesProst);
        verificarFiltroPasajeros(sistema, Consulta.fromString("nacionalidad='UK' AND nombre='Aquiles Prost'"));
    }

    private Sistema tengoUnSistemaValidoConTodosLosPasajeros() {
        Sistema sistema = tengoUnSistemaValido();
        registrarTodosLosPasajeros(sistema);
        return sistema;
    }

    private static void registrarTodosLosPasajeros(Sistema sistema) {
        AuxAccionesPasajeros.agregoPasajerosOk(sistema, ALEMANIA_22_JORGEN, ALEMANIA_15_JORGEN, ALEMANIA_60_ISOLDE,
                ALEMANIA_80_TRISTAN, FRANCIA_26_MARIE, ALEMANIA_26_MARIE, UK_31_SOPHIE, UK_80_HARRY, UK_10_HARRY,
                ES_55_ROBERTO, ES_82_ALBERTA);
    }

    private void verificarFiltroPasajeros(Sistema sistema, Consulta consulta, AuxTestPasajeroNoUsar... pasajerosEsperados) {
        Retorno retorno = checkearOk(sistema.filtrarPasajeros(consulta), "Se esperaba que el filtro funcionara correctamente");
        Arrays.sort(pasajerosEsperados, Comparator.nullsLast(Comparator.comparing(p -> p.getNumeroId())));
        Assertions.assertEquals(Arrays.stream(pasajerosEsperados).filter(Objects::nonNull).map(v -> v.getIdentificador()).collect(Collectors.joining("|")), retorno.getValorString());
    }

    private Sistema tengoUnSistemaValido() {
        Sistema sistema = new ImplementacionSistema();
        sistema.inicializarSistema(20);
        return sistema;
    }
}
