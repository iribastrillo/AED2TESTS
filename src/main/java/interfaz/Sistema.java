package interfaz;

/**
 * Provee una interfaz para interactuar con el sistema
 */
public interface Sistema {
    /**
     * @param maxEstaciones La cantidad máxima de estaciones.
     * @return Ok
     */
    Retorno inicializarSistema(int maxEstaciones);


    /**
     * Registra el pasajero en el sistema
     *
     * @param identificador El identificador del pasajero
     * @param nombre        El nombre del pasajero
     */
    Retorno registrarPasajero(String identificador, String nombre, int edad);

    /**
     * Nos devuelve los pasajeros que cumplen la condicion especificada en la consulta
     *
     * @param consulta La consulta a aplicar
     */
    Retorno filtrarPasajeros(Consulta consulta);

    /**
     * Busca un pasajero en el sistema por cedula
     *
     * @param identificador El identificador del pasajero.
     */
    Retorno buscarPasajero(String identificador);

    /**
     * @return Los pasajeros listados por identificador ascendente
     */
    Retorno listarPasajerosAscendente();

    /**
     * @return Los pasajeros listados por identificador descendente
     */
    Retorno listarPasajerosDescendente();

    /**
     * @param nacionalidad La nacionalidad de un pasajero.
     * @return Los pasajeros de un tipo especifico.
     */
    Retorno listarPasajerosPorNacionalidad(Nacionalidad nacionalidad);

    /**
     * @param codigo El codigo de la estacion a registrar.
     * @param nombre El nombre de la estacion a registrar.
     */
    Retorno registrarEstacionDeTren(String codigo, String nombre);

    ;

    /**
     * Registra un camino en el sistema
     *
     * @param codigoEstacionOrigen  El codigo de la estacion de origen.
     * @param codigoEstacionDestino El codigo de la estacion de destino.
     * @param costo                 El costo en euros.
     * @param tiempo                El tiempo del camino.
     * @param kilometros            Los kilometros del camino.
     * @param estadoDeLaConexion    El estado del camino.
     */
    Retorno registrarConexion(String codigoEstacionOrigen, String codigoEstacionDestino, int identificadorConexion,
                              double costo, double tiempo, double kilometros, EstadoCamino estadoDeLaConexion);

    /**
     * Actualiza un camino en el sistema.
     *
     * @param codigoEstacionOrigen  El codigo de la estacion de origen.
     * @param codigoEstacionDestino El codigo de la estacion de destino.
     * @param costo                 El costo en euros del camino.
     * @param tiempo                El tiempo del camino.
     * @param kilometros            Los kilometros del camino.
     * @param estadoDelCamino       El estado del camino.
     */
    Retorno actualizarCamino(String codigoEstacionOrigen, String codigoEstacionDestino, int identificadorConexion,
                             double costo, double tiempo, double kilometros, EstadoCamino estadoDelCamino);

    /**
     * @param codigo   El codigo de la estacion de origen.
     * @param cantidad La cantidad de saltos maxima
     * @return Las estaciones a las que puede llegar con hasta cantidad de trasbordos.
     */
    Retorno listadoEstacionesCantTrasbordos(String codigo, int cantidad);

    /**
     * @param codigoEstacionOrigen  El codigo de la estacion de origen.
     * @param codigoEstacionDestino El codigo de la estacion de destino.
     * @return El camino si lo hay entre el origen y el destino con los menores kilometros posibles
     */
    Retorno viajeCostoMinimoKilometros(String codigoEstacionOrigen, String codigoEstacionDestino);

    /**
     * @param codigoEstacionOrigen  El codigo de la estacion de origen.
     * @param codigoEstacionDestino El codigo de la estacion de destino.
     * @return El camino si lo hay entre el origen y el destino con el menor costo en euros posible
     */
    Retorno viajeCostoMinimoEuros(String codigoEstacionOrigen, String codigoEstacionDestino);

}
