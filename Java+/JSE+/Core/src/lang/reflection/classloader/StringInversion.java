package lang.reflection.classloader;

/**
 * Класс, который будет динамически загружен из jar.
 */
class StringInversion {
    public static String invertString(String origin) {
        return new StringBuilder(origin).reverse().toString();
    }
}
