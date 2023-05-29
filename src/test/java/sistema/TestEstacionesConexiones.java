package sistema;

import org.junit.jupiter.api.Test;

import static sistema.AuxAccionesEstaciones.registroEstacion;
import static sistema.AuxAccionesEstaciones.registroEstacionOk;
import static sistema.AuxAsserciones.checkearError;
import static sistema.AuxAsserciones.checkearOk;
import static sistema.AuxTestClaseEstacionNoUsar.estacion;
import static interfaz.EstadoCamino.*;
import static interfaz.Retorno.Resultado.*;
import  interfaz.*;

/**********************************************************
 ************************ ERRORES COMUNES *****************
 * Cosas que pueden estarles pasando y que haga que los tests no funcionen
 * Usar '==' en vez de equals
 * No modelarlos como un multigrafo dirigido, no es lo mismo aristas[a][b] que aristas[b][a]
 * Usar attributos de clase para las agregaciones, que funcionen los test no quita que esto este mal. Lo mejor es usar attributos o objetos creados para esa llamada como por ejemplo un StringBuilder.
 * Mal manejo de los pipes, es mas facil hacer un prepend del pipe (agregarlo al principio) que un append la mayoría de las veces.
 **********************************************************/
public class TestEstacionesConexiones {


    private static final String MADRID_1 = "MAD001";
    private static final String MADRID_2 = "MAD002";
    private static final String PARIS_1 = "PAR001";
    private static final String PARIS_2 = "PAR001";
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
        return tengoUnSistemaValido(20);
    }

    private Sistema tengoUnSistemaValido(int maxEstaciones) {
        ImplementacionSistema impl = new ImplementacionSistema();
        impl.inicializarSistema(maxEstaciones);
        return impl;
    }

    @Test
    public void testInicializarError1() {
        Sistema sis = new ImplementacionSistema();
        checkearError(ERROR_1, sis.inicializarSistema(5), "Deberia dar error");
        checkearError(ERROR_1, sis.inicializarSistema(-5), "Deberia dar error");
    }

    @Test
    public void testRegistrarEstacionOk() {
        Sistema sistema = tengoUnSistemaValido();
        checkearOk(registroEstacion(sistema, estacion("AAA002", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        checkearOk(registroEstacion(sistema, estacion("AAA000", "Otro nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        checkearOk(registroEstacion(sistema, estacion("ABB149", "Un nombre")), "La estacion es valida por lo que debería haberse registrado");
        checkearOk(registroEstacion(sistema, estacion("ZZZ999", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");

    }

    @Test
    public void testRegistrarEstacionError1() {
        Sistema sistema = tengoUnSistemaValido(6);

        for (int i = 0; i < 6; i++) {

            checkearOk(registroEstacion(sistema, estacion("AAA00" + i, "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        }
        checkearError(ERROR_1, registroEstacion(sistema, estacion("BBB023", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
    }

    @Test
    public void testRegistrarEstacionError1ConReinicioDelSistema() {
        Sistema sistema = tengoUnSistemaValido(6);

        for (int i = 0; i < 6; i++) {

            checkearOk(registroEstacion(sistema, estacion("AAA00" + i, "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        }
        checkearError(ERROR_1, registroEstacion(sistema, estacion("BBB023", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        sistema.inicializarSistema(10);
        for (int i = 0; i < 10; i++) {

            checkearOk(registroEstacion(sistema, estacion("AAA00" + i, "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        }
        checkearError(ERROR_1, registroEstacion(sistema, estacion("BBB023", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
    }

    @Test
    public void testRegistrarEstacionError2() {
        Sistema sistema = tengoUnSistemaValido();
        checkearError(ERROR_2, registroEstacion(sistema, estacion(null, "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        checkearError(ERROR_2, registroEstacion(sistema, estacion(null, null)), "La estacion es valida por lo que debería haberse registrado");
        checkearError(ERROR_2, registroEstacion(sistema, estacion(null, null)), "La estacion es valida por lo que debería haberse registrado");

    }

    @Test
    public void testRegistrarEstacionError3() {
        Sistema sistema = tengoUnSistemaValido();
        checkearError(ERROR_3, registroEstacion(sistema, estacion("AA@322", "Un nombre valido")), "La estacion no tiene codigo valido");
        checkearError(ERROR_3, registroEstacion(sistema, estacion("AA2322", "Un nombre valido")), "La estacion no tiene codigo valido");
        checkearError(ERROR_3, registroEstacion(sistema, estacion("aaa322", "Un nombre valido")), "La estacion no tiene codigo valido");
        checkearError(ERROR_3, registroEstacion(sistema, estacion("AAB42!", "Un nombre valido")), "La estacion no tiene codigo valido");
    }

    @Test
    public void testRegistrarEstacion4() {

        Sistema sistema = tengoUnSistemaValido();
        checkearOk(registroEstacion(sistema, estacion("AAA004", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        checkearOk(registroEstacion(sistema, estacion("AAA231", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        checkearOk(registroEstacion(sistema, estacion("AAA121", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        checkearOk(registroEstacion(sistema, estacion("AXZ124", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");
        checkearOk(registroEstacion(sistema, estacion("ABZ124", "Un nombre valido")), "La estacion es valida por lo que debería haberse registrado");

        checkearError(ERROR_4, registroEstacion(sistema, estacion("AXZ124", "Un nombre valido")), "La estacion deberia estar duplicada");

    }

    @Test
    public void testRegistrarConexionOk() {

        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_PARIS_1, EST_MADRID_2);
        checkearOk(sistema.registrarConexion(LONDRES, MADRID_1, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        //el grafo es dirigido
        checkearOk(sistema.registrarConexion(MADRID_1, LONDRES, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(MADRID_1, PARIS_1, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");

    }

    @Test
    public void testRegistrarConexionError1() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(ERROR_1, sistema.registrarConexion(MADRID_1, MADRID_2, -1, 222, 333, 22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.registrarConexion(MADRID_1, MADRID_2, 12, -222, 333, 22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.registrarConexion(MADRID_1, MADRID_2, 1, 222, -333, 22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.registrarConexion(MADRID_1, MADRID_2, 1, 222, 333, -22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.registrarConexion(MADRID_1, MADRID_2, -1, -222, -333, 22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.registrarConexion(MADRID_1, MADRID_2, 0, -222, -333, 22, EstadoCamino.BUENO), "negativos");

    }

    @Test
    public void testRegistrarConexionError2() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(ERROR_2, sistema.registrarConexion(MADRID_1, "", 1, 222, 333, 22, EstadoCamino.BUENO), "nulos");
        checkearError(ERROR_2, sistema.registrarConexion(null, "", 1, 222, 333, 22, EstadoCamino.BUENO), "nulos");
        checkearError(ERROR_2, sistema.registrarConexion(null, MADRID_2, 1, 222, 333, 22, EstadoCamino.BUENO), "nulos");
        checkearError(ERROR_2, sistema.registrarConexion(null, MADRID_2, 1, 222, 333, 22, null), "nulos");

    }

    @Test
    public void testRegistrarConexionError3() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(ERROR_3, sistema.registrarConexion(MADRID_1, "ZADDD@23", 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");
        checkearError(ERROR_3, sistema.registrarConexion("ADSQ!2", "ZADDD@23", 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");
        checkearError(ERROR_3, sistema.registrarConexion("~AAA233", "ZADDD@23", 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");

    }


    @Test
    public void testRegistrarConexionError4() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(ERROR_4, sistema.registrarConexion(MONTREAL, MADRID_1, 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");

    }

    @Test
    public void testRegistrarConexionError5() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(ERROR_5, sistema.registrarConexion(MADRID_1, MONTREAL, 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");
    }

    @Test
    public void testRegistrarConexionError6() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearOk(sistema.registrarConexion(LONDRES, MADRID_1, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(LONDRES, MADRID_2, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        //Las conexiones no son navegables en ambos sentidos, por lo que lo siguiente es ok
        checkearOk(sistema.registrarConexion(MADRID_2, LONDRES, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        // Si hay queremos registrar otra tendriamos que poner otro identificador. El tema es que no lo cambiamos por lo que nos da error 6
        checkearError(ERROR_6, sistema.registrarConexion(MADRID_2, LONDRES, 1, 100, 20, 300, EstadoCamino.BUENO), "El codigo esta repetido");
        //Al cambiarlo no hay problema
        checkearOk(sistema.registrarConexion(MADRID_2, LONDRES, 2, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(MADRID_2, LONDRES, 3, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearError(ERROR_6, sistema.registrarConexion(MADRID_2, LONDRES, 2, 100, 20, 300, EstadoCamino.BUENO), "El codigo esta repetido");
    }

    @Test
    public void testActualizarConexionOk() {

        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_PARIS_1, EST_MADRID_2);
        checkearOk(sistema.registrarConexion(LONDRES, MADRID_1, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        //el grafo es dirigido
        checkearOk(sistema.registrarConexion(MADRID_1, LONDRES, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(MADRID_1, LONDRES, 2, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(MADRID_1, PARIS_1, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");

        checkearOk(sistema.actualizarCamino(MADRID_1, LONDRES, 1, 20, 30, 40, EstadoCamino.MALO), "Todos los parametros estan bien y la conexión existe");
        checkearOk(sistema.actualizarCamino(MADRID_1, LONDRES, 1, 10, 340, 40, EstadoCamino.EXCELENTE), "Todos los parametros estan bien y la conexión existe");
        checkearOk(sistema.actualizarCamino(LONDRES, MADRID_1, 1, 20, 30, 40, EstadoCamino.MALO), "Todos los parametros estan bien y la conexión existe");
        checkearOk(sistema.actualizarCamino(MADRID_1, LONDRES, 2, 30, 200, 300, EstadoCamino.BUENO), "Es una conexion valida");

    }

    @Test
    public void testActualizarConexionError1() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearOk(sistema.registrarConexion(MADRID_1, MADRID_2, 1, 222, 333, 22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.actualizarCamino(MADRID_1, MADRID_2, -1, 222, 333, 22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.actualizarCamino(MADRID_1, MADRID_2, 12, -222, 333, 22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.actualizarCamino(MADRID_1, MADRID_2, 1, 222, -333, 22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.actualizarCamino(MADRID_1, MADRID_2, 1, 222, 333, -22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.actualizarCamino(MADRID_1, MADRID_2, -1, -222, -333, 22, EstadoCamino.BUENO), "negativos");
        checkearError(ERROR_1, sistema.actualizarCamino(MADRID_1, MADRID_2, 0, -222, -333, 22, EstadoCamino.BUENO), "negativos");

    }

    @Test
    public void testActualizarConexionError2() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(ERROR_2, sistema.actualizarCamino(MADRID_1, "", 1, 222, 333, 22, EstadoCamino.BUENO), "nulos");
        checkearError(ERROR_2, sistema.actualizarCamino(null, "", 1, 222, 333, 22, EstadoCamino.BUENO), "nulos");
        checkearError(ERROR_2, sistema.actualizarCamino(null, MADRID_2, 1, 222, 333, 22, EstadoCamino.BUENO), "nulos");
        checkearError(ERROR_2, sistema.actualizarCamino(null, MADRID_2, 1, 222, 333, 22, null), "nulos");

    }

    @Test
    public void testActualizarConexionError3() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(ERROR_3, sistema.actualizarCamino(MADRID_1, "ZADDD@23", 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");
        checkearError(ERROR_3, sistema.actualizarCamino("ADSQ!2", "ZADDD@23", 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");
        checkearError(ERROR_3, sistema.actualizarCamino("~AAA233", "ZADDD@23", 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");

    }


    @Test
    public void testActualizarConexionError4() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(ERROR_4, sistema.actualizarCamino(MONTREAL, MADRID_1, 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");

    }

    @Test
    public void testActualizarConexionError5() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearError(ERROR_5, sistema.actualizarCamino(MADRID_1, MONTREAL, 1, 222, 333, 22, EstadoCamino.BUENO), "codigos invalidos");
    }

    @Test
    public void testActualizarConexionError6() {
        Sistema sistema = tengoUnSistemaValido();
        registroEstacionOk(sistema, EST_LONDRES, EST_MADRID_1, EST_MADRID_2, EST_PARIS_1);
        checkearOk(sistema.registrarConexion(LONDRES, MADRID_1, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(LONDRES, MADRID_2, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(MADRID_2, LONDRES, 1, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(MADRID_2, LONDRES, 2, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(MADRID_2, LONDRES, 3, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearOk(sistema.registrarConexion(MADRID_1, PARIS_1, 3, 100, 20, 300, EstadoCamino.BUENO), "Es una conexion valida");
        checkearError(ERROR_6, sistema.actualizarCamino(MADRID_2, LONDRES, 4, 100, 20, 300, EstadoCamino.BUENO), "El id de camino no esta registrado");
        checkearError(ERROR_6, sistema.actualizarCamino(PARIS_1, LONDRES, 2, 100, 20, 300, EstadoCamino.BUENO), "No ningun camino");
        //Recuerden que el grafo es dirigido
        checkearError(ERROR_6, sistema.actualizarCamino(PARIS_1, MADRID_1, 1, 100, 20, 300, EstadoCamino.BUENO), "El id de camino no esta registrado");
    }


}
