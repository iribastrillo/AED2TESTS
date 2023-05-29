package interfaz;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Esta clase abstrae una consulta
 */
public class Consulta {
    private static final Logger LOGGER = Logger.getLogger(Consulta.class.getName());
    /**
     * La raiz de la consulta
     */
    private NodoConsulta raiz;

    private Consulta(NodoConsulta raiz) {
        Objects.requireNonNull(raiz);
        this.raiz = raiz;
    }

    public NodoConsulta getRaiz() {
        return this.raiz;
    }

    public enum TipoNodoConsulta {
        And("AND", ' ', false),
        Or("OR", ' ', false),
        EdadMayor("edad", '>', true),
        NombreIgual("nombre", '=', false),
        Nacionalidad("nacionalidad", '=', false);
        private String valorStr;
        private char simboloEsperado;
        private boolean esNumerico;

        TipoNodoConsulta(String valorStr, char simboloEsperado, boolean esNumerico) {
            this.valorStr = valorStr;
            this.simboloEsperado = simboloEsperado;
            this.esNumerico = esNumerico;
        }
    }


    public static class NodoConsulta {
        private final TipoNodoConsulta tipoNodoConsulta;
        private final int valorInt;//se usa para la consulta de edad
        private final String valorString; //se usa para la consulta de nombre

        private final Nacionalidad valorNacionalidad; //se usa para la consulta de nacionalidad
        private final NodoConsulta izq;
        private final NodoConsulta der;

        public NodoConsulta(final TipoNodoConsulta tipoNodoConsulta,
            final int valorInt,
            final String valorString,
            final Nacionalidad valorNacionalidad,
            final NodoConsulta izq,
            final NodoConsulta der) {
            this.tipoNodoConsulta = tipoNodoConsulta;
            this.valorInt = valorInt;
            this.valorString = valorString;
            this.izq = izq;
            this.der = der;
            this.valorNacionalidad = valorNacionalidad;
        }

        public NodoConsulta(final TipoNodoConsulta tipoNodoConsulta,
            final NodoConsulta izq,
            final NodoConsulta der) {
            this.tipoNodoConsulta = tipoNodoConsulta;
            this.izq = izq;
            this.der = der;
            valorInt = 0;
            valorNacionalidad = null;
            valorString = "";
        }

        public TipoNodoConsulta getTipoNodoConsulta() {
            return tipoNodoConsulta;
        }

        public int getValorInt() {
            return valorInt;
        }

        public String getValorString() {
            return valorString;
        }

        public Nacionalidad getValorNacionalidad() {
            return valorNacionalidad;
        }

        public NodoConsulta getIzq() {
            return izq;
        }

        public NodoConsulta getDer() {
            return der;
        }
    }

    public Consulta or(Consulta consulta) {
        return or(this, consulta);
    }

    public Consulta and(Consulta consulta) {
        return and(this, consulta);
    }

    public static Consulta and(Consulta c1,
        Consulta c2) {
        return new Consulta(new NodoConsulta(TipoNodoConsulta.And, 0, "", null, c1.raiz, c2.raiz));
    }

    public static Consulta or(Consulta c1,
        Consulta c2) {
        return new Consulta(new NodoConsulta(TipoNodoConsulta.Or, 0, "", null, c1.raiz, c2.raiz));
    }

    public static Consulta edadMayor(int valor) {
        return new Consulta(new NodoConsulta(TipoNodoConsulta.EdadMayor, valor, "", null, null, null));
    }

    public static Consulta nacionalidadIgual(Nacionalidad nacionalidad) {
        return new Consulta(new NodoConsulta(TipoNodoConsulta.Nacionalidad, 0, null,
            Objects.requireNonNull(nacionalidad), null, null));
    }

    public static Consulta nombreIgual(String nombre) {
        return new Consulta(new NodoConsulta(TipoNodoConsulta.NombreIgual, 0, Objects.requireNonNull(nombre),
            null, null, null));
    }

    public String toUrl() {
        StringBuilder sb = new StringBuilder("digraph G{\n");
        toUrl(raiz, null, sb, "R");
        sb.append("}");
        return String.format("https://dreampuf.github.io/GraphvizOnline/#%s",
            sb.toString().replace("\n", "%0A")
                .replace(" ", "%20")
                .replace("#", "%232")
                .replace("\"", "%22")
                .replace("{", "%7B")
                .replace("}", "%7D")
                .replace("[", "%5B")
                .replace("]", "%5D")
                .replace("=", "%3D")
                .replace("-", "%2D")
                .replace("_", "%5F")
                .replace(">", "%3E")
                .replace(";", "%3B"));
    }

    private void toUrl(NodoConsulta nodo, String nombrePadre, StringBuilder sb, String prefix) {
        if (nodo != null) {
            switch (nodo.tipoNodoConsulta) {
                case Or:
                case And:
                    sb.append(String.format("%s [label=\"%s\"];\n", prefix, nodo.tipoNodoConsulta));
                    break;
                case EdadMayor:
                    sb.append(String.format("%s [label=\"%s [%s]\"];\n", prefix, nodo.tipoNodoConsulta, nodo.valorInt));
                    break;
                case NombreIgual:
                    sb.append(String.format("%s [label=\"%s [%s]\"];\n", prefix, nodo.tipoNodoConsulta, nodo.valorString));
                    break;
                case Nacionalidad:
                    sb.append(String.format("%s [label=\"%s [%s]\"];\n", prefix, nodo.tipoNodoConsulta, nodo.valorNacionalidad));
                    break;
                default:
                    sb.append(String.format("%s [label=\"%s\"];\n", prefix, nodo.tipoNodoConsulta));
                    break;
            }
            if (nombrePadre != null) {
                sb.append(String.format("%s -> %s;\n", nombrePadre, prefix));
            }
            toUrl(nodo.izq, prefix, sb, prefix + "_L");
            toUrl(nodo.der, prefix, sb, prefix + "_R");
        }
    }

    @Override
    public String toString() {
        return toString(raiz);
    }

    private String toString(final NodoConsulta nodo) {
        if (nodo == null) {
            return "";
        }
        if (nodo.tipoNodoConsulta == TipoNodoConsulta.And || nodo.tipoNodoConsulta == TipoNodoConsulta.Or) {

            return String.format("[%s] %s [%s]", toString(nodo.izq), nodo.tipoNodoConsulta, toString(nodo.der));
        } else if (nodo.tipoNodoConsulta == TipoNodoConsulta.EdadMayor) {
            return String.format("edad > %s", nodo.valorInt);
        } else if (nodo.tipoNodoConsulta == TipoNodoConsulta.Nacionalidad) {
            return String.format("nacionalidad = '%s'", nodo.valorNacionalidad.getCodigo());
        } else if (nodo.tipoNodoConsulta == TipoNodoConsulta.NombreIgual) {
            return String.format("nombre = '%s'", nodo.valorString);
        } else {
            throw new UnsupportedOperationException("El tipo de nodo no esta reconocido: " + nodo.tipoNodoConsulta);
        }
    }

    /*
     * A PARTIR DE ACA EMPIEZA EL PARSEO DESDE EL TEXTO
     *
     */


    public static void main(String[] args) {
        //Vamos subiendo la complejidad
        System.out.println(Consulta.fromString("edad > 10 OR nacionalidad = 'OT'"));

        System.out.println(Consulta.fromString("[edad >10] AND [nacionalidad ='DE' AND nacionalidad='ES']"));
        System.out.println(Consulta.fromString("[[[[[edad>10]]]] AND [nombre='pepe']]"));
        System.out.println(Consulta.fromString("'pepe'=nombre"));
        System.out.println(Consulta.fromString("[[edad>12 AND nacionalidad='ES' AND nombre='Roberto'] OR " +
            "[edad>   14 AND     ['Raul'    =     nombre OR nombre= 'clotilda']]]"));

        //Estas son claramente invalidas
        System.out.println(Consulta.fromString("[[edad>10] AND nombre='pepe'"));
        System.out.println(Consulta.fromString("invalida][]"));
        System.out.println(Consulta.fromString("'pepe'=nombre AND [10>edad] "));
        System.out.println(Consulta.fromString("[edad>10] nombre='sofia'"));
    }

    // Metodo publico que llama al recursivo
    public static Consulta fromString(String consulta) {
        ResultadoParse<NodoConsulta> resultadoParse = fromStringRec(consulta, null);
        if (resultadoParse == null) {
            return null;
        }
        if (!resultadoParse.resto.isBlank()) {
            return null;//NO se pudo consumir todo el string.
        }
        return new Consulta(resultadoParse.resultado);

    }

    /*
    public int sumaAcumularEnParametro(int [] arr){
     return sumaAcumularEnParametroRec(arr,0,0);
    }
    private int sumaAcumularEnParametro(int [] arr,int desde, int suma){
     if(desde>=arr.length)return suma;
     else return sumaAcumularEnParametro(arr,desde+1,suma+arr[desde]);
    }
     */
    // El metodo recursivo consume el string hasta llegar al final o hasta lo mas que pueda interpretar
    // Acumula en el parametro previo el resultado a devolver o el nodo que fue parseado anteriormente, es igual a lo
    // que vimos con la suma acumulada (ver arriba).
    // Funciona de la siguiente manera, busca reglas de izquierda a derecha, una vez pudo parsear algo lo saca del
    // string y le pasa el resto del computo al paso recursivo
    private static ResultadoParse<NodoConsulta> fromStringRec(String consulta, NodoConsulta previo) {
        if (consulta.isEmpty()) {
            //Caso base consumimos todo el string
            return new ResultadoParse<>(previo, "");
        } else if (consulta.startsWith("]")) {
            //caso base consumimos todo lo que habia dentro de los corchetes, notese que devolvemos como
            // resto el corchete para que el metodo que lo llama valide que haya un corchete que lo abre.
            return new ResultadoParse<>(previo, consulta);
        } else if (consulta.startsWith(" ")) {
            //Ignoramos cualquier espacio en blanco
            return fromStringRec(consulta.substring(1), previo);
        } else if (consulta.startsWith("[")) {
            //Lo que pasa despues del corchete, tiene que ser una regla valida
            //El resto tiene que empezar por un corchete, sino esta mal el resultado
            ResultadoParse<NodoConsulta> resultadoParentesis = fromStringRec(consulta.substring(1), previo);
            if (resultadoParentesis == null) {
                return null;
            }
            if (!resultadoParentesis.resto.startsWith("]")) {
                LOGGER.info("Falto cerrar parentesis en: " + consulta);
                return null;
            }
            return fromStringRec(resultadoParentesis.resto.substring(1), resultadoParentesis.resultado);
        } else if (consulta.startsWith("AND")) {

            // Ya parseamos una regla previo, vimos un simbolo de and. por lo que tendriamos que unir
            // el resultado parseado de la derecha con lo previo que teniamos
            ResultadoParse<NodoConsulta> resultadoDerecha = fromStringRec(consulta.substring("AND".length()), null);
            if (resultadoDerecha == null || previo == null) {
                LOGGER.warning("Resultado de AND invalido parseando: " + consulta);
                return null;
            }
            return fromStringRec(resultadoDerecha.resto, new NodoConsulta(TipoNodoConsulta.And, previo,
                resultadoDerecha.resultado));
        } else if (consulta.startsWith("OR")) {
            // Ya parseamos una regla previo, vimos un simbolo de or. por lo que tendriamos que unir
            // el resultado parseado de la derecha con lo previo que teniamos
            ResultadoParse<NodoConsulta> resultadoDerecha = fromStringRec(consulta.substring("OR".length()), null);
            if (resultadoDerecha == null || previo == null) {
                LOGGER.warning("Resultado de OR invalido parseando: " + consulta);
                return null;
            }

            return fromStringRec(resultadoDerecha.resto, new NodoConsulta(TipoNodoConsulta.Or, previo,
                resultadoDerecha.resultado));
        } else {
            // Si no eran reglas compuestas o corchetes, quiere decir que tiene que haber una regla simple.
            if (previo != null) {
                // Entre reglas simples siempre tiene que haber un OR/AND, sino como combinamos el resultado
                // de lo anterior Con algo que es simple?
                LOGGER.info("Te olvidaste de un AND/OR previo a:" + consulta);
                return null;
            }
            //De las simples tratamos de parsear todas, la primera que no de un vacio es la regla que sirve
            ResultadoParse<NodoConsulta> reglaSimple =
                primeroNoNulo(parsearReglaSimple(TipoNodoConsulta.EdadMayor, consulta),
                    parsearReglaSimple(TipoNodoConsulta.Nacionalidad, consulta),
                    parsearReglaSimple(TipoNodoConsulta.NombreIgual, consulta));

            if (reglaSimple == null) {
                //Si no encontramos ninguna regla simple, esta mal el string
                LOGGER.warning("No pude parsear una regla simple desde: " + consulta);
                return null;
            }
            //Lo que queda es seguir parseando la consulta, sin el texto de la regla simple.
            return fromStringRec(reglaSimple.resto, reglaSimple.resultado);

        }
    }

    //Ignora los espacios hasta encontrar la comita, una vez encontrada busca que se cierre dicha comita.
    private static ResultadoParse<String> parsearValorString(String consulta) {
        if (consulta.startsWith(" ")) {
            return parsearValorString(consulta.substring(1));
        }
        if (consulta.startsWith("'")) {
            int idxSiguienteComita = consulta.indexOf('\'', 1);
            return new ResultadoParse<>(consulta.substring(1, idxSiguienteComita),
                consulta.substring(idxSiguienteComita + 1));
        } else {
            return null;
        }
    }

    //  VA a dar algo valido mientras haya un <espacios>numero[cualquierCosa]
    private static ResultadoParse<Integer> parsearInt(String consulta) {

        if (consulta.startsWith(" ")) {
            return parsearInt(consulta.substring(1));
        } else if (Character.isDigit(consulta.charAt(0))) {
            int valor = 0;
            int indiceCaracter = 0;
            while (Character.isDigit(consulta.charAt(indiceCaracter))) {
                valor = valor * 10 + Integer.parseInt(consulta.charAt(indiceCaracter) + "");
                indiceCaracter++;
            }
            return new ResultadoParse<>(valor,
                consulta.substring(indiceCaracter));
        } else {
            return null;
        }
    }

    //Busca que empiece con el nombre de la regla simple que se pasa por parametro,
    // o que sea <espacios>'texto'<espacios>=<espacios><nombre regla simple>[cualquierCosa]
    private static ResultadoParse<NodoConsulta> parsearReglaSimple(TipoNodoConsulta tipoEsperado, String consulta) {
        if (consulta.startsWith(" ")) {
            return parsearReglaSimple(tipoEsperado, consulta.substring(1));
        }
        if (consulta.startsWith(tipoEsperado.valorStr)) {

            int idxInicio = buscarSimbolo(tipoEsperado, consulta.substring(tipoEsperado.valorStr.length()));
            if (idxInicio < 0) {
                return null;
            }
            idxInicio += tipoEsperado.valorStr.length();
            if (tipoEsperado.esNumerico) {
                ResultadoParse<Integer> valorNumerico = parsearInt(consulta.substring(idxInicio + 1));
                if (valorNumerico == null) {
                    return null;
                }
                return new ResultadoParse<>(new NodoConsulta(tipoEsperado, valorNumerico.resultado, "",
                    null, null, null), valorNumerico.resto);
            } else {
                ResultadoParse<String> texto = parsearValorString(consulta.substring(idxInicio + 1));
                Nacionalidad nacionalidad = Nacionalidad.fromCodigo(texto.resultado);
                if (texto.resultado == null) {
                    return null;
                } else if (tipoEsperado == TipoNodoConsulta.Nacionalidad && nacionalidad == null) {
                    return null;
                }
                return new ResultadoParse<>(new NodoConsulta(tipoEsperado, 0, texto.resultado, nacionalidad,
                    null, null),
                    texto.resto);
            }
        } else if (!tipoEsperado.esNumerico) {
            ResultadoParse<String> texto = parsearValorString(consulta);
            if (texto != null) {

                Nacionalidad nacionalidad = Nacionalidad.fromCodigo(texto.resultado);
                if (tipoEsperado == TipoNodoConsulta.Nacionalidad && nacionalidad == null) {
                    return null;
                }
                int idxSimbolo = buscarSimbolo(tipoEsperado, texto.resto);
                if (idxSimbolo < 0) {
                    return null;
                }
                String restoDespuesSimbolo = texto.resto.substring(idxSimbolo + 1).trim();
                if (restoDespuesSimbolo.startsWith(tipoEsperado.valorStr)) {
                    return new ResultadoParse<>(new NodoConsulta(tipoEsperado, 0, texto.resultado, nacionalidad,
                        null, null), restoDespuesSimbolo.substring(tipoEsperado.valorStr.length()));
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return null;
    }

    private static int buscarSimbolo(final TipoNodoConsulta tipoEsperado,
        final String consulta) {
        int idxInicio = 0;
        while (idxInicio < consulta.length() && consulta.charAt(idxInicio) != tipoEsperado.simboloEsperado) {
            if (consulta.charAt(idxInicio) == ' ') {
                idxInicio++;
            } else {
                LOGGER.info("No pude encontrar el simbolo: " + tipoEsperado.simboloEsperado + " en " + consulta);
                return -1;
            }

        }
        if (idxInicio >= consulta.length()) {
            LOGGER.info("No pude encontrar el simbolo: " + tipoEsperado.simboloEsperado + " en " + consulta);
            return -1;
        }
        return idxInicio;
    }

    private static class ResultadoParse<T> {
        private final String resto;
        private final T resultado;

        public ResultadoParse(
            final T resultado,
            final String resto) {
            this.resto = resto;
            this.resultado = resultado;
        }
    }

    /**
     * Retorna el primero no nulo
     *
     * @param elems Los valores a ver
     * @param <T>   El tipo de dichos valores
     */
    @SafeVarargs
    private static <T> T primeroNoNulo(final T... elems) {
        for (T r : elems
        ) {
            if (r != null) {
                return r;
            }

        }
        return null;
    }
}
