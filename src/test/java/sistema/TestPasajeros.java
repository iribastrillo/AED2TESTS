package sistema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Random;
import interfaz.*;

import static sistema.AuxAccionesPasajeros.agregoUnPasajeroValidoConElMismoId;
import static sistema.AuxAccionesPasajeros.seAgregoCorrectamenteUnPasajeroValido;
import static sistema.AuxAsserciones.checkearError;
import static sistema.AuxAsserciones.checkearOk;
import static sistema.AuxTestPasajeroNoUsar.getIdentificadorValido;
import static sistema.AuxTestPasajeroNoUsar.pasajero;
import static sistema.TestUtil.*;
import static interfaz.Retorno.Resultado.*;

/**********************************************************
 ************************ ERRORES COMUNES *****************
 * Cosas que pueden estarles pasando y que haga que los tests no funcionen
 * Orden lexicografico en vez de númerico
 * Usar '==' en vez de equals
 * Tener invertido el comparator
 * Usar attributos de clase para las agregaciones, que funcionen los test no quita que esto este mal. Lo mejor es usar attributos o objetos creados para esa llamada como por ejemplo un StringBuilder.
 * Mal manejo de los pipes, es mas facil hacer un prepend del pipe (agregarlo al principio) que un append la mayoría de las veces.
 **********************************************************/
public class TestPasajeros {

    private static final String IDENTIFICADOR_VALIDO = getIdentificadorValido(Nacionalidad.Alemania, 1_234_567_8);

    private static final String[][] IDENTIFICADORES_INVALIDOS_CON_MENSAJE_ERROR = new String[][]{
            {"CH123.456#2", "La nacionalidad es invalida"},
            {"123.456#2", "La nacionalidad no esta"},
            {"FR123.456-2", "El caracter # no esta"},
            {"FR123#456#2", "En vez de punto hay un # (acuerdense que el punto en las regexp hay que 'escapearlo')"},
            {"FR123....456#2", "Hay muchos puntos"},
            {"FR023.456#2", "Empieza en 0"},
            {"FR0.123.456#2", "Empieza en 0"},
            {"FR123222#2", "No es valido"},
            {"bla bla bla", "No es valido"},
            {"FRA@#!@!@!$CXXSAAQW!", "No es valido"},
            {"PEPWEWW--ew" + IDENTIFICADOR_VALIDO, "Si falla seguramente falte el pedirle a la regexp que empieze con lo que le piden"},
            {IDENTIFICADOR_VALIDO + "323232dsd", "Se olvidaron de pedirle a la regexp que termine (por defecto caputra contains no que matchee todo)"},
    };

    private static final AuxTestPasajeroNoUsar[] PASAJEROS_VALIDOS = new AuxTestPasajeroNoUsar[]{
            pasajero(Nacionalidad.Alemania, 1_232_222_2, "Jorgen", 23, 0),
            pasajero(Nacionalidad.Espania, 2_223_222_2, "Raul", 52, 1),
            pasajero(Nacionalidad.Francia, 223_222_2, "Amelie", 17, 1),
            pasajero(Nacionalidad.ReinoUnido, 5_555_555_3, "Catherine", 43, 2),
            pasajero(Nacionalidad.Otro, 301_101_0, "Ian", 22, 2),
            pasajero(Nacionalidad.Otro, 332_222_2, "George", 23, 3),
            pasajero(Nacionalidad.Otro, 335_222_2, "George", 23, 4),
            pasajero(Nacionalidad.Otro, 101_111_2, "Robert", 23, 2)
    };

    @Test
    public void testRegistrarOk() {
        //dado que
        Sistema sis = tengoUnSistemaValido();

        //registro varios pasajeros y chequeo que todos sean correctos

        int indice = 0;
        //podriamos haber hecho un for pero lo hacemos manual porque así pueden depurar más facilmente
        //0
        Retorno resultado = sis.registrarPasajero(PASAJEROS_VALIDOS[indice].getIdentificador(), PASAJEROS_VALIDOS[indice].getNombre(), PASAJEROS_VALIDOS[indice++].getEdad());
        checkearOk(resultado, "El pasajero deberia haberse registrado correctamente");
        //1
        resultado = sis.registrarPasajero(PASAJEROS_VALIDOS[indice].getIdentificador(), PASAJEROS_VALIDOS[indice].getNombre(), PASAJEROS_VALIDOS[indice++].getEdad());
        checkearOk(resultado, "El pasajero deberia haberse registrado correctamente");
        //2
        resultado = sis.registrarPasajero(PASAJEROS_VALIDOS[indice].getIdentificador(), PASAJEROS_VALIDOS[indice].getNombre(), PASAJEROS_VALIDOS[indice++].getEdad());
        checkearOk(resultado, "El pasajero deberia haberse registrado correctamente");
        //3
        resultado = sis.registrarPasajero(PASAJEROS_VALIDOS[indice].getIdentificador(), PASAJEROS_VALIDOS[indice].getNombre(), PASAJEROS_VALIDOS[indice++].getEdad());
        checkearOk(resultado, "El pasajero deberia haberse registrado correctamente");
        //4
        resultado = sis.registrarPasajero(PASAJEROS_VALIDOS[indice].getIdentificador(), PASAJEROS_VALIDOS[indice].getNombre(), PASAJEROS_VALIDOS[indice++].getEdad());
        checkearOk(resultado, "El pasajero deberia haberse registrado correctamente");

        //5
        resultado = sis.registrarPasajero(PASAJEROS_VALIDOS[indice].getIdentificador(), PASAJEROS_VALIDOS[indice].getNombre(), PASAJEROS_VALIDOS[indice++].getEdad());
        checkearOk(resultado, "El pasajero deberia haberse registrado correctamente");
    }

    @Test
    public void testRegistrarVaciosONulosError() {
        //dado que
        Sistema sis = tengoUnSistemaValido();
        //cuando quiero
        Retorno resultado = sis.registrarPasajero(null, "Un nombre", 23);
        checkearError(ERROR_1, resultado, "El id es nullo por lo que el error es 1");

        //cuando quiero
        resultado = sis.registrarPasajero("", "Un nombre", 23);
        checkearError(ERROR_1, resultado, "El id es vacio por lo que el error es 1");

        //cuando quiero
        resultado = sis.registrarPasajero(IDENTIFICADOR_VALIDO, null, 23);
        checkearError(ERROR_1, resultado, "El nombre es nulo por lo que el error es 1");
        //cuando quiero
        resultado = sis.registrarPasajero(IDENTIFICADOR_VALIDO, "", 23);
        checkearError(ERROR_1, resultado, "El nombre es vacio por lo que el error es 1");
        //cuando quiero
        resultado = sis.registrarPasajero(IDENTIFICADOR_VALIDO, "nombre valido", 23);
        checkearOk(resultado, "Deberia poder agregarlo si es valido");
    }

    @Test
    public void testRegistrarIdentificadoresInvalidosError() {
        //dado que
        Sistema sis = tengoUnSistemaValido();
        //cuando quiero
        for (String[] invalidos : IDENTIFICADORES_INVALIDOS_CON_MENSAJE_ERROR) {

            Retorno resultado = sis.registrarPasajero(invalidos[0], "Un nombre valido", 23);
            AuxAsserciones.checkearError(ERROR_2, resultado, invalidos[1]);
        }

        //chequeamos que el sistema no haya quedado inestable
        Retorno resultado = sis.registrarPasajero(IDENTIFICADOR_VALIDO, "nombre valido", 23);
        checkearOk(resultado, "Deberia poder agregarlo si es valido");
    }

    @Test
    public void testearAgregarRepetidosError() {
        //dado que
        Sistema sis = tengoUnSistemaValido();
        seAgregoCorrectamenteUnPasajeroValido(sis, Nacionalidad.Alemania, 1_232_222_2, "Jorgen", 23);
        seAgregoCorrectamenteUnPasajeroValido(sis, Nacionalidad.Francia, 232_222_2, "Roberto", 15);
        seAgregoCorrectamenteUnPasajeroValido(sis, Nacionalidad.Otro, 332_222_2, "Ramon", 12);
        //cuando quiero
        Retorno resultado = agregoUnPasajeroValidoConElMismoId(sis, getIdentificadorValido(Nacionalidad.Francia, 232_222_2), "2323", 12);
        //entonces espero
        checkearError(ERROR_3, resultado);
        //Para nosotros tambien un pasajero es duplicado aunque tenga la misma nacionalidad pero diferente numero.
        resultado = agregoUnPasajeroValidoConElMismoId(sis, getIdentificadorValido(Nacionalidad.Alemania, 232_222_2), "2323", 12);
        checkearError(ERROR_3, resultado);
    }


    @Test
    public void testBuscarPasajeroOk() {
        Sistema sis = tengoUnSistemaConPasajerosValidosRegistrados();
        for (AuxTestPasajeroNoUsar datosPasajero : PASAJEROS_VALIDOS) {
            Retorno resultado = sis.buscarPasajero(datosPasajero.getIdentificador());
            AuxAsserciones.checkearOk(resultado, datosPasajero.toString(), "El pasajero no fue encontrado correctamente");
            Assertions.assertEquals(datosPasajero.getProfundidadEsperada(), resultado.getValorInteger(), "La profundidad no es la esperada");
        }
    }

    @Test
    public void testAscendenteOk() {
        Sistema sis = tengoUnSistemaConPasajerosValidosRegistrados();

        Assertions.assertEquals(
                arrToString(ordenar(PASAJEROS_VALIDOS, Comparator.comparingInt(p -> p.getNumeroId()))),
                sis.listarPasajerosAscendente().getValorString(),
                "Error con la lista");
        Random r = new Random(1_424_2322);
        sis = tengoUnSistemaValido();
        for (AuxTestPasajeroNoUsar p : shuffle(PASAJEROS_VALIDOS, r)) {
            seAgregoCorrectamenteUnPasajeroValido(sis, p.getNacionalidad(), p.getNumeroId(), p.getNombre(), p.getEdad());
        }
        Assertions.assertEquals(
                arrToString(ordenar(PASAJEROS_VALIDOS, Comparator.comparingInt(p -> p.getNumeroId()))),
                sis.listarPasajerosAscendente().getValorString(),
                "Error con la lista, probablemente tengan un estado intermedio o este mal el agregado en el arbol");
    }

    @Test
    public void testDescendenteOk() {
        Sistema sis = tengoUnSistemaConPasajerosValidosRegistrados();

        Assertions.assertEquals(
                arrToString(ordenar(PASAJEROS_VALIDOS, Comparator.<AuxTestPasajeroNoUsar>comparingInt(p -> p.getNumeroId()).reversed())),
                sis.listarPasajerosDescendente().getValorString(),
                "Error con la lista");
        Random r = new Random(1_424_2322);
        sis = tengoUnSistemaValido();
        for (AuxTestPasajeroNoUsar p : shuffle(PASAJEROS_VALIDOS, r)) {
            seAgregoCorrectamenteUnPasajeroValido(sis, p.getNacionalidad(), p.getNumeroId(), p.getNombre(), p.getEdad());
        }
        Assertions.assertEquals(
                arrToString(ordenar(PASAJEROS_VALIDOS, Comparator.<AuxTestPasajeroNoUsar>comparingInt(p -> p.getNumeroId()).reversed())),
                sis.listarPasajerosDescendente().getValorString(),
                "Error con la lista, probablemente tengan un estado intermedio o este mal el agregado en el arbol");
        sis.inicializarSistema(42);//ojo con el reinicio

        Assertions.assertEquals(
                "",
                sis.listarPasajerosDescendente().getValorString(),
                "Error con la lista, probablemente tengan un estado intermedio o este mal el agregado en el arbol");
    }

    @Test
    public void testPasajerosNacionalidad() {
        Sistema sis = tengoUnSistemaConPasajerosValidosRegistrados();
        String resultado = checkearOk(sis.listarPasajerosPorNacionalidad(Nacionalidad.Alemania), "Deberia haber funcionado").getValorString();

        Assertions.assertFalse(resultado.endsWith("|"), "No puede terminar con pipe");

        AuxTestPasajeroNoUsar[] pasajerosAlemanes = new AuxTestPasajeroNoUsar[]{
                pasajero(Nacionalidad.Alemania, 1_232_222_2, "Jorgen", 23, 0),

        };
        chequearListaNacionalidadesCoinciden(resultado, pasajerosAlemanes);

        AuxTestPasajeroNoUsar[] pasajerosConOtraNacionalidad = new AuxTestPasajeroNoUsar[]{
                pasajero(Nacionalidad.Otro, 301_101_0, "Ian", 22, 2),
                pasajero(Nacionalidad.Otro, 332_222_2, "George", 23, 3),
                pasajero(Nacionalidad.Otro, 335_222_2, "George", 23, 4),
                pasajero(Nacionalidad.Otro, 101_111_2, "Robert", 23, 2)
        };
        resultado = checkearOk(sis.listarPasajerosPorNacionalidad(Nacionalidad.Otro), "Deberia haber funcionado").getValorString();
        chequearListaNacionalidadesCoinciden(resultado, pasajerosConOtraNacionalidad);
    }

    private static void chequearListaNacionalidadesCoinciden(String resultado, AuxTestPasajeroNoUsar[] pasajerosEsperados) {
        String[] infoPasajeros = resultado.split("[|]");
        Assertions.assertEquals(pasajerosEsperados.length, infoPasajeros.length, "Las listas tienen que tener el mismo largo");
        for (int i = 0; i < pasajerosEsperados.length; i++) {
            Assertions.assertTrue(resultado.contains(pasajerosEsperados[i].getIdentificador() + ";"),
                    "Esperabamos que estuviera el pasajero '" + pasajerosEsperados[i] + "' en la lista de salida");
            boolean existeElResultadoEnLosEsperados = false;
            String idABuscar = infoPasajeros[i].split(";")[0];
            for (int j = 0; j < pasajerosEsperados.length; j++) {
                if (idABuscar.equals(pasajerosEsperados[j].getIdentificador())) {
                    existeElResultadoEnLosEsperados = true;
                    break;
                }
            }
            Assertions.assertTrue(existeElResultadoEnLosEsperados, "El pasajero de la salida '" + infoPasajeros[i] + "', no esta en los esperados");
        }
    }

    @Test
    public void testBuscarPasajeroIdentificadorNoValidoError() {
        Sistema sis = tengoUnSistemaConPasajerosValidosRegistrados();
        Retorno resultado = sis.buscarPasajero(null);
        AuxAsserciones.checkearError(ERROR_1, resultado, "El identificador es nulo");

        resultado = sis.buscarPasajero("");
        AuxAsserciones.checkearError(ERROR_1, resultado, "El identificador es vacío");
        for (String[] idInvalido : IDENTIFICADORES_INVALIDOS_CON_MENSAJE_ERROR) {
            resultado = sis.buscarPasajero(idInvalido[0]);
            AuxAsserciones.checkearError(ERROR_1, resultado, "El identificador es invalido: " + idInvalido[1]);
        }
    }

    @Test
    public void testBuscarPasajeroNoEncontradoError() {
        Sistema sis = tengoUnSistemaConPasajerosValidosRegistrados();
        Retorno resultado = sis.buscarPasajero(getIdentificadorValido(Nacionalidad.Espania, 7_777_777_7));
        AuxAsserciones.checkearError(ERROR_2, resultado, "El pasajero no existe por lo que deberia dar el codigo de error esperado");
    }


    private Sistema tengoUnSistemaConPasajerosValidosRegistrados() {
        Sistema sistema = tengoUnSistemaValido();
        for (AuxTestPasajeroNoUsar datosPasajero : PASAJEROS_VALIDOS) {
            checkearOk(sistema.registrarPasajero(copy(datosPasajero.getIdentificador()), copy(datosPasajero.getNombre()), datosPasajero.getEdad()), "Se deberia haber registrado correctamente");

        }
        return sistema;
    }

    private Sistema tengoUnSistemaValido() {
        ImplementacionSistema impl = new ImplementacionSistema();
        impl.inicializarSistema(20);
        return impl;
    }

    private static String copy(String txt) {
        return new String(txt);
    }


}
