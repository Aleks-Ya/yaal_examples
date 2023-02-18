package pattern.mvc.numberformatter.model;

/**
 * Событие "Ошибка".
 */
public class ErrorEvent {
    private final String message;

    public ErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
