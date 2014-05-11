package scan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.Desktop;

/**
 * Присваиваем полям значения, полученные от статических методов.
 */
@Component
public class StaticMethods {

    /**
     * Вызов статического метода.
     */
    @Value("#{T(java.awt.Desktop).desktop}")
    private Desktop desktop;

    @Override
    public String toString() {
        return String.format("StaticMethods[desktop=%s]", desktop);
    }
}