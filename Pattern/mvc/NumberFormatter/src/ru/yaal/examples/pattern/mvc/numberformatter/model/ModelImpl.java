package ru.yaal.examples.pattern.mvc.numberformatter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация модели.
 */
public class ModelImpl implements IModel {
    private double number = 0;
    private final List<IChangeListener> changeListeners = new ArrayList<>();

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
        for (IChangeListener listener : changeListeners) {
            listener.actionChanged(this);
        }
    }

    @Override
    public void addChangedListener(IChangeListener listener) {
        changeListeners.add(listener);
    }
}
