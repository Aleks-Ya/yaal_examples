import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * Поиск нескольких вхождений регулярного выражения в строку.
 */
public class MultiSubstring {
    public static void main(String[] args) {
        final String source = "concert Einaudi ludovico einaudi today ";
        Pattern p = Pattern.compile("[Ee]inaudi");
        Matcher m = p.matcher(source);
        while (m.find()) {
            out.println(m.group());
        }
    }
}