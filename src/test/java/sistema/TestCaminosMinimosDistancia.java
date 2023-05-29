package sistema;

import interfaz.EstadoCamino;
import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static interfaz.EstadoCamino.*;
import static interfaz.Retorno.Resultado.*;
import static sistema.AuxAccionesEstaciones.*;
import static sistema.AuxAsserciones.checkearError;
import static sistema.AuxAsserciones.checkearOk;
import static sistema.AuxTestClaseEstacionNoUsar.estacion;

/**********************************************************
 ************************ ERRORES COMUNES *****************
 * Cosas que pueden estarles pasando y que haga que los tests no funcionen
 * Usar '==' en vez de equals
 * No modelarlos como un multigrafo dirigido, no es lo mismo aristas[a][b] que aristas[b][a]
 * Usar attributos de clase para las agregaciones, que funcionen los test no quita que esto este mal. Lo mejor es usar attributos o objetos creados para esa llamada como por ejemplo un StringBuilder.
 * Mal manejo de los pipes, es mas facil hacer un prepend del pipe (agregarlo al principio) que un append la mayoría de las veces.
 * No darse cuenta que hay lugares donde no podemos explorar en el dijkstra debido a que el grafo no es conexo.
 * Lo anterior puede generar errores de overflow (INTEGER.MAX_VALUE+1 es negativo, porque?)
 * No usar doubles en las distancias
 * Que el máximo de vertices sea 100, no quiere decir que haya 100 vertices
 **********************************************************/
public class TestCaminosMinimosDistancia {
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

    private static final AuxTestConexionNoUsar MADRID_1_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_MADRID_1, 1, 50, 2, 10, EXCELENTE);
    private static final AuxTestConexionNoUsar MADRID_1_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_MADRID_2, 2, 25, 1, 5, MALO);
    private static final AuxTestConexionNoUsar MADRID_1_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_PARIS_1, 3, 625, 25, 120, MALO);
    private static final AuxTestConexionNoUsar MADRID_1_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_PARIS_2, 4, 0, 0, 0, BUENO);
    private static final AuxTestConexionNoUsar MADRID_1_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_LISBOA, 5, 650, 26, 145, BUENO);
    private static final AuxTestConexionNoUsar MADRID_1_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_LONDRES, 6, 375, 15, 150, BUENO);
    private static final AuxTestConexionNoUsar MADRID_1_TO_LYON = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_LYON, 7, 1250, 50, 175, MALO);
    private static final AuxTestConexionNoUsar MADRID_1_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_MADRID_1, EST_MONTREAL, 8, 650, 26, 175, MALO);
    private static final AuxTestConexionNoUsar MADRID_2_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_MADRID_1, 9, 25, 1, 10, MALO);
    private static final AuxTestConexionNoUsar MADRID_2_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_MADRID_2, 10, 750, 30, 175, BUENO);
    private static final AuxTestConexionNoUsar MADRID_2_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_PARIS_1, 11, 350, 14, 80, BUENO);
    private static final AuxTestConexionNoUsar MADRID_2_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_PARIS_2, 12, 600, 24, 175, MALO);
    private static final AuxTestConexionNoUsar MADRID_2_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_LISBOA, 13, 550, 22, 75, EXCELENTE);
    private static final AuxTestConexionNoUsar MADRID_2_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_LONDRES, 14, 225, 9, 80, BUENO);
    private static final AuxTestConexionNoUsar MADRID_2_TO_LYON = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_LYON, 15, 75, 3, 20, BUENO);
    private static final AuxTestConexionNoUsar MADRID_2_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_MADRID_2, EST_MONTREAL, 16, 200, 8, 60, BUENO);
    private static final AuxTestConexionNoUsar PARIS_1_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_MADRID_1, 17, 650, 26, 95, BUENO);
    private static final AuxTestConexionNoUsar PARIS_1_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_MADRID_2, 18, 625, 25, 170, EXCELENTE);
    private static final AuxTestConexionNoUsar PARIS_1_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_PARIS_1, 19, 900, 36, 190, BUENO);
    private static final AuxTestConexionNoUsar PARIS_1_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_PARIS_2, 20, 525, 21, 75, BUENO);
    private static final AuxTestConexionNoUsar PARIS_1_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_LISBOA, 21, 1325, 53, 185, BUENO);
    private static final AuxTestConexionNoUsar PARIS_1_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_LONDRES, 22, 300, 12, 45, EXCELENTE);
    private static final AuxTestConexionNoUsar PARIS_1_TO_LYON = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_LYON, 23, 50, 34, 185, BUENO);
    private static final AuxTestConexionNoUsar PARIS_1_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_PARIS_1, EST_MONTREAL, 24, 0, 0, 5, EXCELENTE);
    private static final AuxTestConexionNoUsar PARIS_2_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_MADRID_1, 25, 775, 31, 135, EXCELENTE);
    private static final AuxTestConexionNoUsar PARIS_2_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_MADRID_2, 26, 325, 13, 130, MALO);
    private static final AuxTestConexionNoUsar PARIS_2_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_PARIS_1, 27, 200, 8, 80, EXCELENTE);
    private static final AuxTestConexionNoUsar PARIS_2_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_PARIS_2, 28, 975, 39, 165, MALO);
    private static final AuxTestConexionNoUsar PARIS_2_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_LISBOA, 29, 1025, 41, 140, BUENO);
    private static final AuxTestConexionNoUsar PARIS_2_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_LONDRES, 30, 150, 6, 25, EXCELENTE);
    private static final AuxTestConexionNoUsar PARIS_2_TO_LYON = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_LYON, 31, 200, 8, 60, EXCELENTE);
    private static final AuxTestConexionNoUsar PARIS_2_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_PARIS_2, EST_MONTREAL, 32, 75, 3, 30, EXCELENTE);
    private static final AuxTestConexionNoUsar LISBOA_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_MADRID_1, 33, 425, 17, 105, BUENO);
    private static final AuxTestConexionNoUsar LISBOA_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_MADRID_2, 34, 700, 28, 115, MALO);
    private static final AuxTestConexionNoUsar LISBOA_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_PARIS_1, 35, 225, 9, 80, MALO);
    private static final AuxTestConexionNoUsar LISBOA_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_PARIS_2, 36, 275, 11, 65, BUENO);
    private static final AuxTestConexionNoUsar LISBOA_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_LISBOA, 37, 125, 5, 30, MALO);
    private static final AuxTestConexionNoUsar LISBOA_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_LONDRES, 38, 675, 27, 115, EXCELENTE);
    private static final AuxTestConexionNoUsar LISBOA_TO_LYON = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_LYON, 39, 100, 4, 20, MALO);
    private static final AuxTestConexionNoUsar LISBOA_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_LISBOA, EST_MONTREAL, 40, 475, 19, 110, EXCELENTE);
    private static final AuxTestConexionNoUsar LONDRES_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_MADRID_1, 41, 350, 14, 65, EXCELENTE);
    private static final AuxTestConexionNoUsar LONDRES_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_MADRID_2, 42, 225, 9, 60, MALO);
    private static final AuxTestConexionNoUsar LONDRES_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_PARIS_1, 43, 75, 3, 20, BUENO);
    private static final AuxTestConexionNoUsar LONDRES_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_PARIS_2, 44, 1000, 40, 150, BUENO);
    private static final AuxTestConexionNoUsar LONDRES_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_LISBOA, 45, 200, 8, 85, BUENO);
    private static final AuxTestConexionNoUsar LONDRES_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_LONDRES, 46, 525, 21, 100, EXCELENTE);
    private static final AuxTestConexionNoUsar LONDRES_TO_LYON = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_LYON, 47, 100, 4, 40, EXCELENTE);
    private static final AuxTestConexionNoUsar LONDRES_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_LONDRES, EST_MONTREAL, 48, 1175, 47, 185, MALO);
    private static final AuxTestConexionNoUsar LYON_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_LYON, EST_MADRID_1, 49, 575, 23, 120, MALO);
    private static final AuxTestConexionNoUsar LYON_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_LYON, EST_MADRID_2, 50, 100, 4, 30, BUENO);
    private static final AuxTestConexionNoUsar LYON_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_LYON, EST_PARIS_1, 51, 200, 8, 35, MALO);
    private static final AuxTestConexionNoUsar LYON_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_LYON, EST_PARIS_2, 52, 475, 19, 165, BUENO);
    private static final AuxTestConexionNoUsar LYON_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_LYON, EST_LISBOA, 53, 550, 22, 160, MALO);
    private static final AuxTestConexionNoUsar LYON_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_LYON, EST_LONDRES, 54, 350, 14, 75, EXCELENTE);
    private static final AuxTestConexionNoUsar LYON_TO_LYON = AuxTestConexionNoUsar.conexion(EST_LYON, EST_LYON, 55, 1075, 43, 150, BUENO);
    private static final AuxTestConexionNoUsar LYON_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_LYON, EST_MONTREAL, 56, 800, 32, 125, BUENO);
    private static final AuxTestConexionNoUsar MONTREAL_TO_MADRID_1 = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_MADRID_1, 57, 125, 5, 40, MALO);
    private static final AuxTestConexionNoUsar MONTREAL_TO_MADRID_2 = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_MADRID_2, 58, 150, 6, 40, MALO);
    private static final AuxTestConexionNoUsar MONTREAL_TO_PARIS_1 = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_PARIS_1, 59, 225, 9, 65, MALO);
    private static final AuxTestConexionNoUsar MONTREAL_TO_PARIS_2 = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_PARIS_2, 60, 50, 2, 10, EXCELENTE);
    private static final AuxTestConexionNoUsar MONTREAL_TO_LISBOA = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_LISBOA, 61, 575, 23, 115, BUENO);
    private static final AuxTestConexionNoUsar MONTREAL_TO_LONDRES = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_LONDRES, 62, 200, 8, 30, MALO);
    private static final AuxTestConexionNoUsar MONTREAL_TO_LYON = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_LYON, 63, 175, 7, 50, BUENO);
    private static final AuxTestConexionNoUsar MONTREAL_TO_MONTREAL = AuxTestConexionNoUsar.conexion(EST_MONTREAL, EST_MONTREAL, 64, 125, 5, 20, MALO);

    private Sistema tengoUnSistemaValido() {
        Sistema sis = new ImplementacionSistema();
        sis.inicializarSistema(20);
        return sis;
    }

    public static void main(String[] args) {
        String[] estaciones = new String[]{"MADRID_1", "MADRID_2", "PARIS_1", "PARIS_2", "LISBOA", "LONDRES", "LYON", "MONTREAL"};

        int id = 1;
        final double F_TIEMPO = 1 / 5.;
        Random r = new Random();
        for (int i = 0; i < estaciones.length; i++) {
            for (int j = 0; j < estaciones.length; j++) {
                int distancia = r.nextInt(40) * 5;

                int tiempo = (int) ((0.5 + r.nextDouble()) * F_TIEMPO * distancia);
                int costo = (int) tiempo * 25;

                System.out.printf("private static final AuxTestConexionNoUsar %s_TO_%s=AuxTestConexionNoUsar.conexion(EST_%s,EST_%s,%d,%d,%d,%d,%s);\n",
                        estaciones[i], estaciones[j], estaciones[i], estaciones[j], id++, costo, tiempo, distancia,
                        EstadoCamino.values()[r.nextInt(3)]);
            }
        }
    }


    @Test
    public void testCaminosMinimosDistancia1() {

        Sistema sistema = tengoUnSistemaValido();
        //Para ver el grafo esperado pueden entrar a
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%2220km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22150km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_1%5Blabel%3D%2210km%20%7BEXCELENTE%7D%20%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%225km%20%7BMALO%7D%20%22%5D%3B%0AV_3-%3EV_2%5Blabel%3D%22170km%20%7BEXCELENTE%7D%20%22%5D%3B%0A%7D%0A
        //layout recomendado circo
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        registrarConexionesOk(sistema,
                MADRID_1_TO_MADRID_2,
                MADRID_1_TO_MADRID_1,
                MADRID_1_TO_LONDRES,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_MADRID_2
        );

        verificarCaminoDistanciaMinimoOk(sistema, EST_MADRID_1, EST_PARIS_1,
                MADRID_1_TO_LONDRES, LONDRES_TO_PARIS_1);
        //El camino madrid 1 a madrid 2 esta en estado malo
        verificarCaminoDistanciaMinimoOk(sistema, EST_MADRID_1, EST_MADRID_2,
                MADRID_1_TO_LONDRES, LONDRES_TO_PARIS_1, PARIS_1_TO_MADRID_2);
        //Lo actualizamos y en teoria el grafo queda así por lo que podemos irnos derecho.
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%2220km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22150km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_1%5Blabel%3D%2210km%20%7BEXCELENTE%7D%20%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%225km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_2%5Blabel%3D%22170km%20%7BEXCELENTE%7D%20%22%5D%3B%0A%7D%0A
        checkearOk(sistema.actualizarCamino(MADRID_1, MADRID_2, MADRID_1_TO_MADRID_2.getId(), 25, 100, 5, BUENO), "El camino se deberia actualizar bien");
        verificarCaminoDistanciaMinimoOk(sistema, EST_MADRID_1, EST_MADRID_2,
                MADRID_1_TO_MADRID_2);
        //tocamos el costo de la ruta mad1 to mad2 para forzar a ir por la ruta larga
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%2220km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22150km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_1%5Blabel%3D%2210km%20%7BEXCELENTE%7D%20%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%22342km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_2%5Blabel%3D%22170km%20%7BEXCELENTE%7D%20%22%5D%3B%0A%7D%0A
        checkearOk(sistema.actualizarCamino(MADRID_1, MADRID_2, MADRID_1_TO_MADRID_2.getId(), 300, 100, 342, BUENO), "El camino se deberia actualizar bien");

        verificarCaminoDistanciaMinimoOk(sistema, EST_MADRID_1, EST_MADRID_2,
                MADRID_1_TO_LONDRES, LONDRES_TO_PARIS_1, PARIS_1_TO_MADRID_2);

    }

    @Test
    public void testCaminosMinimosDistancia2() {

        Sistema sistema = tengoUnSistemaValido();
        //Para ver el grafo inicial pueden entrar a
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_4%5Blabel%3D%22PAR002%22%5D%3B%0AV_5%5Blabel%3D%22LIS001%22%5D%3B%0AV_6%5Blabel%3D%22LYO001%22%5D%3B%0AV_7%5Blabel%3D%22MON001%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%2220km%20%7BBUENO%7D%20%22%5D%3B%0AV_0-%3EV_5%5Blabel%3D%2285km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22150km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%225km%20%7BMALO%7D%20%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%2280km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_4%5Blabel%3D%2275km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_6%5Blabel%3D%22185km%20%7BBUENO%7D%20%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%2230km%20%7BEXCELENTE%7D%20%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22105km%20%7BBUENO%7D%20%22%5D%3B%0AV_6-%3EV_7%5Blabel%3D%22125km%20%7BBUENO%7D%20%22%5D%3B%0A%7D%0A

        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1, EST_PARIS_2, EST_LISBOA,
                EST_LYON, EST_MONTREAL);
        registrarConexionesOk(sistema,
                MADRID_1_TO_LONDRES,
                MADRID_1_TO_MADRID_2,
                MADRID_2_TO_PARIS_1,
                LONDRES_TO_LISBOA,
                LISBOA_TO_MADRID_1,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_PARIS_2,
                PARIS_1_TO_LYON,
                LYON_TO_MONTREAL,
                PARIS_2_TO_MONTREAL
        );


        verificarCaminoDistanciaMinimoOk(sistema, EST_LISBOA, EST_MONTREAL,
                LISBOA_TO_MADRID_1,
                MADRID_1_TO_LONDRES,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_PARIS_2,
                PARIS_2_TO_MONTREAL);
        //Actualizamos madrid1 to madrid 2 cambiando el camino minimo
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_4%5Blabel%3D%22PAR002%22%5D%3B%0AV_5%5Blabel%3D%22LIS001%22%5D%3B%0AV_6%5Blabel%3D%22LYO001%22%5D%3B%0AV_7%5Blabel%3D%22MON001%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%2220km%20%7BBUENO%7D%20%22%5D%3B%0AV_0-%3EV_5%5Blabel%3D%2285km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22150km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%225km%20%7BBUENO%7D%20%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%2280km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_4%5Blabel%3D%2275km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_6%5Blabel%3D%22185km%20%7BBUENO%7D%20%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%2230km%20%7BEXCELENTE%7D%20%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22105km%20%7BBUENO%7D%20%22%5D%3B%0AV_6-%3EV_7%5Blabel%3D%22125km%20%7BBUENO%7D%20%22%5D%3B%0A%7D%0A
        actualizarConexionesOk(sistema, MADRID_1_TO_MADRID_2.withEstado(BUENO));

        verificarCaminoDistanciaMinimoOk(sistema, EST_LISBOA, EST_MONTREAL,
                LISBOA_TO_MADRID_1,
                MADRID_1_TO_MADRID_2,
                MADRID_2_TO_PARIS_1,
                PARIS_1_TO_PARIS_2,
                PARIS_2_TO_MONTREAL);
        //registramos otra conexion mas barata entre madrid 1 y londres
        AuxTestConexionNoUsar madridLondresMasBarata = MADRID_1_TO_LONDRES.withKm(30).withId(23232);
        registrarConexionesOk(sistema, madridLondresMasBarata);
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_4%5Blabel%3D%22PAR002%22%5D%3B%0AV_5%5Blabel%3D%22LIS001%22%5D%3B%0AV_6%5Blabel%3D%22LYO001%22%5D%3B%0AV_7%5Blabel%3D%22MON001%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%2220km%20%7BBUENO%7D%20%22%5D%3B%0AV_0-%3EV_5%5Blabel%3D%2285km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22150km%20%7BBUENO%7D%20%2C30km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%225km%20%7BBUENO%7D%20%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%2280km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_4%5Blabel%3D%2275km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_6%5Blabel%3D%22185km%20%7BBUENO%7D%20%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%2230km%20%7BEXCELENTE%7D%20%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22105km%20%7BBUENO%7D%20%22%5D%3B%0AV_6-%3EV_7%5Blabel%3D%22125km%20%7BBUENO%7D%20%22%5D%3B%0A%7D%0A
        verificarCaminoDistanciaMinimoOk(sistema, EST_LISBOA, EST_MONTREAL,
                LISBOA_TO_MADRID_1,
                madridLondresMasBarata,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_PARIS_2,
                PARIS_2_TO_MONTREAL);

        verificarCaminoDistanciaMinimoOk(sistema, EST_LISBOA, EST_LYON,
                LISBOA_TO_MADRID_1,
                madridLondresMasBarata,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_LYON);
        registroEstacionOk(sistema, estacion("ROM002", "Roma"));
        registroEstacionOk(sistema, estacion("ITA002", "Florencia"));
        registroEstacionOk(sistema, estacion("RUS002", "Moscu"));
        registrarConexionesOk(
                sistema,
                AuxTestConexionNoUsar.conexion("ROM002", "ITA002", 1, 2322, 22, 22, BUENO),
                AuxTestConexionNoUsar.conexion("ROM002", "RUS002", 1, 2322, 22, 22, BUENO)
        );
        //No importa que agregemos una componente no conexa los caminos se deben mantener
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_4%5Blabel%3D%22PAR002%22%5D%3B%0AV_5%5Blabel%3D%22LIS001%22%5D%3B%0AV_6%5Blabel%3D%22LYO001%22%5D%3B%0AV_7%5Blabel%3D%22MON001%22%5D%3B%0AV_8%5Blabel%3D%22ROM002%22%5D%3B%0AV_9%5Blabel%3D%22ITA002%22%5D%3B%0AV_10%5Blabel%3D%22RUS002%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%2220km%20%7BBUENO%7D%20%22%5D%3B%0AV_0-%3EV_5%5Blabel%3D%2285km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22150km%20%7BBUENO%7D%20%2C30km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%225km%20%7BBUENO%7D%20%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%2280km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_4%5Blabel%3D%2275km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_6%5Blabel%3D%22185km%20%7BBUENO%7D%20%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%2230km%20%7BEXCELENTE%7D%20%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22105km%20%7BBUENO%7D%20%22%5D%3B%0AV_6-%3EV_7%5Blabel%3D%22125km%20%7BBUENO%7D%20%22%5D%3B%0AV_8-%3EV_9%5Blabel%3D%2222km%20%7BBUENO%7D%20%22%5D%3B%0AV_8-%3EV_10%5Blabel%3D%2222km%20%7BBUENO%7D%20%22%5D%3B%0A%7D%0A

        verificarCaminoDistanciaMinimoOk(sistema, EST_LISBOA, EST_MONTREAL,
                LISBOA_TO_MADRID_1,
                madridLondresMasBarata,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_PARIS_2,
                PARIS_2_TO_MONTREAL);

        verificarCaminoDistanciaMinimoOk(sistema, EST_LISBOA, EST_LYON,
                LISBOA_TO_MADRID_1,
                madridLondresMasBarata,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_LYON);

        //Asimismo tampoco importa que agreguemos una conexión "más corta" si el estado es malo

        AuxTestConexionNoUsar madridLondresMasBarataRota = MADRID_1_TO_LONDRES.withKm(1).withId(223232).withEstado(MALO);
        registrarConexionesOk(sistema, madridLondresMasBarataRota);
        verificarCaminoDistanciaMinimoOk(sistema, EST_LISBOA, EST_MONTREAL,
                LISBOA_TO_MADRID_1,
                madridLondresMasBarata,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_PARIS_2,
                PARIS_2_TO_MONTREAL);
    }

    @Test
    public void testCaminosMinimosDistanciaNoHayCamino() {

        Sistema sistema = tengoUnSistemaValido();

        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1, EST_PARIS_2, EST_LISBOA,
                EST_LYON, EST_MONTREAL);
        registrarConexionesOk(sistema,
                MADRID_1_TO_LONDRES,
                MADRID_2_TO_PARIS_1,
                LONDRES_TO_LISBOA,
                LISBOA_TO_MADRID_1,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_PARIS_2,
                PARIS_1_TO_LYON,
                LYON_TO_MONTREAL,
                PARIS_2_TO_MONTREAL
        );
        //El grafo es :
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_4%5Blabel%3D%22PAR002%22%5D%3B%0AV_5%5Blabel%3D%22LIS001%22%5D%3B%0AV_6%5Blabel%3D%22LYO001%22%5D%3B%0AV_7%5Blabel%3D%22MON001%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%2220km%20%7BBUENO%7D%20%22%5D%3B%0AV_0-%3EV_5%5Blabel%3D%2285km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22150km%20%7BBUENO%7D%20%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%2280km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_4%5Blabel%3D%2275km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_6%5Blabel%3D%22185km%20%7BBUENO%7D%20%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%2230km%20%7BEXCELENTE%7D%20%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22105km%20%7BBUENO%7D%20%22%5D%3B%0AV_6-%3EV_7%5Blabel%3D%22125km%20%7BBUENO%7D%20%22%5D%3B%0A%7D%0A
        checkearError(ERROR_3, sistema.viajeCostoMinimoKilometros(LISBOA, MADRID_2), "No hay camino");
        //Agregamos la conexion en estado malo
        registrarConexionesOk(sistema,
                MADRID_1_TO_MADRID_2);
        checkearError(ERROR_3, sistema.viajeCostoMinimoKilometros(LISBOA, MADRID_2), "No hay camino");
        registroEstacionOk(sistema, estacion("ROM022", "Roma"));

        checkearError(ERROR_3, sistema.viajeCostoMinimoKilometros(LISBOA, "ROM022"), "No hay camino");

        //Agregamos una conexion lyon madrid 2
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22LON001%22%5D%3B%0AV_1%5Blabel%3D%22MAD001%22%5D%3B%0AV_2%5Blabel%3D%22MAD002%22%5D%3B%0AV_3%5Blabel%3D%22PAR001%22%5D%3B%0AV_4%5Blabel%3D%22PAR002%22%5D%3B%0AV_5%5Blabel%3D%22LIS001%22%5D%3B%0AV_6%5Blabel%3D%22LYO001%22%5D%3B%0AV_7%5Blabel%3D%22MON001%22%5D%3B%0AV_8%5Blabel%3D%22ROM022%22%5D%3B%0AV_0-%3EV_3%5Blabel%3D%2220km%20%7BBUENO%7D%20%22%5D%3B%0AV_0-%3EV_5%5Blabel%3D%2285km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22150km%20%7BBUENO%7D%20%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%225km%20%7BMALO%7D%20%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%2280km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_4%5Blabel%3D%2275km%20%7BBUENO%7D%20%22%5D%3B%0AV_3-%3EV_6%5Blabel%3D%22185km%20%7BBUENO%7D%20%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%2230km%20%7BEXCELENTE%7D%20%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22105km%20%7BBUENO%7D%20%22%5D%3B%0AV_6-%3EV_2%5Blabel%3D%2230km%20%7BBUENO%7D%20%22%5D%3B%0AV_6-%3EV_7%5Blabel%3D%22125km%20%7BBUENO%7D%20%22%5D%3B%0A%7D%0A
        registrarConexionesOk(sistema, LYON_TO_MADRID_2);
        verificarCaminoDistanciaMinimoOk(sistema, EST_LISBOA, EST_MADRID_2,
                LISBOA_TO_MADRID_1,
                MADRID_1_TO_LONDRES,
                LONDRES_TO_PARIS_1,
                PARIS_1_TO_LYON,
                LYON_TO_MADRID_2);
    }

    @Test
    public void testCaminosDistanciaMinimosEstacionesInvalidas() {
        Sistema sistema = tengoUnSistemaValido();

        checkearError(ERROR_1, sistema.viajeCostoMinimoKilometros("", "ROM022"), "vacios/nulos");
        checkearError(ERROR_1, sistema.viajeCostoMinimoKilometros(null, "ROM022"), "vacios/nulos");
        checkearError(ERROR_1, sistema.viajeCostoMinimoKilometros("ROM022", null), "vacios/nulos");
        checkearError(ERROR_1, sistema.viajeCostoMinimoKilometros("ROM022", ""), "vacios/nulos");

        checkearError(ERROR_2, sistema.viajeCostoMinimoKilometros("AS@33", "ROM022"), "invalidos");
        checkearError(ERROR_2, sistema.viajeCostoMinimoKilometros("ROM232", "ROM02222"), "invalidos");
    }

    @Test
    public void testCaminosDistanciaMinimosEstacionesNoExistentes() {

        Sistema sistema = tengoUnSistemaValido();

        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1, EST_PARIS_2, EST_LISBOA,
                EST_LYON, EST_MONTREAL);
        checkearError(ERROR_5, sistema.viajeCostoMinimoKilometros(EST_LONDRES.getCodigo(), "ROM022"), "invalidos");
        checkearError(ERROR_4, sistema.viajeCostoMinimoKilometros("ROM022", EST_LONDRES.getCodigo()), "invalidos");
    }

    private void verificarCaminoDistanciaMinimoOk(Sistema sistema, AuxTestClaseEstacionNoUsar estacionOrigen, AuxTestClaseEstacionNoUsar estacionDestino,
                                                  AuxTestConexionNoUsar... conexionesQueSeUsan) {
        Retorno ret = checkearOk(sistema.viajeCostoMinimoKilometros(estacionOrigen.getCodigo(), estacionDestino.getCodigo()), "El costo minimo no debe dar error");

        double costoTotal = 0;
        for (AuxTestConexionNoUsar conn : conexionesQueSeUsan) {
            costoTotal += conn.getKilometros();
        }
        Assertions.assertEquals((int) costoTotal, ret.getValorInteger());
        String retornoEsperado = Stream.concat(Stream.of(estacionOrigen), Arrays.stream(conexionesQueSeUsan).map(AuxTestConexionNoUsar::getDestino))
                .map(Objects::toString)
                .collect(Collectors.joining("|"));
        Assertions.assertEquals(retornoEsperado, ret.getValorString());
    }
}
