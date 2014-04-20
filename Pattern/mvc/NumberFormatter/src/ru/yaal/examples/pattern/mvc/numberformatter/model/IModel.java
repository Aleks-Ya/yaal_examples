package ru.yaal.examples.pattern.mvc.numberformatter.model;

/**
 * Интерфейс моделей.
 */
public interface IModel {
    static public enum State {
        LISTENING,
        OFF
    }

    double getNumber();

    void setNumber(double number);

    void changed();

    void addChangedListener(IModelListener listener);

    void setState(State state);

    void setErrorMessage(String message);
}
