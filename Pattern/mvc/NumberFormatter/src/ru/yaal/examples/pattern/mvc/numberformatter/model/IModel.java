package ru.yaal.examples.pattern.mvc.numberformatter.model;

/**
 * Интерфейс моделей.
 */
public interface IModel {
    double getNumber();

    void setNumber(double number);

    void eventChanged();

    void eventError(String message);

    void addChangedListener(IModelListener listener);
}
