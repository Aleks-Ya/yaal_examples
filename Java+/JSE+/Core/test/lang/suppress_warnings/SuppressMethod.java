package lang.suppress_warnings;

/**
 * На поле, конструкторе, методе, параметре метода и локальной переменной.
 */
public class SuppressMethod {

    /**
     * Поле.
     */
    @SuppressWarnings("unused")
    int num;

    /**
     * Конструктор.
     */
    @SuppressWarnings("unused")
    SuppressMethod() {
    }

    /**
     * Метод.
     */
    @SuppressWarnings("unused")
    void doNothing() {
        parameter(null);
    }

    /**
     * Параметр метода.
     * Локальная переменная.
     */
    void parameter(@SuppressWarnings("unused") String unused) {
        @SuppressWarnings("unused")
        long a = 3;
    }

}
