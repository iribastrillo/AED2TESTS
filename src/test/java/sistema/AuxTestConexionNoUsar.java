package sistema;

import interfaz.EstadoCamino;

public class AuxTestConexionNoUsar {
    private AuxTestClaseEstacionNoUsar origen;
    private AuxTestClaseEstacionNoUsar destino;
    private int id;
    private double costo, tiempo, kilometros;
    private EstadoCamino estado;


    public AuxTestConexionNoUsar(AuxTestClaseEstacionNoUsar origen, AuxTestClaseEstacionNoUsar destino, int id, double costo, double tiempo, double kilometros, EstadoCamino estado) {
        this.origen = origen;
        this.destino = destino;
        this.id = id;
        this.costo = costo;
        this.tiempo = tiempo;
        this.kilometros = kilometros;
        this.estado = estado;
    }

    public static AuxTestConexionNoUsar conexion(AuxTestClaseEstacionNoUsar origen, AuxTestClaseEstacionNoUsar destino, int id) {

        return new AuxTestConexionNoUsar(origen, destino, id, 1, 1, 1, EstadoCamino.BUENO);
    }

    public static AuxTestConexionNoUsar conexion(AuxTestClaseEstacionNoUsar origen, AuxTestClaseEstacionNoUsar destino, int id, double costo, double tiempo, double kilometros, EstadoCamino estado) {
        return new AuxTestConexionNoUsar(origen, destino, id, costo, tiempo, kilometros, estado);
    }

    public static AuxTestConexionNoUsar conexion(String origen, String destino, int id, double costo, double tiempo, double kilometros, EstadoCamino estado) {
        return new AuxTestConexionNoUsar(AuxTestClaseEstacionNoUsar.estacion(origen, ""), AuxTestClaseEstacionNoUsar.estacion(destino, ""), id, costo, tiempo, kilometros, estado);
    }

    public String getCodOrig() {
        return origen.getCodigo();
    }

    public String getCodDest() {
        return destino.getCodigo();
    }

    public AuxTestClaseEstacionNoUsar getOrigen() {
        return origen;
    }

    public void setOrigen(AuxTestClaseEstacionNoUsar origen) {
        this.origen = origen;
    }

    public AuxTestClaseEstacionNoUsar getDestino() {
        return destino;
    }

    public void setDestino(AuxTestClaseEstacionNoUsar destino) {
        this.destino = destino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public double getKilometros() {
        return kilometros;
    }

    public void setKilometros(double kilometros) {
        this.kilometros = kilometros;
    }

    public EstadoCamino getEstado() {
        return estado;
    }

    public void setEstado(EstadoCamino estado) {
        this.estado = estado;
    }

    /**
     * Crea una copia con el nuevo estado de camino.
     *
     * @param estado Estado nuevo
     * @return una copia actualizada.
     */
    public AuxTestConexionNoUsar withEstado(EstadoCamino estado) {
        return new AuxTestConexionNoUsar(origen, destino, id, costo, tiempo, kilometros, estado);
    }

    public AuxTestConexionNoUsar withKm(double km) {
        return new AuxTestConexionNoUsar(origen, destino, id, costo, tiempo, km, estado);
    }

    public AuxTestConexionNoUsar withCosto(double costo) {

        return new AuxTestConexionNoUsar(origen, destino, id, costo, tiempo, kilometros, estado);
    }

    public AuxTestConexionNoUsar withTiempo(double tiempo) {

        return new AuxTestConexionNoUsar(origen, destino, id, costo, tiempo, kilometros, estado);
    }

    public AuxTestConexionNoUsar withId(int id) {
        return new AuxTestConexionNoUsar(origen, destino, id, costo, tiempo, kilometros, estado);
    }
}
