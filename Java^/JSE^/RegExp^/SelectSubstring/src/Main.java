import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        final String source = "GET /abba HTTP/1.1";
        {
            Pattern pattern = Pattern.compile("^[^\\s]*\\s([^\\s]*)\\s.*$");
            Matcher matcher = pattern.matcher(source);
            matcher.matches();
            System.out.println(matcher.group(1));
        }
        {
            String[] splitted = source.split(" ");
            System.out.println(splitted[1]);
        }
    }
}
