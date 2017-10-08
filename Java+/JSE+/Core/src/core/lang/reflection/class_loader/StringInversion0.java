package core.lang.reflection.class_loader;

/**
 * Класс, который будет динамически загружен из jar.
 */
class StringInversion0 {
    public static String invertString(String origin) {
        return new StringBuilder(origin).reverse().toString();
    }
}
