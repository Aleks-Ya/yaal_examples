import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

public class EasyI18nApplication {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Locale english = Locale.ENGLISH;
        Locale russian = new Locale("ru", "RU");
        printMessage(russian);
        printMessage(english);
    }

    private static void printMessage(Locale locale) throws UnsupportedEncodingException {
        ResourceBundle myResources = ResourceBundle.getBundle("Messages", locale);
        String message = new String(myResources.getString("hello").getBytes("ISO-8859-1"), "UTF-8");
        System.out.printf("%s: %s\n", locale.toLanguageTag(), message);
    }
}
