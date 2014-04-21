package ru.yaal.examples.pattern.mvc.numberformatter.view;

/**
 * Общие методы представлений.
 */
public abstract class AbstractView implements IView {
    @Override
    public void actionNumberChanged(double number) {
        print(formatNumber(number));
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
