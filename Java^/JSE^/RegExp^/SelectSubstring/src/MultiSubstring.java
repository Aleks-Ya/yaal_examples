import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class MultiSubstring {
    public static void main(String[] args) throws IOException {
        final String source = "concert einaudi ludovico einaudi today ";
        Pattern p = Pattern.compile("einaudi");
        Matcher m = p.matcher(source);
        while(m.find()) {
            out.println(m.group());
        }
    }
}