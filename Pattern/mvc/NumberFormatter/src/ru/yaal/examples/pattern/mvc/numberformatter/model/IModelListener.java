package ru.yaal.examples.pattern.mvc.numberformatter.model;

/**
 * Интерфейс слушателей события модели "Изменилась".
 */
public interface IModelListener {
    void actionChanged(IModel model);
    void actionError(String message);
}
