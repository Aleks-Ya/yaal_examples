package ru.yaal.examples.pattern.mvc.numberformatter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация модели.
 */
public class ModelImpl implements IModel {
    private double number = 0;
    private final List<IModelListener> listeners = new ArrayList<>();

    @Override
    public double getNumber() {
        return number;
    }

    @Override
    public void setNumber(double number) {
        this.number = number;
    }

    @Override
    public void eventChanged() {
        for (IModelListener listener : listeners) {
            listener.actionChanged(this);
        }
    }

    @Override
    public void eventError(String message) {
        for (IModelListener listener : listeners) {
            listener.actionError(message);
        }

    }

    @Override
    public void addChangedListener(IModelListener listener) {
        listeners.add(listener);
    }

}
