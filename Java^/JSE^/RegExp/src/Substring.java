import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * Выбрать из строки подстроку, соответствующую регулярному выражению, и распечатать ее.
 */
public class Substring {
    private static final String SOURCE = "GET /abba HTTP/1.1";

    public static void main(String[] args) throws IOException {
        realization1();
        realization2();
        realization3();
    }

    private static void realization1() {
        Pattern p = Pattern.compile("^[^\\s]*\\s([^\\s]*)\\s.*$");
        Matcher m = p.matcher(SOURCE);
        out.println("Matches: " + m.matches());//Без вызова matches() упадет ошибка
        out.println(m.group(1));
    }

    private static void realization2() {
        Pattern p = Pattern.compile("^[^\\s]*\\s([^\\s]*)\\s.*$");
        Matcher m = p.matcher(SOURCE);
        if (m.find()) {
            out.println(m.group(1));
        } else {
            out.println("Not found");
        }
    }

    private static void realization3() {
        String[] splitted = SOURCE.split(" ");
        out.println(splitted[1]);
    }
}