package pattern.mvc.numberformatter.view;

import pattern.mvc.numberformatter.model.ErrorEvent;
import pattern.mvc.numberformatter.model.NumberChangedEvent;

/**
 * Общие методы представлений.
 */
public abstract class AbstractView implements IView {
    @Override
    public void actionNumberChanged(NumberChangedEvent event) {
        print(formatNumber(event.getNumber()));
    }

    private void print(String message) {
        System.out.println(message);
    }

    protected abstract String formatNumber(double number);

    @Override
    public void actionError(ErrorEvent event) {
        print(event.getMessage());
    }
}
