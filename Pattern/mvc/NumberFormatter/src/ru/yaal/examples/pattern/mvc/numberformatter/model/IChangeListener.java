package ru.yaal.examples.pattern.mvc.numberformatter.model;

/**
 * Интерфейс слушателей события модели "Изменилась".
 */
public interface IChangeListener {
    void actionChanged(IModel model);
}
