package ru.yaal.examples.pattern.mvc.numberformatter.facade;

import ru.yaal.examples.pattern.mvc.numberformatter.controller.ControllerImpl;
import ru.yaal.examples.pattern.mvc.numberformatter.controller.IController;
import ru.yaal.examples.pattern.mvc.numberformatter.model.IModel;
import ru.yaal.examples.pattern.mvc.numberformatter.model.ModelImpl;
import ru.yaal.examples.pattern.mvc.numberformatter.view.DecimalView;
import ru.yaal.examples.pattern.mvc.numberformatter.view.IView;
import ru.yaal.examples.pattern.mvc.numberformatter.view.IntegerView;

/**
 * Фасад: инициализирует систему MVC.
 * Запуск: java -cp . ru.yaal.examples.pattern.mvc.numberformatter.facade.Facade
 */
public class Facade {
    public static void main(String[] args) {
        IView integerView = new IntegerView();
        IView decimalView = new DecimalView();

        IModel model = new ModelImpl();
        model.addChangedListener(integerView);
        model.addChangedListener(decimalView);

        IController controller = new ControllerImpl(model);
        controller.start();
    }
}
