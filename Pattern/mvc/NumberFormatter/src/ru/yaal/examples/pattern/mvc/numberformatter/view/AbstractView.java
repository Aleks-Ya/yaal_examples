package ru.yaal.examples.pattern.mvc.numberformatter.view;

import ru.yaal.examples.pattern.mvc.numberformatter.model.IModel;

/**
 * Общие методы представлений.
 */
public abstract class AbstractView implements IView {
    @Override
    public void actionChanged(IModel model) {
        printNumber(model.getNumber());
    }

    protected abstract void printNumber(double number);
}
