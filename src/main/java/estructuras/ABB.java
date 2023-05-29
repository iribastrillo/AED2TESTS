package estructuras;

import dominio.Pasajero;
import interfaz.Consulta;

public class ABB {

    private Nodo raiz;

    private int contador = 0;

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    private class Nodo {
        Pasajero pasajero;
        Nodo izquierdo;
        Nodo derecho;

        public Nodo(Pasajero pasajero) {
            this.pasajero = pasajero;
            this.izquierdo = null;
            this.derecho = null;
        }

        public Nodo getDerecho() {
            return derecho;
        }

        public Nodo getIzquierdo() {
            return izquierdo;
        }

        public Pasajero getPasajero() {
            return pasajero;
        }

    }

    public void insertar(Pasajero pasajero) {
        raiz = insertarRecursivo(raiz, pasajero);
    }

    private Nodo insertarRecursivo(Nodo nodo, Pasajero pasajero) {
        if (nodo == null) {
            return new Nodo(pasajero);
        }
        if (pasajero.getNro() < nodo.pasajero.getNro()) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, pasajero);
        } else if (pasajero.getNro() > nodo.pasajero.getNro()) {
            nodo.derecho = insertarRecursivo(nodo.derecho, pasajero);
        }
        return nodo;
    }

    public Pasajero buscar(Pasajero pasajero) {
        contador = 0;
        return buscarRecursivo(raiz, pasajero);
    }

    private Pasajero buscarRecursivo(Nodo nodo, Pasajero pasajero) {
        if (nodo == null) {
            return null;
        }

        contador++;

        if (pasajero.equals(nodo.pasajero)) {
            nodo.pasajero.setTiempoDeBusqueda(contador);
            return nodo.pasajero;
        } else if (pasajero.getNro() < nodo.pasajero.getNro()) {
            return buscarRecursivo(nodo.izquierdo, pasajero);
        } else {
            return buscarRecursivo(nodo.derecho, pasajero);
        }
    }

    public void eliminar(Pasajero pasajero) {
        raiz = eliminarRecursivo(raiz, pasajero);
    }

    private Nodo eliminarRecursivo(Nodo nodo, Pasajero pasajero) {
        if (nodo == null) {
            return null;
        }

//        if (pasajero < nodo.pasajero) {
//            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, pasajero);
//        } else if (pasajero > nodo.pasajero) {
//            nodo.derecho = eliminarRecursivo(nodo.derecho, pasajero);
//        } else {
//            if (nodo.izquierdo == null) {
//                return nodo.derecho;
//            } else if (nodo.derecho == null) {
//                return nodo.izquierdo;
//            }
//
//            // nodo.pasajero = obtenerMinimo(nodo.derecho);
//            nodo.derecho = eliminarRecursivo(nodo.derecho, nodo.pasajero);
//        }

        return nodo;
    }

    private int obtenerMinimo(Nodo nodo) {
        //int minimo = nodo.pasajero;
        while (nodo.izquierdo != null) {
            // minimo = nodo.izquierdo.pasajero;
            nodo = nodo.izquierdo;
        }
        return 0;
    }

    public String filtrarPasajeros(Consulta consulta) {
        String valorString = filtrarPasajeros(raiz, consulta);
        return valorString.length() > 1 ? valorString.substring(0, valorString.length()-1) : valorString;
    }

    private String filtrarPasajeros(Nodo nodo, Consulta consulta) {
        if (nodo != null) {
            if (nodo.derecho == null && nodo.izquierdo == null) {
                return filtrar(nodo.pasajero, consulta.getRaiz()) ? nodo.pasajero + "|" : "";
            } else {
                return filtrar(nodo.pasajero, consulta.getRaiz()) ?
                        nodo.pasajero + "|" + filtrarPasajeros(nodo.derecho, consulta) + filtrarPasajeros(nodo.izquierdo, consulta) :
                        filtrarPasajeros(nodo.derecho, consulta) + filtrarPasajeros(nodo.izquierdo, consulta);
            }
        }
        return "";
    }

    private boolean filtrar(Pasajero p, Consulta.NodoConsulta nodo) {
        if(nodo.getTipoNodoConsulta().equals(Consulta.TipoNodoConsulta.EdadMayor)) {
            return p.getEdad()> nodo.getValorInt();
        } else if (nodo.getTipoNodoConsulta().equals(Consulta.TipoNodoConsulta.NombreIgual)) {
            return p.getNombre().equals(nodo.getValorString());
        } else if (nodo.getTipoNodoConsulta().equals(Consulta.TipoNodoConsulta.Nacionalidad)) {
            return p.getNacionalidad().equals(nodo.getValorNacionalidad());
        } else if (nodo.getTipoNodoConsulta().equals(Consulta.TipoNodoConsulta.And)) {
            return filtrar(p, nodo.getIzq()) && filtrar(p, nodo.getDer());
        } else {
            return filtrar(p, nodo.getIzq()) || filtrar(p, nodo.getDer());
        }
    }

    public String imprimirEnOrdenDescendente() {
        String valorString = imprimirEnOrdenDescendente(raiz);
        return valorString.length() > 1 ? valorString.substring(0, valorString.length()-1) : valorString;
    }

    private String imprimirEnOrdenDescendente(Nodo nodo) {
        if (nodo != null) {
            if (nodo.derecho == null) {
                return nodo.pasajero + "|" + imprimirEnOrdenDescendente(nodo.izquierdo);
            } else if (nodo.izquierdo != null) {
                return imprimirEnOrdenDescendente(nodo.derecho)
                        + nodo.pasajero + "|"
                        + imprimirEnOrdenDescendente(nodo.izquierdo);
            } else {
                return imprimirEnOrdenDescendente(nodo.derecho) + nodo.pasajero + "|";
            }
        }
        return "";
    }

    public String imprimirEnOrdenAscendente() {
        String valorString = imprimirEnOrdenAscedente(raiz);
        return valorString.length() > 1 ? valorString.substring(0, valorString.length()-1) : valorString;
    }

    private String imprimirEnOrdenAscedente(Nodo nodo) {
        if (nodo != null) {
            if (nodo.izquierdo == null) {
                return nodo.pasajero + "|" + imprimirEnOrdenAscedente(nodo.derecho);
            } else if (nodo.derecho != null) {
                return imprimirEnOrdenAscedente(nodo.izquierdo)
                        + nodo.pasajero + "|"
                        + imprimirEnOrdenAscedente(nodo.derecho);
            } else {
                return imprimirEnOrdenAscedente(nodo.izquierdo) + nodo.pasajero + "|";
            }
        }
        return "";
    }

    public String imprimirPorNacionalidad(String codigoNacionalidad) {
        StringBuilder mensaje = new StringBuilder();
        imprimirPorNacionalidadRecurisvo(this.raiz, codigoNacionalidad, mensaje);
        return mensaje.toString();
    }

    private String imprimirPorNacionalidadRecurisvo(Nodo nodo, String codigoNacionalidad, StringBuilder mensaje) {
        if (nodo != null) {
            String nacionalidad = nodo.getPasajero().getId().substring(0, 2);

            if (nacionalidad.equals(codigoNacionalidad)) {
                mensaje.append(nodo.getPasajero().getId() + ";"
                        + nodo.getPasajero().getNombre() + ";"
                        + nodo.getPasajero().getEdad() + ";"
                        + nacionalidad + "|");
            }
            imprimirPorNacionalidadRecurisvo(nodo.getIzquierdo(), codigoNacionalidad, mensaje);
            imprimirPorNacionalidadRecurisvo(nodo.getDerecho(), codigoNacionalidad, mensaje);
        }
        return mensaje.toString();
    }
}
