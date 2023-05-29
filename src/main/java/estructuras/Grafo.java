package estructuras;

import dominio.Estacion;
import dominio.vo.Estado;
import interfaz.EstadoCamino;

public class Grafo implements IGrafo {
    private final int cantMaxVertices;
    private int cantVertices;
    private boolean esDirigido;
    private Lista<Arista>[][] matrizAdyacencia;
    private Vertice[] vertices;

    public Grafo(int cantMaxVertices, boolean esDirigido) {
        this.esDirigido = esDirigido;
        this.cantMaxVertices = cantMaxVertices;
        this.matrizAdyacencia = new Lista[cantMaxVertices][cantMaxVertices];
        this.vertices = new Vertice[cantMaxVertices];

        for (int i = 0; i < cantMaxVertices; i++) {
            for (int j = 0; j < cantMaxVertices; j++) {
                matrizAdyacencia[i][j] = new Lista<>();
            }
        }
    }

    @Override
    public void agregarVertice(Estacion estacion) {
        cantVertices++;
        int posLibre = obtenerPosLibre();
        if (posLibre >= 0) {
            vertices[posLibre] = new Vertice(estacion);
        }
    }

    private int obtenerPosLibre() {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPos(Estacion vert) {
        for (int i = 0; i < cantMaxVertices; i++) {
            if (vertices[i] != null && vertices[i].equals(vert))
                return i;
        }
        return -1;
    }

    @Override
    public void agregarArista(Estacion origen, Estacion destino, int identificador, double costo, double distancia, EstadoCamino estado, double tiempo) {
        int obtenerPosOrigen = obtenerPos(origen);
        int obtenerPosDestino = obtenerPos(destino);
        if (obtenerPosOrigen >= 0 && obtenerPosDestino >= 0) {
            Lista<Arista> aristas = matrizAdyacencia[obtenerPosOrigen][obtenerPosDestino];
            aristas.insertar(new Arista(origen.getCodigo(), destino.getCodigo(), costo, distancia,
                    estado, tiempo, identificador));
            if (!esDirigido) {
                matrizAdyacencia[obtenerPosDestino][obtenerPosOrigen] = aristas;
            }
        }
    }

    public void actualizarArista (Estacion origen, Estacion destino, int identificador, double costo,
                                  double distancia, EstadoCamino estado, double tiempo) {
        int obtenerPosOrigen = obtenerPos(origen);
        int obtenerPosDestino = obtenerPos(destino);
        if (obtenerPosOrigen >= 0 && obtenerPosDestino >= 0) {
            Lista<Arista> aristas = matrizAdyacencia[obtenerPosOrigen][obtenerPosDestino];
            Arista conexion = aristas.recuperar(new Arista(identificador));
            if (conexion != null) {
                conexion.costo = costo;
                conexion.distancia = distancia;
                conexion.estado = estado;
                conexion.tiempo = tiempo;
                if (!esDirigido) {
                    matrizAdyacencia[obtenerPosDestino][obtenerPosOrigen] = aristas;
                }
            }
            System.out.println(aristas);
        }
    }

    @Override
    public void eliminarVertice(Estacion vert) {
        cantVertices--;
        int posVert = obtenerPos(vert);
        if (posVert >= 0) {
            vertices[posVert] = null;
            for (int i = 1; i <= cantMaxVertices; i++) {
                matrizAdyacencia[posVert][i] = new Lista<>();
                matrizAdyacencia[i][posVert] = new Lista<>();
            }
        }
    }

    @Override
    public void eliminarArista(int origen, int destino) {}

    @Override
    public boolean existeVertice(Estacion vert) {
        return obtenerPos(vert) >= 0;
    }

    @Override
    public boolean sonAdyacentes(Estacion a, Estacion b, int identificador) {
        int obtenerPosOrigen = obtenerPos(a);
        int obtenerPosDestino = obtenerPos(b);
        if (obtenerPosOrigen >= 0 && obtenerPosDestino >= 0) {
            Lista <Arista> aristas = matrizAdyacencia[obtenerPosOrigen][obtenerPosDestino];
            if (!aristas.esVacia()) {
                Lista.Nodo aux = aristas.getInicio();
                while (aux != null) {
                    Arista arista = (Arista) aux.dato;
                    if (arista.origen == a.getCodigo() && arista.destino == b.getCodigo() && arista.identificador == identificador) {
                        return true;
                    }
                    aux = aux.siguiente;
                }
            }
            return false;
        } else {
            return false;
        }
    }
    @Override
    public Lista<Integer> verticesAdyacentes(Estacion v) {
        Lista<Integer> verticesAdy = new Lista<Integer>();
        int obtenerPosOrigen = obtenerPos(v);
//        if (obtenerPosOrigen >= 0) {
//            for (int i = 0; i < cantMaxVertices; i++) {
//                if (sonAdyacentes(v, vertices[i].dato)) {
//                    verticesAdy.insertar(i);
//                }
//            }
//        }
        return verticesAdy;
    }
    @Override
    public void imprimirGrafo() {

        System.out.println("Información del Grafo:");
        System.out.println("Cantidad máxima de vértices: " + cantMaxVertices);
        System.out.println("Es dirigido: " + esDirigido);
        System.out.println("Matriz de adyacencia:");
        // Imprimir encabezado de columnas
        System.out.print("\t");
        for (int i = 0; i < cantMaxVertices; i++) {
            System.out.print(i + "\t");
        }
        System.out.println();

        // Imprimir filas y valores de la matriz de adyacencia
        for (int i = 0; i < cantMaxVertices; i++) {
            System.out.print(i + "\t");
            for (int j = 0; j < cantMaxVertices; j++) {
                Lista<Arista> aristas = matrizAdyacencia[i][j];
                if (!aristas.esVacia()) {
                    System.out.println(aristas + "\t");
                } else {
                    System.out.print("0" + "\t");
                }
            }
            System.out.println();
        }

        System.out.println("Vértices:");
        for (int i = 0; i < cantMaxVertices; i++) {
            Vertice vertice = vertices[i];
            if (vertice != null) {
                System.out.println(i + ": " + vertice.getNombre());
            }
        }
    }
    @Override
    public boolean esVacio() {
        return cantVertices == 0;
    }

    @Override
    public boolean estaLlena() {
        return cantVertices == cantMaxVertices;
    }

    public Lista bfsCantidadDeTrasbordos (String codigo, int cantidad) {
        int posicion = obtenerPos(new Estacion(codigo));
        int start = 0;
        Lista<String> stations = new Lista<>();
        boolean[] visitados = new boolean[cantMaxVertices];
        Queue<Tupla> cola = new Queue<>();
        visitados[posicion] = true;
        cola.enqueue(new Tupla(posicion, start));
        while (!cola.isEmpty() && cantidad >= start) {
            Tupla current = cola.dequeue();
            Vertice vertex = vertices[current.pos];
            Estacion station = vertex.dato;
            stations.insertar(station.toString() + " " + current.salto + " |");
            for (int i = 0; i < cantMaxVertices; i++) {
                if (!matrizAdyacencia[current.pos][i].esVacia() && !visitados[i]) {
                    cola.enqueue(new Tupla (i, current.salto + 1));
                    visitados[i] = true;
                }
            }
            start ++;
        }
        return stations;
    }

    private class Vertice {
        private Estacion dato;

        public Vertice(Estacion estacion) {
            this.dato = estacion;
        }
        public Vertice(String nombre) {this.dato = new Estacion(nombre); }

        public String getNombre() {
            return dato.getNombre();
        }

        public void setNombre(String nombre) {
            this.dato.setNombre(nombre);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            Estacion vertice = (Estacion) o;
            return this.dato.equals(vertice);
        }
    }

    private class Arista implements Comparable<Arista> {
        public String origen;
        public String destino;
        public boolean existe;
        public double costo;
        public double distancia;
        public EstadoCamino estado;
        public double tiempo;
        public int identificador;
        public Arista (int identificador) {
            this.identificador = identificador;
        }
        public Arista(String codigoOrigen, String codigoDestino, double costo, double distancia, EstadoCamino estado, double tiempo, int identificador) {
            this.origen = codigoOrigen;
            this.destino = codigoDestino;
            this.costo = costo;
            this.distancia = distancia;
            this.existe = true;
            this.estado = estado;
            this.tiempo = tiempo;
            this.identificador = identificador;
        }

        public Arista() {
        }

        public boolean isExiste() {
            return this.existe;
        }

        public void setExiste(boolean existe) {
            this.existe = existe;
        }

        public double getCosto() {
            return costo;
        }

        public void setCosto(int costo) {
            this.costo = costo;
        }

        public double getDistancia() {
            return distancia;
        }

        public void setDistancia(int distancia) {
            this.distancia = distancia;
        }

        @Override
        public boolean equals(Object obj) {
            Arista otra = (Arista) obj;
            return this.identificador == otra.identificador;
        }

        @Override
        public int compareTo (Arista o) {
            if (this.identificador == o.identificador) return 1;
            else return -1;
        }

        @Override
        public String toString() {
            return Double.toString(costo);
        }
    }

    private class Tupla {
        public int pos;
        public int salto;
        public Tupla (int pos, int salto) {
            this.pos = pos;
            this.salto = salto;
        }
    }

}
