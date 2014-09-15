import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * Соответствует ли целая строка регулярному выражению.
 */
public class IsMatch {
    private static final String REGEX = "^\\w+\\s*:\\s*\\d+$";
    private static final String SOURCE_MATCH = "width  : 600";
    private static final String SOURCE_NOT_MATCH = "alwaysTooltips: false";

    public static void main(String[] args) {
        realization1();
        realization2();
    }

    private static void realization1() {
        Pattern p = Pattern.compile(REGEX);

        Matcher m1 = p.matcher(SOURCE_MATCH);
        out.println("Matches 1: " + m1.matches());

        Matcher m2 = p.matcher(SOURCE_NOT_MATCH);
        out.println("Matches 1: " + m2.matches());
    }

    private static void realization2() {
        out.println("Matches 2: " + Pattern.matches(REGEX, SOURCE_MATCH));
        out.println("Matches 2: " + Pattern.matches(REGEX, SOURCE_NOT_MATCH));
    }
}