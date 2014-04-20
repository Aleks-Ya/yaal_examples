package ru.yaal.examples.pattern.mvc.numberformatter.model;

/**
 * Интерфейс слушателей события модели "Изменилась".
 */
public interface IModelListener {
    void actionNumberChanged(double number);
    void actionError(String message);
}
