package sistema;

import interfaz.*;

import static sistema.AuxAsserciones.checkearOk;

public class AuxAccionesEstaciones {

    private static String copy(String txt) {
        return new String(txt);
    }

    public static Retorno registroEstacion(Sistema sistema, AuxTestClaseEstacionNoUsar estacion) {
        return sistema.registrarEstacionDeTren(estacion.getCodigo(), estacion.getNombre());
    }

    public static void registroEstacionOk(Sistema sistema, AuxTestClaseEstacionNoUsar... estaciones) {
        for (AuxTestClaseEstacionNoUsar estacion : estaciones) {
            checkearOk(sistema.registrarEstacionDeTren(copy(estacion.getCodigo()), copy(estacion.getNombre())), "La estacion debia de haberse registrado correctamente");
        }
    }

    public static void registrarConexionesOk(Sistema sistema, AuxTestConexionNoUsar... conexionNoUsar) {

        for (AuxTestConexionNoUsar conn : conexionNoUsar) {
            checkearOk(sistema.registrarConexion(copy(conn.getCodOrig()), copy(conn.getCodDest()), conn.getId(), conn.getCosto(), conn.getTiempo(), conn.getKilometros(), conn.getEstado()),
                    String.format("La conexion [%s,%s] debia de haberse registrado correctamente", conn.getCodOrig(), conn.getCodDest()));
        }

    }

    public static void actualizarConexionesOk(Sistema sistema, AuxTestConexionNoUsar... conexionNoUsar) {

        for (AuxTestConexionNoUsar conn : conexionNoUsar) {
            checkearOk(sistema.actualizarCamino(copy(conn.getCodOrig()), copy(conn.getCodDest()), conn.getId(), conn.getCosto(), conn.getTiempo(), conn.getKilometros(), conn.getEstado()),
                    "La conexion debia de haberse actualizado correctamente");
        }

    }

}
