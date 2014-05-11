package scan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Присваиваем полям константные значения.
 */
@Component
public class Constants {

    /**
     * Строковая константа.
     */
    @Value("Адрес")
    private String className;

    /**
     * Константа числовая.
     */
    @Value("1000000")
    private int population;

    @Override
    public String toString() {
        return String.format("Constants[className=%s]", className);
    }

}