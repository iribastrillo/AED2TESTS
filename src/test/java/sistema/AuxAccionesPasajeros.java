package sistema;

import interfaz.Nacionalidad;
import interfaz.Retorno;
import interfaz.Sistema;

import static sistema.AuxAsserciones.checkearOk;

public final class AuxAccionesPasajeros {
    public static Retorno seAgregoCorrectamenteUnPasajeroValido(Sistema sistema, Nacionalidad nacionalidad, int numeroId, String nombre, int edad) {
        AuxTestPasajeroNoUsar pasajeroNoUsar = new AuxTestPasajeroNoUsar(nacionalidad, numeroId, nombre, edad);
        return checkearOk(sistema.registrarPasajero(pasajeroNoUsar.getIdentificador(), pasajeroNoUsar.getNombre(), pasajeroNoUsar.getEdad()), String.format("El pasajero '%s' no se registro correctamente.", pasajeroNoUsar));
    }

    public static Retorno agregoUnPasajero(Sistema sistema, Nacionalidad nacionalidad, int numeroId, String nombre, int edad) {
        AuxTestPasajeroNoUsar pasajeroNoUsar = new AuxTestPasajeroNoUsar(nacionalidad, numeroId, nombre, edad);
        return sistema.registrarPasajero(pasajeroNoUsar.getIdentificador(), pasajeroNoUsar.getNombre(), pasajeroNoUsar.getEdad());
    }

    public static Retorno agregoUnPasajeroValidoConElMismoId(Sistema sistema, String identificador, String nombre, int edad) {

        return sistema.registrarPasajero(identificador, nombre, edad);
    }

    public static void agregoPasajerosOk(Sistema sistema, AuxTestPasajeroNoUsar... pasajeros) {
        for (AuxTestPasajeroNoUsar pasajero : pasajeros) {

            checkearOk(sistema.registrarPasajero(pasajero.getIdentificador(), pasajero.getNombre(), pasajero.getEdad()),
                    String.format("Esperaba que el pasajero '%s'[id='%s',edad='%d'] se agregara correctamente",
                            pasajero.getNombre(), pasajero.getIdentificador(), pasajero.getEdad()));

        }
    }
}
