package pattern.mvc.numberformatter.facade;

import pattern.mvc.numberformatter.controller.ControllerImpl;
import pattern.mvc.numberformatter.controller.IController;
import pattern.mvc.numberformatter.model.ModelImpl;
import pattern.mvc.numberformatter.view.DecimalView;
import pattern.mvc.numberformatter.view.IView;
import pattern.mvc.numberformatter.view.IntegerView;
import pattern.mvc.numberformatter.model.IModel;

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
