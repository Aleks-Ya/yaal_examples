package ru.yaal.examples.pattern.mvc.numberformatter.model;

/**
 * Интерфейс слушателей события модели "Изменилась".
 */
public interface IModelListener {
    void actionNumberChanged(NumberChangedEvent event);
    void actionError(String message);
}
