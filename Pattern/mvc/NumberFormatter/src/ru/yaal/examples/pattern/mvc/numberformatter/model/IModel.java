package ru.yaal.examples.pattern.mvc.numberformatter.model;

/**
 * Интерфейс моделей.
 */
public interface IModel {
    double getNumber();

    void setNumber(double number);

    void changed();

    void addChangedListener(IModelListener listener);

    void setErrorMessage(String message);
}
