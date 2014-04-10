package ru.yaal.examples.pattern.mvc.numberformatter.view;

/**
 * Представление числа в виде десятичного.
 */
public class DecimalView extends AbstractView {
    @Override
    protected void printNumber(double number) {
        System.out.println(number);
    }
}
