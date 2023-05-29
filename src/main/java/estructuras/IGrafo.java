package estructuras;

import dominio.Estacion;
import dominio.vo.Estado;
import interfaz.EstadoCamino;

public interface IGrafo {
    void agregarVertice(Estacion nombre);
    void agregarArista(Estacion origen, Estacion destino, int identificador, double costo, double distancia, EstadoCamino estado, double tiempo);
    void eliminarVertice(Estacion v);
    void eliminarArista(int origen, int destino);
    void imprimirGrafo();
    boolean existeVertice(Estacion v);
    boolean sonAdyacentes(Estacion a, Estacion b, int identificador);
    Lista<Integer> verticesAdyacentes(Estacion v);
    boolean esVacio();
    boolean estaLlena();

}
