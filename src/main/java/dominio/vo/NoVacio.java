package dominio.vo;

public class NoVacio {
    public static boolean validate (String valor) {
        if (valor == null  || valor.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
