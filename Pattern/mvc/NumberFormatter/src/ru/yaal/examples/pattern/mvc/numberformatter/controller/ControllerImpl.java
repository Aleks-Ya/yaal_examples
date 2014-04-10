package ru.yaal.examples.pattern.mvc.numberformatter.controller;

import ru.yaal.examples.pattern.mvc.numberformatter.model.IModel;

/**
 * Реализация контроллера.
 */
public class ControllerImpl implements IController {
    private IModel model;

    public ControllerImpl(IModel model) {
        this.model = model;
    }

    @Override
    public void keyPressed(int key) {

    }
}
