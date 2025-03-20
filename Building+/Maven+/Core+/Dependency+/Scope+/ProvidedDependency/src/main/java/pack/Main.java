package pack;

import org.apache.commons.lang3.StringUtils;

/**
 * Подтягивать JAR в работающий ClassLoader не вариант.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(StringUtils.capitalize("abc"));
    }
}