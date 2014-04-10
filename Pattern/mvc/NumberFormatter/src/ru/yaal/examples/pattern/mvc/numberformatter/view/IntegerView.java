package ru.yaal.examples.pattern.mvc.numberformatter.view;

/**
 * Представление числа в виде целого.
 */
public class IntegerView extends AbstractView {
    @Override
    protected void printNumber(double number) {
        System.out.println(new Double(number).intValue());
    }
}
