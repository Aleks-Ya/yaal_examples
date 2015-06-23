package flow_control.switch_operator;

import org.junit.Test;

import static java.lang.System.out;

public class Switch {

    /**
     * switch использует equals(), а не сравнение по ссылке.
     */
    @Test
    public void eq() {
        String value = new String("one"); // Не попадет в буфер строк -> сравнение по ссылке не сработает
        switch(value) {
            case "one":  out.println("switch use equals()");
        }
    }

    /**
     * Одинаковое действие для нескольких case.
     */
    @Test
    public void multi() {
        String day = new String("mon");
        switch(day) {
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
            default: out.printf("%s is not a day%n");
        }
    }

    /**
     * Секцию default можно расположить перед case.
     */
    @Test
    public void defaultBeforeCase() {
		switch("winter") {
			default: out.println("default section");
			case "winter": out.println("winter section");
		}
	}

    /**
     * Константа в switch expression.
     */
    @Test
    public void constant() {
        switch(5) {
            case 1:
            case 2:
            default:
        }

    }
}