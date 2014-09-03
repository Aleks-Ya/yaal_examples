import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
           eq();
           multi();
           defaultBeforeCase();
    }

    /**
     * switch использует equals(), а не сравнение по ссылке.
     */
    private static void eq() {
        String value = new String("one"); // Не попадет в буфер строк -> сравнение по ссылке не сработает
        switch(value) {
            case "one":  out.println("switch use equals()");
        }
    }
    
    /**
     * Одинаковое действие для нескольких case.
     */
    private static void multi() {
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
    private static void defaultBeforeCase() {
		switch("winter") {
			default: out.println("default section");
			case "winter": out.println("winter section");
		}
	}
}