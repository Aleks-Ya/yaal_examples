package ru.yaal.examples.pattern.mvc.numberformatter.view;

/**
 * Представление числа в виде десятичного.
 */
public class DecimalView extends AbstractView {
    @Override
    protected String formatNumber(double number) {
        return String.valueOf(number);
    }
}
