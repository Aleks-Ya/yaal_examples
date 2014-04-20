package ru.yaal.examples.pattern.mvc.numberformatter.view;

import ru.yaal.examples.pattern.mvc.numberformatter.model.IModel;

/**
 * Общие методы представлений.
 */
public abstract class AbstractView implements IView {
    @Override
    public void actionChanged(IModel model) {
        print(formatNumber(model.getNumber()));
    }

    private void print(String message) {
        System.out.println(message);
    }

    protected abstract String formatNumber(double number);
}
