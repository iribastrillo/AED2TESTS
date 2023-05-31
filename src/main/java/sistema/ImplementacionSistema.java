package sistema;

import dominio.Estacion;
import dominio.Pasajero;
import dominio.vo.*;
import estructuras.ABB;
import estructuras.Grafo;
import interfaz.*;
import dominio.vo.Nacionalidad;

public class ImplementacionSistema implements Sistema {
    private int maxEstaciones;
    private int cantidadActual;
    private ABB abbPasajeros;

    public Grafo getConexiones() {
        return conexiones;
    }

    public void setConexiones(Grafo conexiones) {
        this.conexiones = conexiones;
    }

    private Grafo conexiones;
    private int cantidadEstacionesRegistradas = 0;

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public int getMaxEstaciones() {
        return maxEstaciones;
    }

    public void setMaxEstaciones(int maxEstaciones) {
        this.maxEstaciones = maxEstaciones;
    }

    @Override
    public Retorno inicializarSistema(int maxEstaciones) {
        if (maxEstaciones <= 5) {
            return Retorno.error1("E1: la cantidad de estaciones debe ser mayor que 5.");
        }
        setMaxEstaciones(maxEstaciones);
        setCantidadActual(0);
        abbPasajeros = new ABB();
        conexiones = new Grafo(maxEstaciones, true);
        cantidadEstacionesRegistradas = 0;
        return Retorno.ok();
    }

    @Override
    public Retorno registrarPasajero(String identificador, String nombre, int edad) {
        if (identificador == null) {
            return Retorno.error1("E2: El identificador es nulo.");
        }

        if (!NoVacio.validate(nombre) || !NoVacio.validate(identificador)) {
            return Retorno.error1("E1: Alguno de los parametros  vacío.");
        }

        if (!Identificador.validate(identificador)) {
            return Retorno.error2("E2: El identificador no es válido.");
        }

        if (abbPasajeros.buscar(new Pasajero(identificador)) != null) {
            return Retorno.error3("E3: El pasajero ya está registrado.");
        }

        Pasajero pasajero = new Pasajero(identificador, nombre, edad);
        abbPasajeros.insertar(pasajero);
        return Retorno.ok();
    }

    @Override
    public Retorno filtrarPasajeros(Consulta consulta) {
        return consulta == null ?
                Retorno.error1("Consulta no válida") :
                Retorno.ok(abbPasajeros.filtrarPasajeros(consulta));
    }

    @Override
    public Retorno buscarPasajero(String identificador) {
        if (identificador == null) {
            return Retorno.error1("E1: El identificador no puede ser nulo.");
        }
        if (!Identificador.validate(identificador)) {
            return Retorno.error1("E1: El identificador no es válido.");
        }

        Pasajero existe = abbPasajeros.buscar(new Pasajero(identificador));

        if (existe == null) {
            return Retorno.error2("E2: No hay un pasajero registrado con ese identificador.");
        }

        String message = existe.getId() +";"+existe.getNombre()+";"+existe.getEdad()+";"+existe.getNacionalidad();

        return Retorno.ok(existe.getTiempoDeBusqueda(), message);
    }

    @Override
    public Retorno listarPasajerosAscendente() {
        return Retorno.ok(abbPasajeros.imprimirEnOrdenAscendente());
    }

    @Override
    public Retorno listarPasajerosDescendente() {
        return Retorno.ok(abbPasajeros.imprimirEnOrdenDescendente());
    }

    @Override
    public Retorno listarPasajerosPorNacionalidad(interfaz.Nacionalidad nacionalidad) {
        return null;
    }
    public Retorno listarPasajerosPorNacionalidad(String nacionalidad) {
        Nacionalidad isValid = Nacionalidad.fromCodigo(nacionalidad);
        if (nacionalidad == null || nacionalidad.replaceAll("\\s", "").length() == 0) {
            return Retorno.error1("Falta ingresar una nacionalidad, las posibilidades son: FR - DE - ES - UK - OT");
        } else if (isValid != null){
            return Retorno.ok(abbPasajeros.imprimirPorNacionalidad(nacionalidad));
        } else{
            return Retorno.error2("Debe ingresar una nacionalidad valida !");
        }

    }

    @Override
    public Retorno registrarEstacionDeTren(String codigo, String nombre) {
        if (getCantidadActual() == getMaxEstaciones()) {
            return Retorno.error1("E1: Ya se registró el máximo permitido de estaciones");
        }

        if (!NoVacio.validate(nombre)) {
            return Retorno.error2("E2: El nombre de la estación no puede ser vacío.");
        }
        if (!NoVacio.validate(codigo)) {
            return Retorno.error2("E2: El código de la estación no puede ser vacío.");
        }
        if (!Codigo.validate(codigo)) {
            return Retorno.error3("E3: El código de la estación no es válido.");
        }


        Estacion station = new Estacion(codigo, nombre);

        if (conexiones.existeVertice(station)) {
            return Retorno.error4("E4: Ya existe una estación con ese código.");
        }
        cantidadEstacionesRegistradas++;
        setCantidadActual(cantidadEstacionesRegistradas);
        conexiones.agregarVertice(station);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoEstacionOrigen, String codigoEstacionDestino, int identificadorConexion,
                                     double costo, double tiempo, double kilometros, EstadoCamino estadoDeLaConexion) {
        if (costo <= 0 || tiempo <= 0 || kilometros <= 0 || identificadorConexion < 0) {
            return Retorno.error1("E1: Costo, tiempo y kilómetros deben ser mayores que 0.");
        }
        if (!NoVacio.validate(codigoEstacionOrigen) || !NoVacio.validate(codigoEstacionDestino)) {
            return Retorno.error2("E2: El código de origen y destino no pueden ser vacíos.");
        }
        if (estadoDeLaConexion == null) {
            return Retorno.error2("E2: El estado de la conexión no puede ser nulo.");
        }
        if (!Codigo.validate(codigoEstacionOrigen)) {
            return Retorno.error3("E2: El código de origen no es válido.");
        }
        if (!Codigo.validate(codigoEstacionDestino)) {
            return Retorno.error3("E2: El código de destino no es válido.");
        }

        Estacion origin = new Estacion(codigoEstacionOrigen);
        Estacion destination = new Estacion(codigoEstacionDestino);

        if (!this.conexiones.existeVertice(origin)) {
            return Retorno.error4("E4: No existe la estación de origen.");
        }

        if (!this.conexiones.existeVertice(destination)) {
            return Retorno.error5("E5: No existe la estación de destino.");
        }

        if (this.conexiones.sonAdyacentes(origin, destination, identificadorConexion)) {
            return Retorno.error6("E6: La conexión ya existe.");
        }

        conexiones.agregarArista(origin, destination, identificadorConexion, costo, kilometros,
                estadoDeLaConexion, tiempo);

        return Retorno.ok();
    }

    @Override
    public Retorno actualizarCamino(String codigoEstacionOrigen, String codigoEstacionDestino, int identificadorConexion,
                                    double costo, double tiempo, double kilometros, EstadoCamino estadoDelCamino) {
        if (costo <= 0 || tiempo <= 0 || kilometros <= 0 || identificadorConexion < 0) {
            return Retorno.error1("E1: Costo, tiempo y kilómetros deben ser mayores que 0.");
        }
        if (!NoVacio.validate(codigoEstacionOrigen) || !NoVacio.validate(codigoEstacionDestino)) {
            return Retorno.error2("E2: El código de origen y destino no pueden ser vacíos.");
        }
        if (estadoDelCamino == null) {
            return Retorno.error2("E2: El estado de la conexión no puede ser nulo.");
        }
        if (!Codigo.validate(codigoEstacionOrigen)) {
            return Retorno.error3("E2: El código de origen no es válido.");
        }
        if (!Codigo.validate(codigoEstacionDestino)) {
            return Retorno.error3("E2: El código de destino no es válido.");
        }

        Estacion origin = new Estacion(codigoEstacionOrigen);
        Estacion destination = new Estacion(codigoEstacionDestino);

        if (!this.conexiones.existeVertice(origin)) {
            return Retorno.error4("E4: No existe la estación de origen.");
        }

        if (!this.conexiones.existeVertice(destination)) {
            return Retorno.error5("E4: No existe la estación de destino.");
        }

        if (this.conexiones.sonAdyacentes(origin, destination, identificadorConexion)) {
            conexiones.actualizarArista(origin, destination, identificadorConexion, costo, kilometros, estadoDelCamino, tiempo);
            return Retorno.ok();
        } else {
            return Retorno.error6("E6: No existe la conexión entre el origen y el destino.");
        }
    }

    @Override
    public Retorno listadoEstacionesCantTrasbordos(String codigo, int cantidad) {
        if (cantidad < 0) {
            return Retorno.error1("E1: La cantidad no puede ser menor a cero.");
        }
        if (!NoVacio.validate(codigo)) {
            return Retorno.error2("E2: El código no puede ser vacío.");
        }
        if (!Codigo.validate(codigo)) {
            return Retorno.error3("E3: El código de la estación no es válido.");
        }

        Estacion station = new Estacion(codigo);

        if (!this.conexiones.existeVertice(station)) {
            return Retorno.error4("E4: No existe la estación de origen.");
        }

        return Retorno.ok(conexiones.bfsCantidadDeTrasbordos(codigo, cantidad).toString());
    }

    @Override
    public Retorno viajeCostoMinimoKilometros(String codigoEstacionOrigen, String codigoEstacionDestino) {
        if (!NoVacio.validate(codigoEstacionOrigen) || !NoVacio.validate(codigoEstacionDestino)) {
            return Retorno.error1("E2: El código de origen y destino no pueden ser vacíos.");
        }
        if (!NoVacio.validate(codigoEstacionDestino)) {
            return Retorno.error2("E2: El código de destino no es válido.");
        }
        if (!Codigo.validate(codigoEstacionOrigen)) {
            return Retorno.error2("E2: El código de origen no es válido.");
        }

        if (!conexiones.dfs(codigoEstacionOrigen, codigoEstacionDestino)) {
            return Retorno.error3("E3: No hay un camino entre el origen y el destino");
        }

        Estacion origin = new Estacion(codigoEstacionOrigen);
        Estacion destination = new Estacion(codigoEstacionDestino);

        if (!this.conexiones.existeVertice(origin)) {
            return Retorno.error4("E4: No existe la estación de origen.");
        }

        if (!this.conexiones.existeVertice(destination)) {
            return Retorno.error5("E5: No existe la estación de destino.");
        }
        return Retorno.ok(conexiones.costoMinKm(codigoEstacionOrigen, codigoEstacionDestino));
    }

    @Override
    public Retorno viajeCostoMinimoEuros(String codigoEstacionOrigen, String codigoEstacionDestino) {
        return null;
    }

    public ABB getAbbPasajeros() {
        return abbPasajeros;
    }

    public void setAbbPasajeros(ABB abbPasajeros) {
        this.abbPasajeros = abbPasajeros;
    }
}
