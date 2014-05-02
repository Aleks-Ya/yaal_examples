package ru.yaal.examples.pattern.mvc.numberformatter.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация модели.
 */
public class ModelImpl implements IModel {
    private final List<IModelListener> listeners = new ArrayList<>();

    @Override
    public void eventNumberChanged(double number) {
        NumberChangedEvent event = new NumberChangedEvent(number);
        for (IModelListener listener : listeners) {
            listener.actionNumberChanged(event);
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
