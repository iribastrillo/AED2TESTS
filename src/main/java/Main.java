import interfaz.EstadoCamino;
import interfaz.Consulta;
import sistema.ImplementacionSistema;

public class Main {
    public static void main(String args[]) {
        ImplementacionSistema sistema = new ImplementacionSistema();
        System.out.println(sistema.inicializarSistema(9));

        System.out.println("----------TESTEO METODO registrarPasajero----------" + "\n");

        System.out.println(sistema.registrarPasajero("FR123.123#3", "Ignacio", 30));
        System.out.println(sistema.registrarPasajero("FR524.155#1", "Jimena", 29));
        System.out.println(sistema.registrarPasajero("DE724.187#1", "Pedro", 28));
        System.out.println(sistema.registrarPasajero("UK805.978#8", "Juan", 54));
        System.out.println(sistema.registrarPasajero("FR123.123#3", "Alicia", 35));
        System.out.println(sistema.registrarPasajero("DER23.12353", "Roberto", 42));
        System.out.println(sistema.registrarPasajero("UK505.988#7", "pepe", 42));
        System.out.println(sistema.registrarPasajero("UK400.111#2", "Viviana", 42));
        System.out.println(sistema.registrarPasajero("", "Federico", 42));

        System.out.println("\n" + "----------TESTEO METODO buscarPasajero----------" + "\n");

        System.out.println(sistema.buscarPasajero("FR123.123#3"));
        System.out.println(sistema.buscarPasajero("UK805.978#8"));
        System.out.println(sistema.buscarPasajero("ES134.167#3"));
        System.out.println(sistema.buscarPasajero("FRR34.16783"));

        System.out.println("\n" + "----------TESTEO METODO registrarEstacionDeTren----------" + "\n");

        System.out.println(sistema.registrarEstacionDeTren("MMM123", "MADRID"));
        System.out.println(sistema.registrarEstacionDeTren("FFF123", "PARIS"));
        System.out.println(sistema.registrarEstacionDeTren("AAA143", "DORTMUND"));
        System.out.println(sistema.registrarEstacionDeTren("ABQ143", "NANTES"));
        System.out.println(sistema.registrarEstacionDeTren("QZY143", "LONDRES"));
        System.out.println(sistema.registrarEstacionDeTren("KJL589", "BERLIN"));
        System.out.println(sistema.registrarEstacionDeTren("UYT896", "OSLO"));
        System.out.println(sistema.registrarEstacionDeTren("AEA143", "MANCHESTER"));
        System.out.println(sistema.registrarEstacionDeTren("ZZZ258", "MONTPELLIER"));

        System.out.println("\n" + "----------TESTEO METODO registrarEstacionDeTren----------" + "\n");

        System.out.println(sistema.registrarEstacionDeTren("MMM123", "MADRID")); //
        System.out.println(sistema.registrarEstacionDeTren("LLL00", "MALAGA")); //
        System.out.println(sistema.registrarEstacionDeTren("AAA1234", "BARCELONA")); //
        System.out.println(sistema.registrarEstacionDeTren("A♪B456", "VALENCIA")); //
        System.out.println(sistema.registrarEstacionDeTren("", "NIZA")); //

        System.out.println("\n" + "----------TESTEO METODO filtrarPasajeros----------" + "\n");

        Consulta consulta = Consulta.fromString("[nombre='Ignacio' OR edad > 10]");
        Consulta consulta2 = Consulta.fromString("[nacionalidad='UK']");
//        Consulta consulta3 = Consulta.fromString("");
        System.out.println(sistema.filtrarPasajeros(consulta));
        System.out.println(sistema.filtrarPasajeros(consulta2));
//        System.out.println(sistema.filtrarPasajeros(consulta3));

        System.out.println("\n" + "----------TESTEO METODO listarPasajerosPorNacionalidad----------" + "\n");

        System.out.println(sistema.listarPasajerosPorNacionalidad("UK"));

        System.out.println("\n" + "----------TEST: Registrar conexión  ----------" + "\n");

        System.out.println(sistema.registrarConexion("MMM123", "FFF123",
                1, 1000, 50, 50, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("MMM123", "FFF123",
                10, 50, 50, 100, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("FFF123", "AAA143",
                2, 3, 100, 200, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("AAA143", "QZY143",
                11, 30, 60, 30, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("QZY143", "KJL589",
                12, 70, 120, 230, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("KJL589", "UYT896",
                13, 40, 80, 170, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("QZY143", "AEA143",
                14, 60, 90, 130, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("ZZZ258", "MMM123",
                15, 40, 90, 160, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("UYT896", "MMM123",
                16, 50, 50, 170, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("AAA143", "ABQ143",
                7, 50, 50, 50, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("KJL589", "UYT896",
                8, 50, 50, 50, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("MMM123", "FFF123",
                9, 50, 50, 50, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("ABQ143", "MMM123",
                17, 50, 50, 30, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("ZZZ258", "ABQ143",
                18, 50, 50, 50, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("AEA143", "ZZZ258",
                18, 50, 50, 50, EstadoCamino.BUENO));

        System.out.println("\n" + "----------TEST: ERROR-Registrar conexión  ----------" + "\n");

        System.out.println(sistema.registrarConexion("RRR123", "FFF123",
                3, 50, 50, 50, EstadoCamino.BUENO)); //
        System.out.println(sistema.registrarConexion("MMM123", "WWW123",
                4, 50, 50, 50, EstadoCamino.BUENO)); //

        System.out.println("\n" + "----------TEST: Actualizar camino  ----------" + "\n");

        sistema.getConexiones().imprimirGrafo();

        System.out.println(sistema.actualizarCamino("MMM123", "FFF123",
                1, 1, 30, 30, EstadoCamino.BUENO));

        sistema.getConexiones().imprimirGrafo();

        System.out.println("\n" + "----------TEST: BFS  ----------" + "\n");

        System.out.println(sistema.listadoEstacionesCantTrasbordos("MMM123", 4));

        System.out.println("\n" + "----------TEST: Dijsktra  ----------" + "\n");

        System.out.println(sistema.viajeCostoMinimoKilometros("MMM123", "FFF123"));
        System.out.println(sistema.viajeCostoMinimoKilometros("AEA143", "ABQ143"));


        System.out.println("\n" + ">>> PRIMERO SIN CAMINO : PRUEBA ERROR 3  ----------" + "\n");
        System.out.println(sistema.viajeCostoMinimoKilometros("MMM123", "ZZZ258"));
        System.out.println(sistema.registrarConexion("AAA143", "ZZZ258",
                12, 1, 30, 30, EstadoCamino.BUENO));
        System.out.println(sistema.registrarConexion("MMM123", "AAA143",
                13, 1, 30, 30, EstadoCamino.BUENO));



        System.out.println("\n" + ">>> TRAS AGREGAR ESTACIONES INTERMEDIAS ----------" + "\n");
        System.out.println(sistema.viajeCostoMinimoKilometros("MMM123", "ZZZ258"));
    }
}
