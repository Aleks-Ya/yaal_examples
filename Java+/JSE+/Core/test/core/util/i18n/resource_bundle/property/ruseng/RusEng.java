package core.util.i18n.resource_bundle.property.ruseng;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Вывод сообщения на русском или английском языке с помощью ResourceBundle.
 */
public class RusEng {

    @Test
    public void main() throws UnsupportedEncodingException {
        Locale english = Locale.ENGLISH;
        Locale russian = new Locale("ru", "RU");
        printMessage(russian);
        printMessage(english);
    }

    private static void printMessage(Locale locale) throws UnsupportedEncodingException {
        ResourceBundle myResources = ResourceBundle.getBundle("util/i18n/resource_bundle/property/ruseng/Messages", locale);
        String message = new String(myResources.getString("hello").getBytes("ISO-8859-1"), "UTF-8");
        System.out.printf("%s: %s\n", locale.toLanguageTag(), message);
    }
}
