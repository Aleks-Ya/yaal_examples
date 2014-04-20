package ru.yaal.examples.pattern.mvc.numberformatter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация модели.
 */
public class ModelImpl implements IModel {
    private double number = 0;
    private final List<IModelListener> changeListeners = new ArrayList<>();
    private String errorMessage;
    private State state;

    @Override
    public double getNumber() {
        return number;
    }

    @Override
    public void setNumber(double number) {
        this.number = number;
    }

    @Override
    public void changed() {
        for (IModelListener listener : changeListeners) {
            listener.actionChanged(this);
        }
    }

    @Override
    public void addChangedListener(IModelListener listener) {
        changeListeners.add(listener);
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void setErrorMessage(String message) {
        errorMessage = message;
    }
}
