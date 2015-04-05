import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * Замена вхождения регулярного выражения в строку подстрокой.
 */
public class SubstringReplace {
    private static final String SOURCE = "concert Einaudi ludovico einaudi today ";
    private static final String REGEX = "[Ee]inaudi";
    private static final String REPLACER = "Tankian";

    public static void main(String[] args) {
        realization1();
        realization2();
    }

    private static void realization1() {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(SOURCE);
        out.println("1: " + m.replaceAll(REPLACER));

        out.println("1: " + Pattern.compile(REGEX).matcher(SOURCE).replaceAll(REPLACER));
    }

    private static void realization2() {
        out.println("2: " + SOURCE.replaceFirst(REGEX, REPLACER));
        out.println("2: " + SOURCE.replaceAll(REGEX, REPLACER));
    }
}