package ru.yaal.examples.pattern.mvc.numberformatter.view;

import ru.yaal.examples.pattern.mvc.numberformatter.model.NumberChangedEvent;

/**
 * Общие методы представлений.
 */
public abstract class AbstractView implements IView {
    @Override
    public void actionNumberChanged(NumberChangedEvent event) {
        print(formatNumber(event.getNumber()));
    }

    private void print(String message) {
        System.out.println(message);
    }

    protected abstract String formatNumber(double number);

    @Override
    public void actionError(String message) {
        print(message);
    }
}
