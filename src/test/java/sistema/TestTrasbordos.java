package sistema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static sistema.AuxAccionesEstaciones.registrarConexionesOk;
import static sistema.AuxAccionesEstaciones.registroEstacionOk;
import static sistema.AuxAsserciones.checkearError;
import static sistema.AuxAsserciones.checkearOk;
import static sistema.AuxTestClaseEstacionNoUsar.estacion;
import interfaz.*;

/**********************************************************
 ************************ ERRORES COMUNES *****************
 * Cosas que pueden estarles pasando y que haga que los tests no funcionen
 * Usar '==' en vez de equals
 * No modelarlos como un multigrafo dirigido, no es lo mismo aristas[a][b] que aristas[b][a]
 * Usar attributos de clase para las agregaciones, que funcionen los test no quita que esto este mal. Lo mejor es usar attributos o objetos creados para esa llamada como por ejemplo un StringBuilder.
 * Mal manejo de los pipes, es mas facil hacer un prepend del pipe (agregarlo al principio) que un append la mayoría de las veces.
 * No usar la recorrida adecuada con el control de saltos correspondiente.
 * Que el máximo de vertices sea 100, no quiere decir que haya 100 vertices
 **********************************************************/
public class TestTrasbordos {

    private static final String MADRID_1 = "MAD001";
    private static final String MADRID_2 = "MAD002";
    private static final String PARIS_1 = "PAR001";
    private static final String PARIS_2 = "PAR002";
    private static final String MONTREAL = "MON001";
    private static final String LYON = "LYO001";
    private static final String LISBOA = "LIS001";
    private static final String LONDRES = "LON001";


    private static final AuxTestClaseEstacionNoUsar EST_MADRID_1 = estacion(MADRID_1, "MADRID_1");
    private static final AuxTestClaseEstacionNoUsar EST_MADRID_2 = estacion(MADRID_2, "MADRID_2");
    private static final AuxTestClaseEstacionNoUsar EST_PARIS_1 = estacion(PARIS_1, "PARIS_1 ");
    private static final AuxTestClaseEstacionNoUsar EST_PARIS_2 = estacion(PARIS_2, "PARIS_2 ");
    private static final AuxTestClaseEstacionNoUsar EST_MONTREAL = estacion(MONTREAL, "MONTREAL");
    private static final AuxTestClaseEstacionNoUsar EST_LYON = estacion(LYON, "LYON ");
    private static final AuxTestClaseEstacionNoUsar EST_LISBOA = estacion(LISBOA, "LISBOA");
    private static final AuxTestClaseEstacionNoUsar EST_LONDRES = estacion(LONDRES, "LONDRES ");

    private Sistema tengoUnSistemaValido() {
        Sistema sis = new ImplementacionSistema();
        sis.inicializarSistema(20);
        return sis;
    }

    public static void main(String[] args) {
        String[] estaciones = new String[]{"MADRID_1", "MADRID_2", "PARIS_1", "PARIS_2", "LISBOA", "LONDRES", "LYON", "MONTREAL"};

        int id = 1;
        for (int i = 0; i < estaciones.length; i++) {
            for (int j = 0; j < estaciones.length; j++) {
                System.out.printf("private static final TestConexionNoUsar %s_TO_%s=TestConexionNoUsar.conexion(EST_%s,EST_%s,%d);\n",
                        estaciones[i], estaciones[j], estaciones[i], estaciones[j], id++);
            }
        }
    }


    private static final AuxTestConexionNoUsar MADRID_1_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_MADRID_1, 1);
    private static final AuxTestConexionNoUsar MADRID_1_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_MADRID_2, 2);
    private static final AuxTestConexionNoUsar MADRID_1_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_PARIS_1, 3);
    private static final AuxTestConexionNoUsar MADRID_1_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_PARIS_2, 4);
    private static final AuxTestConexionNoUsar MADRID_1_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_LISBOA, 5);
    private static final AuxTestConexionNoUsar MADRID_1_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_LONDRES, 6);
    private static final AuxTestConexionNoUsar MADRID_1_TO_LYON = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_LYON, 7);
    private static final AuxTestConexionNoUsar MADRID_1_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_MONTREAL, 8);
    private static final AuxTestConexionNoUsar MADRID_2_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_MADRID_1, 9);
    private static final AuxTestConexionNoUsar MADRID_2_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_MADRID_2, 10);
    private static final AuxTestConexionNoUsar MADRID_2_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_PARIS_1, 11);
    private static final AuxTestConexionNoUsar MADRID_2_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_PARIS_2, 12);
    private static final AuxTestConexionNoUsar MADRID_2_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_LISBOA, 13);
    private static final AuxTestConexionNoUsar MADRID_2_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_LONDRES, 14);
    private static final AuxTestConexionNoUsar MADRID_2_TO_LYON = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_LYON, 15);
    private static final AuxTestConexionNoUsar MADRID_2_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_MONTREAL, 16);
    private static final AuxTestConexionNoUsar PARIS_1_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_MADRID_1, 17);
    private static final AuxTestConexionNoUsar PARIS_1_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_MADRID_2, 18);
    private static final AuxTestConexionNoUsar PARIS_1_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_PARIS_1, 19);
    private static final AuxTestConexionNoUsar PARIS_1_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_PARIS_2, 20);
    private static final AuxTestConexionNoUsar PARIS_1_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_LISBOA, 21);
    private static final AuxTestConexionNoUsar PARIS_1_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_LONDRES, 22);
    private static final AuxTestConexionNoUsar PARIS_1_TO_LYON = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_LYON, 23);
    private static final AuxTestConexionNoUsar PARIS_1_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_MONTREAL, 24);
    private static final AuxTestConexionNoUsar PARIS_2_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_MADRID_1, 25);
    private static final AuxTestConexionNoUsar PARIS_2_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_MADRID_2, 26);
    private static final AuxTestConexionNoUsar PARIS_2_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_PARIS_1, 27);
    private static final AuxTestConexionNoUsar PARIS_2_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_PARIS_2, 28);
    private static final AuxTestConexionNoUsar PARIS_2_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_LISBOA, 29);
    private static final AuxTestConexionNoUsar PARIS_2_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_LONDRES, 30);
    private static final AuxTestConexionNoUsar PARIS_2_TO_LYON = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_LYON, 31);
    private static final AuxTestConexionNoUsar PARIS_2_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_MONTREAL, 32);
    private static final AuxTestConexionNoUsar LISBOA_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_MADRID_1, 33);
    private static final AuxTestConexionNoUsar LISBOA_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_MADRID_2, 34);
    private static final AuxTestConexionNoUsar LISBOA_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_PARIS_1, 35);
    private static final AuxTestConexionNoUsar LISBOA_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_PARIS_2, 36);
    private static final AuxTestConexionNoUsar LISBOA_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_LISBOA, 37);
    private static final AuxTestConexionNoUsar LISBOA_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_LONDRES, 38);
    private static final AuxTestConexionNoUsar LISBOA_TO_LYON = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_LYON, 39);
    private static final AuxTestConexionNoUsar LISBOA_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_MONTREAL, 40);
    private static final AuxTestConexionNoUsar LONDRES_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_MADRID_1, 41);
    private static final AuxTestConexionNoUsar LONDRES_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_MADRID_2, 42);
    private static final AuxTestConexionNoUsar LONDRES_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_PARIS_1, 43);
    private static final AuxTestConexionNoUsar LONDRES_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_PARIS_2, 44);
    private static final AuxTestConexionNoUsar LONDRES_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_LISBOA, 45);
    private static final AuxTestConexionNoUsar LONDRES_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_LONDRES, 46);
    private static final AuxTestConexionNoUsar LONDRES_TO_LYON = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_LYON, 47);
    private static final AuxTestConexionNoUsar LONDRES_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_MONTREAL, 48);
    private static final AuxTestConexionNoUsar LYON_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_LYON, EST_MADRID_1, 49);
    private static final AuxTestConexionNoUsar LYON_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_LYON, EST_MADRID_2, 50);
    private static final AuxTestConexionNoUsar LYON_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_LYON, EST_PARIS_1, 51);
    private static final AuxTestConexionNoUsar LYON_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_LYON, EST_PARIS_2, 52);
    private static final AuxTestConexionNoUsar LYON_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_LYON, EST_LISBOA, 53);
    private static final AuxTestConexionNoUsar LYON_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_LYON, EST_LONDRES, 54);
    private static final AuxTestConexionNoUsar LYON_TO_LYON = AuxTestConexionNoUsar.conexion(EST_LYON, EST_LYON, 55);
    private static final AuxTestConexionNoUsar LYON_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_LYON, EST_MONTREAL, 56);
    private static final AuxTestConexionNoUsar MONTREAL_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_MADRID_1, 57);
    private static final AuxTestConexionNoUsar MONTREAL_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_MADRID_2, 58);
    private static final AuxTestConexionNoUsar MONTREAL_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_PARIS_1, 59);
    private static final AuxTestConexionNoUsar MONTREAL_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_PARIS_2, 60);
    private static final AuxTestConexionNoUsar MONTREAL_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_LISBOA, 61);
    private static final AuxTestConexionNoUsar MONTREAL_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_LONDRES, 62);
    private static final AuxTestConexionNoUsar MONTREAL_TO_LYON = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_LYON, 63);
    private static final AuxTestConexionNoUsar MONTREAL_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_MONTREAL, 64);


    @Test
    public void testTrasbordos1() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        registrarConexionesOk(sistema,
                MADRID_1_TO_MADRID_2,
                MADRID_1_TO_MADRID_1,
                MADRID_1_TO_LONDRES,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_MADRID_2
        );
        //PAra ver el grafo resultante entrar aquí.
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%22id%3A43%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22id%3A6%22%5D%3B%0AV_1-%3EV_1%5Blabel%3D%22id%3A1%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%22id%3A2%22%5D%3B%0AV_3-%3EV_2%5Blabel%3D%22id%3A18%22%5D%3B%0A%7D%0A
        //engine recomendado: circo
        chequearTrasbordoOk(sistema, EST_MADRID_1, 0);
        //Los adyacentes, ojo con el ciclo madrid1 madrid1, que no puede aparecer repetido
        chequearTrasbordoOk(sistema, EST_MADRID_1, 1, MADRID_1_TO_MADRID_2, MADRID_1_TO_LONDRES);
        chequearTrasbordoOk(sistema, EST_MADRID_1, 2, MADRID_1_TO_MADRID_2, MADRID_1_TO_LONDRES, LONDRES_TO_PARIS_1);
        //No importa que haya mil, si ya pasamos por todo el grafo debe seguir funcionando
        chequearTrasbordoOk(sistema, EST_MADRID_1, 1000, MADRID_1_TO_MADRID_2, MADRID_1_TO_LONDRES, LONDRES_TO_PARIS_1);

    }

    @Test
    public void testTrasbordos2() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        //PAra ver el grafo resultante entrar aquí.
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0A%7D%0A
        //engine recomendado: circo
        chequearTrasbordoOk(sistema, EST_PARIS_1, 0);
        chequearTrasbordoOk(sistema, EST_PARIS_1, 100);
        chequearTrasbordoOk(sistema, EST_PARIS_1, 1000);

    }

    @Test
    public void testTrasbordos3() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        registrarConexionesOk(sistema,
                PARIS_1_TO_MADRID_1,
                MADRID_1_TO_MADRID_2,
                MADRID_2_TO_LONDRES,
                LONDRES_TO_PARIS_1,
                //No importa que haya repetidas
                AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_MADRID_1, 109),
                AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_MADRID_1, 110)
        );
        //PAra ver el grafo resultante entrar aquí.
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%22id%3A43%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%22id%3A2%22%5D%3B%0AV_2-%3EV_0%5Blabel%3D%22id%3A14%22%5D%3B%0AV_3-%3EV_1%5Blabel%3D%22id%3A17%22%5D%3B%0A%7D%0A
        //Es una lista circular
        chequearTrasbordoOk(sistema, EST_PARIS_1, 0);
        chequearTrasbordoOk(sistema, EST_PARIS_1, 1, PARIS_1_TO_MADRID_1);
        chequearTrasbordoOk(sistema, EST_PARIS_1, 2, PARIS_1_TO_MADRID_1, MADRID_1_TO_MADRID_2);
        chequearTrasbordoOk(sistema, EST_PARIS_1, 3, PARIS_1_TO_MADRID_1, MADRID_1_TO_MADRID_2, MADRID_2_TO_LONDRES);
        //No importa de donde empiece deberia ver la circularidad

        chequearTrasbordoOk(sistema, EST_MADRID_2, 0);
        chequearTrasbordoOk(sistema, EST_MADRID_2, 1, MADRID_2_TO_LONDRES);
        chequearTrasbordoOk(sistema, EST_MADRID_2, 2, MADRID_2_TO_LONDRES, LONDRES_TO_PARIS_1);
        chequearTrasbordoOk(sistema, EST_MADRID_2, 3, MADRID_2_TO_LONDRES, LONDRES_TO_PARIS_1, PARIS_1_TO_MADRID_1);

    }

    @Test
    public void testTrasbordosError1() {
        checkearError(Retorno.Resultado.ERROR_1, tengoUnSistemaValido().listadoEstacionesCantTrasbordos(MADRID_1, -1), "La cant en negativa");
        checkearError(Retorno.Resultado.ERROR_1, tengoUnSistemaValido().listadoEstacionesCantTrasbordos(MADRID_1, -2323), "La cant en negativa");
    }

    @Test
    public void testTrasbordosError2() {
        checkearError(Retorno.Resultado.ERROR_2,
                tengoUnSistemaValido().listadoEstacionesCantTrasbordos("", 10), "El codigo es nulo o vacio");
        checkearError(Retorno.Resultado.ERROR_2,
                tengoUnSistemaValido().listadoEstacionesCantTrasbordos(null, 10), "El codigo es nulo o vacio");
    }

    @Test
    public void testTrasbordosError3() {
        checkearError(Retorno.Resultado.ERROR_3,
                tengoUnSistemaValido().listadoEstacionesCantTrasbordos("A", 10), "El codigo es invalido");
        checkearError(Retorno.Resultado.ERROR_3,
                tengoUnSistemaValido().listadoEstacionesCantTrasbordos("AAAA232", 10), "El codigo es invalido");
        checkearError(Retorno.Resultado.ERROR_3,
                tengoUnSistemaValido().listadoEstacionesCantTrasbordos("!@#@~~~!~", 10), "El codigo es invalido");
        checkearError(Retorno.Resultado.ERROR_3,
                tengoUnSistemaValido().listadoEstacionesCantTrasbordos("$@$FCCW", 10), "El codigo es invalido");
        checkearError(Retorno.Resultado.ERROR_3,
                tengoUnSistemaValido().listadoEstacionesCantTrasbordos("aaa232", 10), "El codigo es invalido");
    }

    @Test
    public void testError4() {

        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(Retorno.Resultado.ERROR_4, sistema.listadoEstacionesCantTrasbordos(PARIS_2, 23), "No fue registrada");

        registroEstacionOk(sistema, EST_PARIS_2);
        checkearOk(sistema.listadoEstacionesCantTrasbordos(PARIS_2, 3), "Ahora que esta registrada debe funcionar");
        ///chqueamos el reseteo del sistema.
        sistema.inicializarSistema(20);
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(Retorno.Resultado.ERROR_4, sistema.listadoEstacionesCantTrasbordos(PARIS_2, 23), "No fue registrada");
    }

    private void chequearTrasbordoOk(Sistema sistema, AuxTestClaseEstacionNoUsar estacionOrigen, int cantidad, AuxTestConexionNoUsar... conexionesQueSeUsan) {

        Retorno ret = checkearOk(sistema.listadoEstacionesCantTrasbordos(estacionOrigen.getCodigo(), cantidad), "El listado de trasbordos debe ser valido");
        //Creamos un array con todas las estaciones usando el destino de las conexiones + la estacion de origen que siempre va a estar en el listado.
        AuxTestClaseEstacionNoUsar[] estacionesQueDebenEstar = new AuxTestClaseEstacionNoUsar[conexionesQueSeUsan.length + 1];
        int i = 0;
        estacionesQueDebenEstar[i++] = estacionOrigen;
        for (AuxTestConexionNoUsar conexionNoUsar : conexionesQueSeUsan) {
            estacionesQueDebenEstar[i++] = conexionNoUsar.getDestino();
        }
        //primero chequeamos que esten todas las que queremos
        for (AuxTestClaseEstacionNoUsar estacion : estacionesQueDebenEstar) {
            Assertions.assertTrue(ret.getValorString().contains(estacion.getCodigo()),
                    String.format("La estacion '%s' debe de estar pero no esta presente en el listado", estacion.getCodigo()));
        }
        Matcher matcher = Pattern.compile("^%s;%s([|]|$)").matcher(ret.getValorString());
        while (matcher.find()) {
            String codigoActual = matcher.group().split(";")[0];
            Arrays.stream(estacionesQueDebenEstar)
                    .filter(e -> e.getCodigo().equals(codigoActual))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Esta estacion esta de mas: " + codigoActual));
        }
        //segundo ordenamos la lista, y luego chequeamos que los retornos sean iguales. De esta manera nos aseguramos el orden.
        Arrays.sort(estacionesQueDebenEstar, Comparator.comparing(AuxTestClaseEstacionNoUsar::getCodigo));//NO SE PUEDE USAR EN SU OBLIGATORIO
        Assertions.assertEquals(Arrays.stream(estacionesQueDebenEstar).map(Objects::toString).collect(Collectors.joining("|")), ret.getValorString());


    }
}
