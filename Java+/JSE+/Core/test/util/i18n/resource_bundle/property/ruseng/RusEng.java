package util.i18n.resource_bundle.property.ruseng;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Вывод сообщения на русском или английском языке с помощью ResourceBundle.
 */
class RusEng {

    @Test
    void main() throws UnsupportedEncodingException {
        var english = Locale.ENGLISH;
        var russian = new Locale("ru", "RU");
        printMessage(russian);
        printMessage(english);
    }

    private static void printMessage(Locale locale) throws UnsupportedEncodingException {
        var myResources = ResourceBundle.getBundle("util/i18n/resource_bundle/property/ruseng/Messages", locale);
        var message = new String(myResources.getString("hello").getBytes("ISO-8859-1"), "UTF-8");
        System.out.printf("%s: %s\n", locale.toLanguageTag(), message);
    }
}
