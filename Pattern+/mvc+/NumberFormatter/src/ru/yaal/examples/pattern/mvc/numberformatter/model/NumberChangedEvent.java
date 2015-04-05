package ru.yaal.examples.pattern.mvc.numberformatter.model;

/**
 * Событие модели.
 */
public class NumberChangedEvent {
    private double number;

    public NumberChangedEvent(double number) {
        this.number = number;
    }

    public double getNumber() {
        return number;
    }

}
