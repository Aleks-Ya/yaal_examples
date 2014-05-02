package ru.yaal.examples.pattern.mvc.numberformatter.model;

/**
 * Событие "Ошибка".
 */
public class ErrorEvent {
    private String message;

    public ErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
