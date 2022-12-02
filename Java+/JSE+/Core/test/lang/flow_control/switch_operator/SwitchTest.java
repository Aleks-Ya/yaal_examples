package lang.flow_control.switch_operator;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class SwitchTest {

    /**
     * switch использует equals(), а не сравнение по ссылке.
     */
    @Test
    void eq() {
        String value = new String("one"); // Не попадет в буфер строк -> сравнение по ссылке не сработает
        switch (value) {
            case "one":
                out.println("switch use equals()");
        }
    }

    /**
     * Одинаковое действие для нескольких case.
     */
    @Test
    void multi() {
        String day = new String("mon");
        switch (day) {
            case "mon":
            case "tue":
            case "wed":
            case "thu":
            case "fri":
                out.printf("%s is a work day%n", day);
                break;
            case "sat":
            case "sun":
                out.printf("%s is a holiday%n", day);
                break;
            default:
                out.printf("%s is not a day%n");
        }
    }

    /**
     * Секцию default можно расположить перед case.
     */
    @Test
    void defaultBeforeCase() {
        switch ("winter") {
            default:
                out.println("default section");
            case "winter":
                out.println("winter section");
        }
    }

    /**
     * Константа в switch expression.
     */
    @Test
    void constant() {
        switch (5) {
            case 1:
            case 2:
            default:
        }

    }
}