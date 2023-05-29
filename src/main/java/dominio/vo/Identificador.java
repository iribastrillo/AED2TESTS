package dominio.vo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Identificador {
    public static boolean validate(String valor) {
        Pattern pattern_one = Pattern.compile("^(FR|DE|UK|ES|OT){1}[1-9]{1}\\.[0-9]{3}\\.[0-9]{3}#\\d{1}$");
        Pattern pattern_two = Pattern.compile("^(FR|DE|UK|ES|OT){1}[1-9]{1}[0-9]{2}\\.[0-9]{3}#\\d{1}$");
        Matcher m1 = pattern_one.matcher(valor);
        Matcher m2 = pattern_two.matcher(valor);
        boolean found = m1.find() || m2.find();
        return found;
    }

    public static int getNumber(String input) {
        int number = 0;

        int endIndex = input.indexOf('#');
        if (endIndex != -1) {
            String numberString = input.substring(0, endIndex);
            numberString = numberString.replaceAll("[^\\d]", "");
            if (!numberString.isEmpty()) {
                number = Integer.parseInt(numberString);
            }
        }

        return number;
    }
}
