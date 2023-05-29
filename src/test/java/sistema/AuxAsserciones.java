package sistema;

import org.junit.jupiter.api.Assertions;
import interfaz.*;

public class AuxAsserciones {
    public static Retorno checkearOk(Retorno retorno, String mensajeError) {
        Assertions.assertEquals(Retorno.Resultado.OK, retorno.getResultado(), mensajeError);
        return retorno;
    }

    public static Retorno checkearOk(Retorno retorno, String texto, String mensajeError) {
        Assertions.assertEquals(Retorno.Resultado.OK, retorno.getResultado(), mensajeError);
        Assertions.assertEquals(texto, retorno.getValorString(), mensajeError);

        return retorno;
    }

    public static Retorno checkearOk(Retorno retorno, int valor, String texto, String mensajeError) {
        Assertions.assertEquals(Retorno.Resultado.OK, retorno.getResultado(), mensajeError);
        Assertions.assertEquals(texto, retorno.getValorString(), mensajeError);
        Assertions.assertEquals(valor, retorno.getValorInteger(), mensajeError);

        return retorno;
    }

    public static Retorno checkearError(Retorno.Resultado resultado, Retorno retorno, String textoAImprimir) {
        Assertions.assertEquals(resultado, retorno.getResultado(), textoAImprimir);
        return retorno;

    }

    public static Retorno checkearError(Retorno.Resultado resultado, Retorno retorno) {
        Assertions.assertEquals(resultado, retorno.getResultado());
        return retorno;
    }
}
